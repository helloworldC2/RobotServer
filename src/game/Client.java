package game;
import java.net.InetAddress;


public class Client {

	public String username;
	public int port;
	public int x,y,movingDir,numSteps;
	public boolean isMoving;
	public InetAddress ipAddress;
	public Client(String username, InetAddress ipAddress, int port){
		this.ipAddress = ipAddress;
		this.username = username;
		this.port = port;
	}
	
	public String getUsername(){
		return this.username;
	}

	public void setMoving(boolean moving) {
		this.isMoving = moving;
		
	}

	public void setMovingDir(int m) {
		this.movingDir = m;
		
	}

	public void setNumSteps(int n) {
		this.numSteps = n;
		
	}
}
