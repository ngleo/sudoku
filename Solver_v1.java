import java.util.*;

/**
 * Backtracking method - row by row 
 */*

class Solver_v1 {
    public void solveSudoku(char[][] board) {
	recurse(board, 0, 0);
    }
    
    public boolean recurse(char[][] board, int currRow, int currCol) {
	if (currRow == 8 && currCol == 8) return true;

	while (currRow <= 8 && currCol <= 8) {
	    if (board[currRow][currCol] == '.') break;
	    currCol++;
	    if (currCol > 8) {
	       	currCol = 0;
		currRow++;
	    }
	}

	if (currRow == 9 && currCol == 0) return true;

	System.out.println();
	System.out.println("Filling in row: " + (currRow + 1));	
	System.out.println("Filling in column: " + (currCol + 1));

	List<Character> choices = new ArrayList<>(
	       Arrays.asList('1','2','3','4','5','6','7','8','9'));
	
	checkRow(board, currRow, currCol, choices);
	checkCol(board, currRow, currCol, choices);
	checkBox(board, currRow, currCol, choices);

	if (choices.isEmpty()) {
	    return false;
	}

	for (char choice : choices) {
	    board[currRow][currCol] = choice;

	    printBoard(board);

	    if (recurse(board, currRow, currCol) == false) {
		board[currRow][currCol] = '.';
		continue;
	    } else {
		return true;
	    }
	}
	

	return false;
    }

    public void checkRow(char[][] board, int currRow, int currCol, List<Character> choices) {
	for (char elem : board[currRow]) {
	    if (elem != '.' && choices.contains(elem)) {
		choices.remove((Character) elem);
	    }
	}
    }

    public void checkCol(char[][] board, int currRow, int currCol, List<Character> choices) {
	for (char[] row : board) {
	    char elem = row[currCol];
	    if (elem != '.' && choices.contains(elem)) {
		choices.remove((Character) elem);
	    }
	} 
    }

    public void checkBox(char[][] board, int currRow, int currCol, List<Character> choices) {
	int beginRow = currRow / 3 * 3;
	int beginCol = currCol / 3 * 3;
	
	for (int row = 0; row < 3; row++) {
	    for (int col = 0; col < 3; col++) {
		char elem = board[beginRow + row][beginCol + col];
		if (elem != '.' && choices.contains(elem)) {
		    choices.remove((Character) elem);
		}
	    }
	}
    }

    public void printBoard(char[][] board) {
	for (char[] row : board) {
	    for (char elem : row) {
		System.out.print(elem + "  ");
	    }
	    System.out.println();
	}	
	System.out.print('\r');
    }

    public static void main(String[] args) {
	char[][] testBoard1 = {
	    {'5','3','.','.','7','.','.','.','.'},
	    {'6','.','.','1','9','5','.','.','.'},
	    {'.','9','8','.','.','.','.','6','.'},
	    {'8','.','.','.','6','.','.','.','3'},
	    {'4','.','.','8','.','3','.','.','1'},
       	    {'7','.','.','.','2','.','.','.','6'},
	    {'.','6','.','.','.','.','2','8','.'},
            {'.','.','.','4','1','9','.','.','5'},
	    {'.','.','.','.','8','.','.','7','9'}
	};

	char[][] testBoard2 = {
	    {'8','.','.','.','.','.','.','.','.'},
	    {'.','.','3','6','.','.','.','.','.'},
       	    {'.','7','.','.','9','.','2','.','.'},
	    {'.','5','.','.','.','7','.','.','.'},
            {'.','.','.','.','4','5','7','.','.'},
	    {'.','.','.','1','.','.','.','3','.'},
	    {'.','.','1','.','.','.','.','6','8'},
	    {'.','.','8','5','.','.','.','1','.'},
	    {'.','9','.','.','.','.','4','.','.'},
	};
	
	Solver solver = new Solver();
	solver.printBoard(testBoard2);
	
	try {
	    Thread.sleep(1000);
	} catch (InterruptedException e) {
	    Thread.currentThread().interrupt();
	}

	long startTime = System.nanoTime();
	solver.solveSudoku(testBoard2);	
	long endTime = System.nanoTime();
	double seconds = (double) (endTime - startTime) / 1000000000;
	System.out.println();
	System.out.println("Solver took " + seconds + " seconds");
    }
}