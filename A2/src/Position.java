
public class Position{ 

	private char piece;
	public static final char EMPTY = ' ';
	public static final char BLACK = 'B';
	public static final char WHITE = 'W';
	
	public char getPiece() {
        return this.piece;
    }

    public void setPiece(char piece) {
        this.piece = piece;
    }

	public boolean canPlay(Position positionsArr[][], int row, int col) {  //LEAVE IT AS DESCRIBED. POLYMORPHISM TO BE USED. THIS WILL BE USED TO KNOW INFO ABOUT THE POSITIONS OF THE BOARD
		
		if ((positionsArr[row][col].getPiece() == Position.BLACK) || (positionsArr[row][col].getPiece() == Position.WHITE) || (positionsArr[row][col].getPiece() == UnplayablePosition.UNPLAYABLE)) {
    		return false; //position is taken
    	}
    		return true; //position is empty
    }
	
}
