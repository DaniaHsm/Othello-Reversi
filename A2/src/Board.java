
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Board  {

	public OthelloPlayer p1;
	public OthelloPlayer p2;
	public OthelloPlayer current;
	int row, col;
	
	Position[][] positionsArr = new Position[8][8];
	
	private static Scanner sc = new Scanner(System.in);
	
	public void drawBoard() {  
		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				
				System.out.print(positionsArr[row][col].getPiece());
			}
			System.out.println();
		}							
	}
	
	public static void load () { 
		
		 // Should the user choose to load a game, the program should ask for a filename, 
		 // load the game saved in that file, then continue from where that game left of
	
		try {
			FileInputStream fis = new FileInputStream(sc.next());
			ObjectInputStream ois = new ObjectInputStream(fis);
			board = (Board) ois.readObject();
			ois.close();
			System.out.println("Game loaded");
		}
		catch (Exception e) {
			System.out.println("Error: Cannot load data.");
		}
		
	}
	

	public Board(String save_file) { 
		load();
	}
	
	public static Board board;
	public void save() {
		
		// Games should be saved as a text file, with first

		try {
			System.out.println("Enter file name: ");
		FileOutputStream fos = new FileOutputStream(sc.next());
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(board);
		oos.flush();
		oos.close();
		}
		catch (Exception e) {
			System.out.println("Error: Cannot save data.");
		}
	}
	
	public Board (OthelloPlayer p1, OthelloPlayer p2, int start) {
		
		this.p1 = p1;
		this.p2 = p2;
		current = p1;
		
		for(int row=0; row<8;row++) {
            for(int col=0; col<8;col++) {
            	positionsArr[row][col] = new Position();
                positionsArr[row][col].setPiece(Position.EMPTY);
            }
        }

		switch(start) {
        case 1: 
            positionsArr[3][3].setPiece(Position.WHITE); 
            positionsArr[4][4].setPiece(Position.WHITE); 
            positionsArr[3][4].setPiece(Position.BLACK); 
            positionsArr[4][3].setPiece(Position.BLACK); 
            break;
        case 2:
            positionsArr[3][3].setPiece(Position.WHITE); 
            positionsArr[3][2].setPiece(Position.WHITE); 
            positionsArr[2][3].setPiece(Position.WHITE); 
            positionsArr[2][2].setPiece(Position.WHITE); 
            positionsArr[4][4].setPiece(Position.WHITE); 
            positionsArr[4][5].setPiece(Position.WHITE); 
            positionsArr[5][4].setPiece(Position.WHITE); 
            positionsArr[5][5].setPiece(Position.WHITE); 
            
            positionsArr[3][4].setPiece(Position.BLACK); 
            positionsArr[2][4].setPiece(Position.BLACK); 
            positionsArr[2][5].setPiece(Position.BLACK);
            positionsArr[3][5].setPiece(Position.BLACK); 
            positionsArr[4][3].setPiece(Position.BLACK);
            positionsArr[5][3].setPiece(Position.BLACK);
            positionsArr[5][2].setPiece(Position.BLACK); 
            positionsArr[4][2].setPiece(Position.BLACK);
            break;
        default:
            System.out.println("Invalid starting position!");
            return;
			}
		
		positionsArr[0][0] = new UnplayablePosition();
		positionsArr[0][0].setPiece(UnplayablePosition.UNPLAYABLE);
		positionsArr[0][7] = new UnplayablePosition();
		positionsArr[0][7].setPiece(UnplayablePosition.UNPLAYABLE);
		positionsArr[7][0] = new UnplayablePosition();
		positionsArr[7][0].setPiece(UnplayablePosition.UNPLAYABLE);
		positionsArr[7][7] = new UnplayablePosition();
		positionsArr[7][7].setPiece(UnplayablePosition.UNPLAYABLE);
	}
	
	
	public void play() { 
		
		Scanner sc = new Scanner(System.in);
		OthelloPlayer current = p1;
		
		System.out.println("Please select an option");
		System.out.println("1. Save");
		System.out.println("2. Forfeit");
		System.out.println("3. Make a move");
		System.out.println();
		drawBoard();
		
		switch (sc.nextInt()) {
		case 1: 
			save();
			System.out.println("Saved");
			break;
		case 2:							
			swap();
			takeTurn();
			drawBoard();
		case 3:
			takeTurn();
			drawBoard();
		}
		
	}
	
	private void swap() {
	if (current == p1) { 
		current = p2;
	}
	else {
		current = p1;
	}
	}
	
	private void takeTurn() { //added field OthelloPlayer current
		
		Position p = new Position();
		Position pp = new UnplayablePosition();
			
			if (current == p1) {
				System.out.println("Select the row and column numbers (0 - 7) for " + current);
				
				boolean bool = true;
				
				do {
					System.out.print("Row: ");
					row = sc.nextInt();
					System.out.print("Column: ");
					col = sc.nextInt();
						
					if (pp.canPlay(positionsArr, row, col) == false) {										//if user tries to place a disk in the middle of an empty space w nothing around it
						System.out.println("This position cannot be played! Try again...");
					}
					else if ((row < 0 || row > 7) && (col < 0 || col > 7)) {									//if user tries to place a disk outside the board
						System.out.println("ERROR: Select placements that fit in the board.");
					}
					else if (p.canPlay(positionsArr, row, col) == false) {					//if the user tries to place a disk in a non-empty space
						System.out.println("This place is taken!");
					}
					else if (p.canPlay(positionsArr, row, col) == true) {
						positionsArr[row][col].setPiece(Position.BLACK);
						bool = false;
						flip();
						if(current == p2) current = p1;
						else current = p2;
						play();
					}
				} while(bool);
			}
			else {
				System.out.println("Select the row and column numbers (0 - 7) for " + current);
					
				boolean bool = true;
					
				do {
					System.out.print("Row: ");
					row = sc.nextInt();
					System.out.print("Column: ");
					col = sc.nextInt();
						
					if (pp.canPlay(positionsArr, row, col) == false) {										//if user tries to place a disk in the middle of an empty space w nothing around it
						System.out.println("This position cannot be played! Try again...");
					}
					else if (row < 0 || row > 7 && col < 0 || col > 7) {									//if user tries to place a disk outside the board
						System.out.println("ERROR: Select placements that fit in the board.");
					}
					else if (p.canPlay(positionsArr, row, col) == false) {					//if the user tries to place a disk where theres already a disk
						System.out.println("This place is taken!");
					}
					else if (p.canPlay(positionsArr, row, col) == true) {
						positionsArr[row][col].setPiece(Position.WHITE);
						bool = false;
						flip();
						if(current == p2) current = p1;
						else current = p2;
						play();
					}
				} while(bool);
			}
		}
	
		public void flip() {  //code that flips appropriate disks

			// E, O, S, N
			
			if (current == p1) { //PLAYER 1 : BLACK
				
				try { //FLIPS ONE DISK TO BLACK
				
				if (positionsArr[row][col+2].getPiece() == Position.BLACK && positionsArr[row][col+1].getPiece() == Position.WHITE) {  // east (-->)
					positionsArr[row][col+1].setPiece(Position.BLACK);
				}
				else if (positionsArr[row][col-2].getPiece() == Position.BLACK && positionsArr[row][col-1].getPiece() == Position.WHITE) { // west (<--)
					positionsArr[row][col-1].setPiece(Position.BLACK);
				}
				else if (positionsArr[row+2][col].getPiece() == Position.BLACK && positionsArr[row+1][col].getPiece() == Position.WHITE) { // south (v)
					positionsArr[row+1][col].setPiece(Position.BLACK);
				}
				else if (positionsArr[row-2][col].getPiece() == Position.BLACK && positionsArr[row-1][col].getPiece() == Position.WHITE) { // north (^)
					positionsArr[row-1][col].setPiece(Position.BLACK);
				}
				
				}
				catch (IndexOutOfBoundsException e){
					positionsArr[row][col].getPiece();
				}
				
				try {  //FLIPS 2 DISKS TO BLACK
					
					if (positionsArr[row][col+3].getPiece() == Position.BLACK && positionsArr[row][col+1].getPiece() == Position.WHITE && positionsArr[row][col+2].getPiece() == Position.WHITE) {  // -->
						positionsArr[row][col+1].setPiece(Position.BLACK);
						positionsArr[row][col+2].setPiece(Position.BLACK);
					}
					else if (positionsArr[row][col-3].getPiece() == Position.BLACK && positionsArr[row][col-1].getPiece() == Position.WHITE && positionsArr[row][col-2].getPiece() == Position.WHITE) { // <--
						positionsArr[row][col-1].setPiece(Position.BLACK);
						positionsArr[row][col-2].setPiece(Position.BLACK);
					}
					else if (positionsArr[row+3][col].getPiece() == Position.BLACK && positionsArr[row+1][col].getPiece() == Position.WHITE && positionsArr[row+2][col].getPiece() == Position.WHITE) { // south
						positionsArr[row+1][col].setPiece(Position.BLACK);
						positionsArr[row+2][col].setPiece(Position.BLACK);
					}
					else if (positionsArr[row-3][col].getPiece() == Position.BLACK && positionsArr[row-1][col].getPiece() == Position.WHITE && positionsArr[row-2][col].getPiece() == Position.WHITE) { // north
						positionsArr[row-1][col].setPiece(Position.BLACK);
						positionsArr[row-2][col].setPiece(Position.BLACK);
					}
				}
				catch (IndexOutOfBoundsException e){
					positionsArr[row][col].getPiece();
				}
				
				try {  //FLIPS 3 DISKS TO BLACK
					
					if (positionsArr[row][col+4].getPiece() == Position.BLACK && positionsArr[row][col+1].getPiece() == Position.WHITE && positionsArr[row][col+2].getPiece() == Position.WHITE && positionsArr[row][col+3].getPiece() == Position.WHITE) {  // -->
						positionsArr[row][col+2].setPiece(Position.BLACK);
						positionsArr[row][col+3].setPiece(Position.BLACK);
						positionsArr[row][col+1].setPiece(Position.BLACK);
					}
					else if (positionsArr[row][col-4].getPiece() == Position.BLACK && positionsArr[row][col-1].getPiece() == Position.WHITE && positionsArr[row][col-2].getPiece() == Position.WHITE && positionsArr[row][col-3].getPiece() == Position.WHITE) { // <--
						positionsArr[row][col-1].setPiece(Position.BLACK);
						positionsArr[row][col-2].setPiece(Position.BLACK);
						positionsArr[row][col-3].setPiece(Position.BLACK);
					}
					else if (positionsArr[row+4][col].getPiece() == Position.BLACK && positionsArr[row+1][col].getPiece() == Position.WHITE && positionsArr[row+2][col].getPiece() == Position.WHITE  && positionsArr[row+3][col].getPiece() == Position.WHITE) { // south
						positionsArr[row+1][col].setPiece(Position.BLACK);
						positionsArr[row+2][col].setPiece(Position.BLACK);
						positionsArr[row+3][col].setPiece(Position.BLACK);
					}
					else if (positionsArr[row-4][col].getPiece() == Position.BLACK && positionsArr[row-1][col].getPiece() == Position.WHITE && positionsArr[row-2][col].getPiece() == Position.WHITE  && positionsArr[row-3][col].getPiece() == Position.WHITE) { // north
						positionsArr[row-1][col].setPiece(Position.BLACK);
						positionsArr[row-2][col].setPiece(Position.BLACK);
						positionsArr[row-3][col].setPiece(Position.BLACK);
					}
				}
				catch (IndexOutOfBoundsException e){
					positionsArr[row][col].getPiece();
				}
				
				try {  //FLIPS 4 DISKS TO BLACK
					
					if (positionsArr[row][col+5].getPiece() == Position.BLACK && positionsArr[row][col+1].getPiece() == Position.WHITE && positionsArr[row][col+2].getPiece() == Position.WHITE && positionsArr[row][col+3].getPiece() == Position.WHITE && positionsArr[row][col+4].getPiece() == Position.WHITE) {  // -->
						positionsArr[row][col+1].setPiece(Position.BLACK);
						positionsArr[row][col+2].setPiece(Position.BLACK);
						positionsArr[row][col+3].setPiece(Position.BLACK);
						positionsArr[row][col+4].setPiece(Position.BLACK);
					}
					else if (positionsArr[row][col-5].getPiece() == Position.BLACK && positionsArr[row][col+1].getPiece() == Position.WHITE && positionsArr[row][col+2].getPiece() == Position.WHITE && positionsArr[row][col+3].getPiece() == Position.WHITE && positionsArr[row][col-4].getPiece() == Position.WHITE) {  // -->
						positionsArr[row][col-1].setPiece(Position.BLACK);
						positionsArr[row][col-2].setPiece(Position.BLACK);
						positionsArr[row][col-3].setPiece(Position.BLACK);
						positionsArr[row][col-4].setPiece(Position.BLACK);
					}
					else if (positionsArr[row+5][col].getPiece() == Position.BLACK && positionsArr[row+1][col].getPiece() == Position.WHITE && positionsArr[row+2][col].getPiece() == Position.WHITE  && positionsArr[row+3][col].getPiece() == Position.WHITE && positionsArr[row+4][col].getPiece() == Position.WHITE) { // south
						positionsArr[row+1][col].setPiece(Position.BLACK);
						positionsArr[row+2][col].setPiece(Position.BLACK);
						positionsArr[row+3][col].setPiece(Position.BLACK);
						positionsArr[row+4][col].setPiece(Position.BLACK);
					}
					else if (positionsArr[row-5][col].getPiece() == Position.BLACK && positionsArr[row-1][col].getPiece() == Position.WHITE && positionsArr[row-2][col].getPiece() == Position.WHITE  && positionsArr[row-3][col].getPiece() == Position.WHITE && positionsArr[row-4][col].getPiece() == Position.WHITE) { // north
						positionsArr[row-1][col].setPiece(Position.BLACK);
						positionsArr[row-2][col].setPiece(Position.BLACK);
						positionsArr[row-3][col].setPiece(Position.BLACK);
						positionsArr[row-4][col].setPiece(Position.BLACK);
					}
				}
				catch (IndexOutOfBoundsException e){
					positionsArr[row][col].getPiece();
				}
				
				try {  //FLIPS 5 DISKS TO BLACK
					
					if (positionsArr[row][col+6].getPiece() == Position.BLACK && positionsArr[row][col+1].getPiece() == Position.WHITE && positionsArr[row][col+2].getPiece() == Position.WHITE && positionsArr[row][col+3].getPiece() == Position.WHITE && positionsArr[row][col+4].getPiece() == Position.WHITE && positionsArr[row][col+5].getPiece() == Position.WHITE) {  // -->
						positionsArr[row][col+1].setPiece(Position.BLACK);
						positionsArr[row][col+2].setPiece(Position.BLACK);
						positionsArr[row][col+3].setPiece(Position.BLACK);
						positionsArr[row][col+4].setPiece(Position.BLACK);
						positionsArr[row][col+5].setPiece(Position.BLACK);
					}
					else if (positionsArr[row][col-6].getPiece() == Position.BLACK && positionsArr[row][col+1].getPiece() == Position.WHITE && positionsArr[row][col+2].getPiece() == Position.WHITE && positionsArr[row][col+3].getPiece() == Position.WHITE && positionsArr[row][col-4].getPiece() == Position.WHITE && positionsArr[row][col-5].getPiece() == Position.WHITE) {  // -->
						positionsArr[row][col-1].setPiece(Position.BLACK);
						positionsArr[row][col-2].setPiece(Position.BLACK);
						positionsArr[row][col-3].setPiece(Position.BLACK);
						positionsArr[row][col-4].setPiece(Position.BLACK);
						positionsArr[row][col-5].setPiece(Position.BLACK);
					}
					else if (positionsArr[row+6][col].getPiece() == Position.BLACK && positionsArr[row+1][col].getPiece() == Position.WHITE && positionsArr[row+2][col].getPiece() == Position.WHITE  && positionsArr[row+3][col].getPiece() == Position.WHITE && positionsArr[row+4][col].getPiece() == Position.WHITE && positionsArr[row+5][col].getPiece() == Position.WHITE) { // south
						positionsArr[row+1][col].setPiece(Position.BLACK);
						positionsArr[row+2][col].setPiece(Position.BLACK);
						positionsArr[row+3][col].setPiece(Position.BLACK);
						positionsArr[row+4][col].setPiece(Position.BLACK);
						positionsArr[row+5][col].setPiece(Position.BLACK);
					}
					else if (positionsArr[row-6][col].getPiece() == Position.BLACK && positionsArr[row-1][col].getPiece() == Position.WHITE && positionsArr[row-2][col].getPiece() == Position.WHITE  && positionsArr[row-3][col].getPiece() == Position.WHITE && positionsArr[row-4][col].getPiece() == Position.WHITE && positionsArr[row-5][col].getPiece() == Position.WHITE) { // north
						positionsArr[row-1][col].setPiece(Position.BLACK);
						positionsArr[row-2][col].setPiece(Position.BLACK);
						positionsArr[row-3][col].setPiece(Position.BLACK);
						positionsArr[row-4][col].setPiece(Position.BLACK);
						positionsArr[row-5][col].setPiece(Position.BLACK);
					}
				}
				catch (IndexOutOfBoundsException e){
					positionsArr[row][col].getPiece();
				}
				
				try {  //FLIPS 6 DISKS TO BLACK
					
					if (positionsArr[row][col+7].getPiece() == Position.BLACK && positionsArr[row][col+1].getPiece() == Position.WHITE && positionsArr[row][col+2].getPiece() == Position.WHITE && positionsArr[row][col+3].getPiece() == Position.WHITE && positionsArr[row][col+4].getPiece() == Position.WHITE && positionsArr[row][col+5].getPiece() == Position.WHITE && positionsArr[row][col+6].getPiece() == Position.WHITE) {  // -->
						positionsArr[row][col+1].setPiece(Position.BLACK);
						positionsArr[row][col+2].setPiece(Position.BLACK);
						positionsArr[row][col+3].setPiece(Position.BLACK);
						positionsArr[row][col+4].setPiece(Position.BLACK);
						positionsArr[row][col+5].setPiece(Position.BLACK);
						positionsArr[row][col+6].setPiece(Position.BLACK);
					}
					else if (positionsArr[row][col-7].getPiece() == Position.BLACK && positionsArr[row][col+1].getPiece() == Position.WHITE && positionsArr[row][col+2].getPiece() == Position.WHITE && positionsArr[row][col+3].getPiece() == Position.WHITE && positionsArr[row][col-4].getPiece() == Position.WHITE && positionsArr[row][col-5].getPiece() == Position.WHITE && positionsArr[row][col-6].getPiece() == Position.WHITE) {  // -->
						positionsArr[row][col-1].setPiece(Position.BLACK);
						positionsArr[row][col-2].setPiece(Position.BLACK);
						positionsArr[row][col-3].setPiece(Position.BLACK);
						positionsArr[row][col-4].setPiece(Position.BLACK);
						positionsArr[row][col-5].setPiece(Position.BLACK);
						positionsArr[row][col-6].setPiece(Position.BLACK);
					}
					else if (positionsArr[row+7][col].getPiece() == Position.BLACK && positionsArr[row+1][col].getPiece() == Position.WHITE && positionsArr[row+2][col].getPiece() == Position.WHITE  && positionsArr[row+3][col].getPiece() == Position.WHITE && positionsArr[row+4][col].getPiece() == Position.WHITE && positionsArr[row+5][col].getPiece() == Position.WHITE && positionsArr[row+6][col].getPiece() == Position.WHITE) { // south
						positionsArr[row+1][col].setPiece(Position.BLACK);
						positionsArr[row+2][col].setPiece(Position.BLACK);
						positionsArr[row+3][col].setPiece(Position.BLACK);
						positionsArr[row+4][col].setPiece(Position.BLACK);
						positionsArr[row+5][col].setPiece(Position.BLACK);
						positionsArr[row+6][col].setPiece(Position.BLACK);
					}
					else if (positionsArr[row-7][col].getPiece() == Position.BLACK && positionsArr[row-1][col].getPiece() == Position.WHITE && positionsArr[row-2][col].getPiece() == Position.WHITE  && positionsArr[row-3][col].getPiece() == Position.WHITE && positionsArr[row-4][col].getPiece() == Position.WHITE && positionsArr[row-5][col].getPiece() == Position.WHITE && positionsArr[row-6][col].getPiece() == Position.WHITE) { // north
						positionsArr[row-1][col].setPiece(Position.BLACK);
						positionsArr[row-2][col].setPiece(Position.BLACK);
						positionsArr[row-3][col].setPiece(Position.BLACK);
						positionsArr[row-4][col].setPiece(Position.BLACK);
						positionsArr[row-5][col].setPiece(Position.BLACK);
						positionsArr[row-6][col].setPiece(Position.BLACK);
					}
				}
				catch (IndexOutOfBoundsException e){
					positionsArr[row][col].getPiece();
				}
				
			}
			else { //if player == p2
				
				try { //FLIPS ONE DISK TO WHITE
					
					if (positionsArr[row][col+2].getPiece() == Position.WHITE && positionsArr[row][col+1].getPiece() == Position.BLACK) {  // east (-->)
						positionsArr[row][col+1].setPiece(Position.WHITE);
					}
					else if (positionsArr[row][col-2].getPiece() == Position.WHITE && positionsArr[row][col-1].getPiece() == Position.BLACK) { // west (<--)
						positionsArr[row][col-1].setPiece(Position.WHITE);
					}
					else if (positionsArr[row+2][col].getPiece() == Position.WHITE && positionsArr[row+1][col].getPiece() == Position.BLACK) { // south (v)
						positionsArr[row+1][col].setPiece(Position.WHITE);
					}
					else if (positionsArr[row-2][col].getPiece() == Position.WHITE && positionsArr[row-1][col].getPiece() == Position.BLACK) { // north (^)
						positionsArr[row-1][col].setPiece(Position.WHITE);
					}
					
					}
					catch (IndexOutOfBoundsException e){
						positionsArr[row][col].getPiece();
					}
					
					try {  //FLIPS 2 DISKS TO BLACK
						
						if (positionsArr[row][col+3].getPiece() == Position.WHITE && positionsArr[row][col+1].getPiece() == Position.BLACK && positionsArr[row][col+2].getPiece() == Position.BLACK) {  // -->
							positionsArr[row][col+1].setPiece(Position.WHITE);
							positionsArr[row][col+2].setPiece(Position.WHITE);
						}
						else if (positionsArr[row][col-3].getPiece() == Position.WHITE && positionsArr[row][col-1].getPiece() == Position.BLACK && positionsArr[row][col-2].getPiece() == Position.BLACK) { // <--
							positionsArr[row][col-1].setPiece(Position.WHITE);
							positionsArr[row][col-2].setPiece(Position.WHITE);
						}
						else if (positionsArr[row+3][col].getPiece() == Position.WHITE && positionsArr[row+1][col].getPiece() == Position.BLACK && positionsArr[row+2][col].getPiece() == Position.BLACK) { // south
							positionsArr[row+1][col].setPiece(Position.WHITE);
							positionsArr[row+2][col].setPiece(Position.WHITE);
						}
						else if (positionsArr[row-3][col].getPiece() == Position.WHITE && positionsArr[row-1][col].getPiece() == Position.BLACK && positionsArr[row-2][col].getPiece() == Position.BLACK) { // north
							positionsArr[row-1][col].setPiece(Position.WHITE);
							positionsArr[row-2][col].setPiece(Position.WHITE);
						}
					}
					catch (IndexOutOfBoundsException e){
						positionsArr[row][col].getPiece();
					}
					
					try {  //FLIPS 3 DISKS TO BLACK
						
						if (positionsArr[row][col+4].getPiece() == Position.WHITE && positionsArr[row][col+1].getPiece() == Position.BLACK && positionsArr[row][col+2].getPiece() == Position.BLACK && positionsArr[row][col+3].getPiece() == Position.BLACK) {  // -->
							positionsArr[row][col+2].setPiece(Position.WHITE);
							positionsArr[row][col+3].setPiece(Position.WHITE);
							positionsArr[row][col+1].setPiece(Position.WHITE);
						}
						else if (positionsArr[row][col-4].getPiece() == Position.WHITE && positionsArr[row][col-1].getPiece() == Position.BLACK && positionsArr[row][col-2].getPiece() == Position.BLACK && positionsArr[row][col-3].getPiece() == Position.BLACK) { // <--
							positionsArr[row][col-1].setPiece(Position.WHITE);
							positionsArr[row][col-2].setPiece(Position.WHITE);
							positionsArr[row][col-3].setPiece(Position.WHITE);
						}
						else if (positionsArr[row+4][col].getPiece() == Position.WHITE && positionsArr[row+1][col].getPiece() == Position.BLACK && positionsArr[row+2][col].getPiece() == Position.BLACK  && positionsArr[row+3][col].getPiece() == Position.BLACK) { // south
							positionsArr[row+1][col].setPiece(Position.WHITE);
							positionsArr[row+2][col].setPiece(Position.WHITE);
							positionsArr[row+3][col].setPiece(Position.WHITE);
						}
						else if (positionsArr[row-4][col].getPiece() == Position.WHITE && positionsArr[row-1][col].getPiece() == Position.BLACK && positionsArr[row-2][col].getPiece() == Position.BLACK  && positionsArr[row-3][col].getPiece() == Position.BLACK) { // north
							positionsArr[row-1][col].setPiece(Position.WHITE);
							positionsArr[row-2][col].setPiece(Position.WHITE);
							positionsArr[row-3][col].setPiece(Position.WHITE);
						}
					}
					catch (IndexOutOfBoundsException e){
						positionsArr[row][col].getPiece();
					}
					
					try {  //FLIPS 4 DISKS TO BLACK
						
						if (positionsArr[row][col+5].getPiece() == Position.WHITE && positionsArr[row][col+1].getPiece() == Position.BLACK && positionsArr[row][col+2].getPiece() == Position.BLACK && positionsArr[row][col+3].getPiece() == Position.BLACK && positionsArr[row][col+4].getPiece() == Position.BLACK) {  // -->
							positionsArr[row][col+1].setPiece(Position.WHITE);
							positionsArr[row][col+2].setPiece(Position.WHITE);
							positionsArr[row][col+3].setPiece(Position.WHITE);
							positionsArr[row][col+4].setPiece(Position.WHITE);
						}
						else if (positionsArr[row][col-5].getPiece() == Position.WHITE && positionsArr[row][col+1].getPiece() == Position.BLACK && positionsArr[row][col+2].getPiece() == Position.BLACK && positionsArr[row][col+3].getPiece() == Position.BLACK && positionsArr[row][col-4].getPiece() == Position.BLACK) {  // -->
							positionsArr[row][col-1].setPiece(Position.WHITE);
							positionsArr[row][col-2].setPiece(Position.WHITE);
							positionsArr[row][col-3].setPiece(Position.WHITE);
							positionsArr[row][col-4].setPiece(Position.WHITE);
						}
						else if (positionsArr[row+5][col].getPiece() == Position.WHITE && positionsArr[row+1][col].getPiece() == Position.BLACK && positionsArr[row+2][col].getPiece() == Position.BLACK  && positionsArr[row+3][col].getPiece() == Position.BLACK && positionsArr[row+4][col].getPiece() == Position.BLACK) { // south
							positionsArr[row+1][col].setPiece(Position.WHITE);
							positionsArr[row+2][col].setPiece(Position.WHITE);
							positionsArr[row+3][col].setPiece(Position.WHITE);
							positionsArr[row+4][col].setPiece(Position.WHITE);
						}
						else if (positionsArr[row-5][col].getPiece() == Position.WHITE && positionsArr[row-1][col].getPiece() == Position.BLACK && positionsArr[row-2][col].getPiece() == Position.BLACK  && positionsArr[row-3][col].getPiece() == Position.BLACK && positionsArr[row-4][col].getPiece() == Position.BLACK) { // north
							positionsArr[row-1][col].setPiece(Position.WHITE);
							positionsArr[row-2][col].setPiece(Position.WHITE);
							positionsArr[row-3][col].setPiece(Position.WHITE);
							positionsArr[row-4][col].setPiece(Position.WHITE);
						}
					}
					catch (IndexOutOfBoundsException e){
						positionsArr[row][col].getPiece();
					}
					
					try {  //FLIPS 5 DISKS TO BLACK
						
						if (positionsArr[row][col+6].getPiece() == Position.WHITE && positionsArr[row][col+1].getPiece() == Position.BLACK && positionsArr[row][col+2].getPiece() == Position.BLACK && positionsArr[row][col+3].getPiece() == Position.BLACK && positionsArr[row][col+4].getPiece() == Position.BLACK && positionsArr[row][col+5].getPiece() == Position.BLACK) {  // -->
							positionsArr[row][col+1].setPiece(Position.WHITE);
							positionsArr[row][col+2].setPiece(Position.WHITE);
							positionsArr[row][col+3].setPiece(Position.WHITE);
							positionsArr[row][col+4].setPiece(Position.WHITE);
							positionsArr[row][col+5].setPiece(Position.WHITE);
						}
						else if (positionsArr[row][col-6].getPiece() == Position.WHITE && positionsArr[row][col+1].getPiece() == Position.BLACK && positionsArr[row][col+2].getPiece() == Position.BLACK && positionsArr[row][col+3].getPiece() == Position.BLACK && positionsArr[row][col-4].getPiece() == Position.BLACK && positionsArr[row][col-5].getPiece() == Position.BLACK) {  // -->
							positionsArr[row][col-1].setPiece(Position.WHITE);
							positionsArr[row][col-2].setPiece(Position.WHITE);
							positionsArr[row][col-3].setPiece(Position.WHITE);
							positionsArr[row][col-4].setPiece(Position.WHITE);
							positionsArr[row][col-5].setPiece(Position.WHITE);
						}
						else if (positionsArr[row+6][col].getPiece() == Position.WHITE && positionsArr[row+1][col].getPiece() == Position.BLACK && positionsArr[row+2][col].getPiece() == Position.BLACK  && positionsArr[row+3][col].getPiece() == Position.BLACK && positionsArr[row+4][col].getPiece() == Position.BLACK && positionsArr[row+5][col].getPiece() == Position.BLACK) { // south
							positionsArr[row+1][col].setPiece(Position.WHITE);
							positionsArr[row+2][col].setPiece(Position.WHITE);
							positionsArr[row+3][col].setPiece(Position.WHITE);
							positionsArr[row+4][col].setPiece(Position.WHITE);
							positionsArr[row+5][col].setPiece(Position.WHITE);
						}
						else if (positionsArr[row-6][col].getPiece() == Position.WHITE && positionsArr[row-1][col].getPiece() == Position.BLACK && positionsArr[row-2][col].getPiece() == Position.BLACK  && positionsArr[row-3][col].getPiece() == Position.BLACK && positionsArr[row-4][col].getPiece() == Position.BLACK && positionsArr[row-5][col].getPiece() == Position.BLACK) { // north
							positionsArr[row-1][col].setPiece(Position.WHITE);
							positionsArr[row-2][col].setPiece(Position.WHITE);
							positionsArr[row-3][col].setPiece(Position.WHITE);
							positionsArr[row-4][col].setPiece(Position.WHITE);
							positionsArr[row-5][col].setPiece(Position.WHITE);
						}
					}
					catch (IndexOutOfBoundsException e){
						positionsArr[row][col].getPiece();
					}
					
					try {  //FLIPS 6 DISKS TO BLACK
						
						if (positionsArr[row][col+7].getPiece() == Position.WHITE && positionsArr[row][col+1].getPiece() == Position.BLACK && positionsArr[row][col+2].getPiece() == Position.BLACK && positionsArr[row][col+3].getPiece() == Position.BLACK && positionsArr[row][col+4].getPiece() == Position.BLACK && positionsArr[row][col+5].getPiece() == Position.BLACK && positionsArr[row][col+6].getPiece() == Position.WHITE) {  // -->
							positionsArr[row][col+1].setPiece(Position.WHITE);
							positionsArr[row][col+2].setPiece(Position.WHITE);
							positionsArr[row][col+3].setPiece(Position.WHITE);
							positionsArr[row][col+4].setPiece(Position.WHITE);
							positionsArr[row][col+5].setPiece(Position.WHITE);
							positionsArr[row][col+6].setPiece(Position.WHITE);
						}
						else if (positionsArr[row][col-7].getPiece() == Position.WHITE && positionsArr[row][col+1].getPiece() == Position.BLACK && positionsArr[row][col+2].getPiece() == Position.BLACK && positionsArr[row][col+3].getPiece() == Position.BLACK && positionsArr[row][col-4].getPiece() == Position.BLACK && positionsArr[row][col-5].getPiece() == Position.BLACK && positionsArr[row][col-6].getPiece() == Position.WHITE) {  // -->
							positionsArr[row][col-1].setPiece(Position.WHITE);
							positionsArr[row][col-2].setPiece(Position.WHITE);
							positionsArr[row][col-3].setPiece(Position.WHITE);
							positionsArr[row][col-4].setPiece(Position.WHITE);
							positionsArr[row][col-5].setPiece(Position.WHITE);
							positionsArr[row][col-6].setPiece(Position.WHITE);
						}
						else if (positionsArr[row+7][col].getPiece() == Position.WHITE && positionsArr[row+1][col].getPiece() == Position.BLACK && positionsArr[row+2][col].getPiece() == Position.BLACK  && positionsArr[row+3][col].getPiece() == Position.BLACK && positionsArr[row+4][col].getPiece() == Position.BLACK && positionsArr[row+5][col].getPiece() == Position.BLACK && positionsArr[row+6][col].getPiece() == Position.WHITE) { // south
							positionsArr[row+1][col].setPiece(Position.WHITE);
							positionsArr[row+2][col].setPiece(Position.WHITE);
							positionsArr[row+3][col].setPiece(Position.WHITE);
							positionsArr[row+4][col].setPiece(Position.WHITE);
							positionsArr[row+5][col].setPiece(Position.WHITE);
							positionsArr[row+6][col].setPiece(Position.WHITE);
						}
						else if (positionsArr[row-7][col].getPiece() == Position.WHITE && positionsArr[row-1][col].getPiece() == Position.BLACK && positionsArr[row-2][col].getPiece() == Position.BLACK  && positionsArr[row-3][col].getPiece() == Position.BLACK && positionsArr[row-4][col].getPiece() == Position.BLACK && positionsArr[row-5][col].getPiece() == Position.BLACK && positionsArr[row-6][col].getPiece() == Position.WHITE) { // north
							positionsArr[row-1][col].setPiece(Position.WHITE);
							positionsArr[row-2][col].setPiece(Position.WHITE);
							positionsArr[row-3][col].setPiece(Position.WHITE);
							positionsArr[row-4][col].setPiece(Position.WHITE);
							positionsArr[row-5][col].setPiece(Position.WHITE);
							positionsArr[row-6][col].setPiece(Position.WHITE);
						}
					}
					catch (IndexOutOfBoundsException e){
						positionsArr[row][col].getPiece();
					}
			}
		}

	}
	


