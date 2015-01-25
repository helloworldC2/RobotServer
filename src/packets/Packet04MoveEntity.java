package packets;
import game.Client;
import game.GameServer;





public class Packet04MoveEntity extends Packet {

    private int id;
    private int x, y;

    
    private int movingDir = 0;
    private boolean isSwimming;

    public Packet04MoveEntity(byte[] data) {
        super(04);
        String[] dataArray = readData(data).split(",");
        this.id = Integer.parseInt(dataArray[0]);
        this.x = Integer.parseInt(dataArray[1]);
        this.y = Integer.parseInt(dataArray[2]);
        this.movingDir = Integer.parseInt(dataArray[3]);
        this.isSwimming = Boolean.parseBoolean(dataArray[4]);
    }

    public Packet04MoveEntity(int id, int x, int y, int movingDir,boolean isSwimming) {
        super(04);
        this.id = id;
        this.x = x;
        this.y = y;
        this.movingDir = movingDir;
        this.isSwimming = isSwimming;
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
        return ("04" + this.id + "," + this.x + "," + this.y + "," + this.movingDir + "," + this.isSwimming).getBytes();

    }

    public int getId() {
        return this.id;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

   

    public int getMovingDir() {
        return movingDir;
    }

	public boolean getSwimming() {
		return this.isSwimming;
	}
}
