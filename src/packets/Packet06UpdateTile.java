package packets;
import game.Client;
import game.GameServer;





public class Packet06UpdateTile extends Packet {

 
    private int x, y, tile;

    public Packet06UpdateTile(byte[] data) {
        super(06);
        String[] dataArray = readData(data).split(",");
        this.tile = Integer.parseInt(dataArray[0]);
        this.x = Integer.parseInt(dataArray[1]);
        this.y = Integer.parseInt(dataArray[2]);
    }

    public Packet06UpdateTile(int tile, int x, int y) {
        super(06);
        this.tile = tile;
        this.x = x;
        this.y = y;
    }


    @Override
    public void writeData(GameServer server) {
        server.sendDataToAllClients(getData());
    }
    public void writeDataToOneClient(GameServer server, Client player) {
        server.sendDataToClient(getData(),player);
    }

    @Override
    public byte[] getData() {
        return ("06" + this.tile + "," + this.x + "," + this.y).getBytes();

    }

    public int getTile() {
        return this.tile;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

  
}
