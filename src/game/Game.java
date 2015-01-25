package game;
public class Game  implements Runnable {


    private Thread thread;

    

   
    public GameServer socketServer;

    

   

    public synchronized void start() {
    	socketServer = new GameServer();
        socketServer.start();
        System.out.println("Ready");
           
    }

    public synchronized void stop() {
       
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
       

        
    }

  

  public static void main(String[] args){
	  new Game().start();
  }

   
}
