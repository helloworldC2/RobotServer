package game;
public class Game  implements Runnable {


    private Thread thread;

    public boolean running = false;
    public int tickCount = 0;

   
    public GameServer socketServer;

    public boolean debug = true;
    public boolean isApplet = false;

    public void init() {
       
    }

    public synchronized void start() {
    			
    			
                socketServer = new GameServer(this);
                socketServer.start();
                System.out.println("Ready");
           
    }

    public synchronized void stop() {
        running = false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / 60D;

        int ticks = 0;
        int frames = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        init();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            

            while (delta >= 1) {
                ticks++;
                tick();
                delta -= 1;
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                frames = 0;
                ticks = 0;
            }
        }
    }

    public void tick() {
        tickCount++;
       
    }

  public static void main(String[] args){
	  new Game().start();
  }

   
}
