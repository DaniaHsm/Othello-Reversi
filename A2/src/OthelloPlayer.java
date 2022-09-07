


public class OthelloPlayer extends Player{
	String name;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public OthelloPlayer(String name) {
		super(name);
		
	}
	
	@Override
	public String toString() {
		return getName();
	}

	
	
}
