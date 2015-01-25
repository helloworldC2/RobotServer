package packets;
import game.Client;
import game.GameServer;





public class Packet05SendTiles extends Packet {

    private int[] tiles;

    public Packet05SendTiles(byte[] data) {
        super(05);
        String[] dataArray = readData(data).split(",");
        int[] t = new int[(dataArray.length)];
        for(int i=0;i<dataArray.length;i++){
        	t[i]=Integer.parseInt(dataArray[i]);
        }
        this.tiles = t;
    }

    public Packet05SendTiles(int[] tiles) {
        super(05);
        this.tiles = tiles;
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
        return ("05" + this.getTileString()).getBytes();

    }
    
    public String getTileString(){
    	String t = "";
    	for(int i:this.tiles){
    		t=t+i;
    		t=t+",";
    	}
		return t;
    	
    }
    
    public int[] getTiles() {
        return this.tiles;
    }

    
}
