
public class UnplayablePosition extends Position{

	final static char UNPLAYABLE = '*';
	
	 public boolean taken(Position positionsArr[][], int row, int col) {
	    	
	    	if ((positionsArr[row][col].getPiece() == Position.BLACK) || (positionsArr[row][col].getPiece() == Position.WHITE) || (positionsArr[row][col].getPiece() == UnplayablePosition.UNPLAYABLE)) {
	    		return true; //position is taken
	    	}
	    	else {
	    		return false; //position is empty
	    	}
	    }
	
	public boolean canPlay(Position positionsArr[][], int row, int col) { //this will be for the positions that a player is not allowed to use to play during their turn
		
		
		if (taken(positionsArr, row, col) == false &&    positionsArr[row+1][col].getPiece() == Position.EMPTY &&	
				positionsArr[row-1][col].getPiece() == Position.EMPTY &&
				positionsArr[row][col+1].getPiece() == Position.EMPTY &&
				positionsArr[row][col-1].getPiece() == Position.EMPTY &&
				positionsArr[row+1][col+1].getPiece() == Position.EMPTY &&
				positionsArr[row-1][col-1].getPiece() == Position.EMPTY &&
				positionsArr[row+1][col-1].getPiece() == Position.EMPTY &&
				positionsArr[row-1][col+1].getPiece() == Position.EMPTY) {
			return false;	//if user tries to place a disk in the middle of an empty space w nothing around it
		}
		return true;
		
		
	}
	
	
}
