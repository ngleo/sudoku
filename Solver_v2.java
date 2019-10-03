import java.util.*;

/**
 * Backtracking solution - always complete blanks with the fewest
 * number of choices first
 */
class Solver_v2 {
    public void solveSudoku(char[][] board) {
	backtrack(board);
    }
    
    public boolean backtrack(char[][] board) {
	// Find blank with few choices - If num of choices is 1, fill it and break
	int minChoiceI = 0;
	int minChoiceJ = 0;
	int minNumOfChoice = 10;
	List<Character> minChoices = new ArrayList<Character>();;

	for (int i = 0; i < 9; i++) {
	    for (int j = 0; j < 9; j++) {
		if (board[i][j] == '.') {
		    List<Character> choices = new ArrayList<>(
	                    Arrays.asList('1','2','3','4','5','6','7','8','9'));
		
		    checkRow(board, i, choices);
		    checkCol(board,  j, choices);
		    checkBox(board, i, j, choices);
		    
		    // Backtrack if no valid choices
		    if (choices.isEmpty()) {
			return false;
		    }
		    
		    // If 1 choice, fill it in and recurse
		    if (choices.size() == 1) {
			board[i][j] = choices.get(0);
			printBoard(board, i, j);
			
			if (backtrack(board) == false) {
			    board[i][j] = '.';
			    return false;
			} else {
			    return true;
			}
		    }
		    
		    // If 1+ choices, store choices and exit loop
		    if (choices.size() < minNumOfChoice) {
			minChoiceI = i;
			minChoiceJ = j;
			minNumOfChoice = choices.size();
			minChoices = choices;
		    }		    
		}

	    	// Base case: If there are not blanks/dots, minNumOfChoices will not change
		if (i == 8 && j == 8 && minNumOfChoice == 10) {
		    return true; 
		}
	    }
	}
	
	// Try every choice for the selected blank
	for (char choice : minChoices) {
	    board[minChoiceI][minChoiceJ] = choice;	    
	    printBoard(board, minChoiceI, minChoiceJ);

	    if (backtrack(board) == false) {
		board[minChoiceI][minChoiceJ] = '.';
	    } else {
		return true;
	    }
	}
	
	return false;
    }

    public void checkRow(char[][] board, int currRow, List<Character> choices) {
	for (char elem : board[currRow]) {
	    if (elem != '.' && choices.contains(elem)) {
		choices.remove((Character) elem);
	    }
	}
    }

    public void checkCol(char[][] board, int currCol, List<Character> choices) {
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

    public void printBoard(char[][] board, int currRow, int currCol) {
	System.out.println("Filling in row: " + (currRow + 1));	
	System.out.println("Filling in column: " + (currCol + 1));
	System.out.println();

	for (char[] row : board) {
	    for (char elem : row) {
		System.out.print(elem + "  ");
	    }
	    System.out.println();
	}	
	System.out.println();
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
	
	Solver_v2 solver = new Solver_v2();
	solver.printBoard(testBoard2, 0, 0);
	
	try {
	    Thread.sleep(100);
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