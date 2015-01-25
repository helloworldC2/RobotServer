package game;
import java.net.InetAddress;


public class Client {

	public String username;
	public int port;
	public int x,y,movingDir;
	public boolean isSwimming;
	public InetAddress ipAddress;
	public Client(String username, InetAddress ipAddress, int port){
		this.ipAddress = ipAddress;
		this.username = username;
		this.port = port;
	}
	
	public String getUsername(){
		return this.username;
	}

	public void setSwimming(boolean swimming) {
		this.isSwimming = swimming;
		
	}

	public void setMovingDir(int m) {
		this.movingDir = m;
		
	}

	
}
