import java.util.Scanner;

public class OthelloDriver {


	public static void main(String[] args) {
		
		//GAME MUST START BY ASKING USERS TO INPUT THEIR NAME AND COLOR
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Welcome to Othello. Would you like to quit, "
				+ "load a previous game, or start a new game?");
		System.out.println("1. Quit");
		System.out.println("2. Load Game");
		System.out.println("3. Start New Game");
		
		int option = sc.nextInt();
		
		switch(option) {
		
		case 1: 
			System.out.println("Thank you for playing Othello.");
			break;
		case 2:
			System.out.println("Please enter the file name of the game: ");
			String fileName = sc.next();
			//Board board = Board.load(sc.next());
			Board board = new Board(fileName);
			board.load();
			System.out.println("File loaded");
			break;
		case 3:
			System.out.println("Kindly enter Player 1's name: ");
			OthelloPlayer p1 = new OthelloPlayer(sc.next());
	
			System.out.println("Kindly enter Player 2's name: ");
			OthelloPlayer p2 = new OthelloPlayer(sc.next());

			Game game = new Game(p1, p2);
			game.start();
			break;
		default:
			System.out.println("You must choose one of the options. ");
			return;
		}
		

		
		
	}

}
