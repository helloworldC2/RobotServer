package game;

public class Entity {

	public int id,x,y;
	public String type;
	public boolean isSwimming;
	
	public Entity(int id, String type,int x, int y,boolean isSwimming){
		this.id =id;
		this.x = x;
		this.y = y;
		this.type = type;
		this.isSwimming = isSwimming;
	}
}
