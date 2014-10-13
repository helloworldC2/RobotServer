package game;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import packets.Packet;
import packets.Packet.PacketTypes;
import packets.Packet00Login;
import packets.Packet01Disconnect;
import packets.Packet02Move;



public class GameServer extends Thread {

    private DatagramSocket socket;
    private Game game;
    private List<Client> connectedPlayers = new ArrayList<Client>();
    public GameServer(Game game) {
        this.game = game;
        try {
            this.socket = new DatagramSocket(1331);
        } catch (SocketException e) {
        	System.out.println("nooo");
            e.printStackTrace();
        }
        System.out.println("Server started on port: 1331");
    }

    public void run() {
        while (true) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
        }
    }

    private void parsePacket(byte[] data, InetAddress address, int port) {
        String message = new String(data).trim();
        PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
        Packet packet = null;
        switch (type) {
        default:
        case INVALID:
            break;
        case LOGIN:
            packet = new Packet00Login(data);
            System.out.println(getTimeStamp()+"[" + address.getHostAddress() + ":" + port + "] "
                    + ((Packet00Login) packet).getUsername() + " has connected...");
            Client player = new Client(((Packet00Login) packet).getUsername(), address, port);
            this.addConnection(player, (Packet00Login) packet);
            Packet00Login accepted = new Packet00Login(data);
            accepted.writeDataToOneClient(this, player);
            break;
        case DISCONNECT:
            packet = new Packet01Disconnect(data);
            System.out.println(getTimeStamp()+"[" + address.getHostAddress() + ":" + port + "] "
                    + ((Packet01Disconnect) packet).getUsername() + " has left...");
            this.removeConnection((Packet01Disconnect) packet);
            break;
        case MOVE:
            packet = new Packet02Move(data);
            this.handleMove(((Packet02Move) packet),address,port);
            break;
        }
    }

    public void addConnection(Client player, Packet00Login packet) {
        boolean alreadyConnected = false;
        for (Client p : this.connectedPlayers) {
            if (player.getUsername().equalsIgnoreCase(p.getUsername())) {
                if (p.ipAddress == null) {
                    p.ipAddress = player.ipAddress;
                }
                if (p.port == -1) {
                    p.port = player.port;
                }
                alreadyConnected = true;
            } else {
                // relay to the current connected player that there is a new
                // player
                sendData(packet.getData(), p.ipAddress, p.port);

                // relay to the new player that the currently connect player
                // exists
                packet = new Packet00Login(p.getUsername(), p.x, p.y);
                sendData(packet.getData(), player.ipAddress, player.port);
            }
        }
        if (!alreadyConnected) {
            this.connectedPlayers.add(player);
        }
    }

    public void removeConnection(Packet01Disconnect packet) {
        this.connectedPlayers.remove(getPlayerMPIndex(packet.getUsername()));
        packet.writeData(this);
    }

    public Client getPlayerMP(String username) {
        for (Client player : this.connectedPlayers) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }

    public int getPlayerMPIndex(String username) {
        int index = 0;
        for (Client player : this.connectedPlayers) {
            if (player.getUsername().equals(username)) {
                break;
            }
            index++;
        }
        return index;
    }

    public void sendData(byte[] data, InetAddress ipAddress, int port) {
    		System.out.println(getTimeStamp()+"Sending "+new String(data)+" ("+data.length+" bytes)"+"  [" + ipAddress.getHostAddress() + ":" + port + "] ");
            DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
            try {
                this.socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void sendDataToAllClients(byte[] data) {
        for (Client p : connectedPlayers) {
            sendData(data, p.ipAddress, p.port);
        }
    }

    public void sendDataToAllOtherClients(byte[] data, InetAddress address, int port) {
        for (Client p : connectedPlayers) {
        	if(p.ipAddress!=address&&p.port!=port)
            sendData((p.username+": "+new String(data)).getBytes(), p.ipAddress, p.port);
        }
    }
    
    private void handleMove(Packet02Move packet, InetAddress address, int port) {
        if (getPlayerMP(packet.getUsername()) != null) {
            int index = getPlayerMPIndex(packet.getUsername());
            Client player = this.connectedPlayers.get(index);
            player.x = packet.getX();
            player.y = packet.getY();
            player.setMoving(packet.isMoving());
            player.setMovingDir(packet.getMovingDir());
            player.setNumSteps(packet.getNumSteps());
            packet.writeData(this);
            
        }
    }

	public void sendDataToClient(byte[] data,Client p) {
		sendData(data, p.ipAddress, p.port);
		
	}
	private String getTimeStamp(){
		return new Date().toGMTString()+"\t";
	}

}
