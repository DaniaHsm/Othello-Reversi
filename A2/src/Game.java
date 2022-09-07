
import java.util.Scanner;

public class Game {
	
	public OthelloPlayer p1;
	public OthelloPlayer p2;
	public Board board;
	
	public Game (OthelloPlayer p1, OthelloPlayer p2) {
		this.p1 = p1;
		this.p2 = p2;
		
	}

	public void start() {
		
		System.out.println(
	            "Select one of these 2 layouts:"+
	            "\n1. Standard" +
	            "\n2. Four-by-four"
	            );

	        Scanner sc = new Scanner(System.in);
	        
	        board = new Board(p1, p2, sc.nextInt()); 
	        
	        board.play(); //calls play method to start new game

		
	}
}
