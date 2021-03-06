package packets;
import game.GameServer;





public class Packet02Move extends Packet {

    private String username;
    private int x, y;

  
    private boolean isSwimming;
    private int movingDir = 1;

    public Packet02Move(byte[] data) {
        super(02);
        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        this.x = Integer.parseInt(dataArray[1]);
        this.y = Integer.parseInt(dataArray[2]);
        this.movingDir = Integer.parseInt(dataArray[3]);
        this.isSwimming = Boolean.parseBoolean(dataArray[4]);
    }

    public Packet02Move(String username, int x, int y, int movingDir,boolean isSwimming) {
        super(02);
        this.username = username;
        this.x = x;
        this.y = y;
        this.movingDir = movingDir;
        this.isSwimming = isSwimming;
    }


    @Override
    public void writeData(GameServer server) {
        server.sendDataToAllClients(getData());
    }

    @Override
    public byte[] getData() {
        return ("02" + this.username + "," + this.x + "," + this.y  + "," + this.movingDir + "," + this.isSwimming).getBytes();

    }

    public String getUsername() {
        return username;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

 

    public boolean isSwimming() {
        return isSwimming;
    }

    public int getMovingDir() {
        return movingDir;
    }
}
