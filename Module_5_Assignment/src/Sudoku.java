public class Sudoku {

	// A method that detrmines whether its valid to place num at a certain square on
	// the puzzle
	public static boolean validPlacement(int num, int row, int column, int sudoku_puzzle[][]) {
		boolean isValid = true;

		// Checks if the number is already in the intended row
		for (int i = 0; i < sudoku_puzzle.length; i++) {
			if (sudoku_puzzle[row][i] == num) {
				return false;
			}
		}

		// Checks if the number is already in the intended column
		for (int j = 0; j < sudoku_puzzle[0].length; j++) {
			if (sudoku_puzzle[j][column] == num) {
				return false;
			}
		}

		// Finds the initial row and column of the sub-grid that we are checking
		// to see if the number we are trying to insert is already in this
		// block
		int sub_grid_row = (row / 3) * 3;
		int sub_grid_column = (column / 3) * 3;

		// 3 added to get the end of the row and col of the grid respectively
		for (int i = sub_grid_row; i < sub_grid_row + 3; i++) {
			for (int j = sub_grid_column; j < sub_grid_column + 3; j++) {
				if (sudoku_puzzle[i][j] == num) {
					return false;
				}
			}
		}

		return isValid;
	}

	// Takes the sudoku puzzle as a 2D array, and uses recursion to solve the puzzle
	public static boolean solve_Sudoku(int sudoku_puzzle[][]) {

		// Will hold the value of the current square for comparison during the solving
		// process
		int current_square;

		// Checks each square for a zero, and fills accordingly
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				current_square = sudoku_puzzle[i][j];

				// If the current square is empty, tries to fill it with a valid number
				if (current_square == 0) {
					for (int val = 1; val <= 9; val++) {
						// Checks if 1-9 valid at this position in the puzzle
						if (validPlacement(val, i, j, sudoku_puzzle) == true) {
							sudoku_puzzle[i][j] = val;

							// Calls the method recursively to check is the puzzle is solved
							// returning true.
							if (solve_Sudoku(sudoku_puzzle) == true) {
								return true;
							}

							// If the above is not true, we set arr[i][j] back to 0 in order to backtrack
							// see where we may have gone wrong with the puzzle
							sudoku_puzzle[i][j] = 0;
						}
					}
					// Returns false to indicate that the puzzle has not yet been solved
					// and further backtracking is needed to find a solution
					return false;
				}
			}
		}
		return true;
	}

	// A small function that runs solve_Sudoku on the given puzzle, and either
	// prints the solved puzzle or that the puzzle cannot be solved
	public static void run_Sudoku(int sudoku_puzzle[][]) {
		if (solve_Sudoku(sudoku_puzzle) == true) {
			// Prints out the solved puzzle
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					System.out.print(sudoku_puzzle[i][j] + " ");
				}
				System.out.println();
			}
			System.out.println();
		}

		// Will print out this message is the puzzle cannot be solved. Though this
		// is rare, cases exist, such as when not enough squares are filled initially
		else {
			System.out.println("This puzzle is invalid, and cannot be solved\n");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// A puzzles have 0s to mark empty spaces to be filled by 1-9
		int[][] sudoku_puzzle = { { 3, 0, 3, 8, 0, 1, 0, 0, 2 }, { 2, 0, 1, 0, 3, 0, 6, 0, 4 },
				{ 0, 0, 0, 2, 0, 4, 0, 0, 0 }, { 8, 0, 9, 0, 0, 0, 1, 0, 6 }, { 0, 6, 0, 0, 0, 0, 0, 5, 0 },
				{ 7, 0, 2, 0, 0, 0, 4, 0, 9 }, { 0, 0, 0, 5, 0, 9, 0, 0, 0 }, { 9, 0, 4, 0, 8, 0, 7, 0, 5 },
				{ 6, 0, 0, 1, 0, 7, 0, 0, 3 } };

		int[][] another_puzzle = { { 5, 3, 0, 0, 7, 0, 0, 0, 0 }, { 6, 0, 0, 1, 9, 5, 0, 0, 0 },
				{ 0, 9, 8, 0, 0, 0, 0, 6, 0 }, { 8, 0, 0, 0, 6, 0, 0, 0, 3 }, { 4, 0, 0, 8, 0, 3, 0, 0, 1 },
				{ 7, 0, 0, 0, 2, 0, 0, 0, 6 }, { 0, 6, 0, 0, 0, 0, 2, 8, 0 }, { 0, 0, 0, 4, 1, 9, 0, 0, 5 },
				{ 0, 0, 0, 0, 8, 0, 0, 7, 9 } };

		// A sudoku puzzle needs at least 17 filled squares to be solvable, so the below
		// will be declared invalid by the solver
		int[][] invalid_puzzle = { { 2, 0, 0, 0, 0, 0, 0, 0, 0 }, { 5, 2, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 8, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 3, 0, 0, 0, 0, 8, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 9, 0, 6, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 6, 0, 0, 0 } };

		// Please note that due to the recursive nature of the solve_Sudoku method, it
		// can take some time to finish executing once run
		run_Sudoku(sudoku_puzzle);
		run_Sudoku(another_puzzle);
		run_Sudoku(invalid_puzzle);
	}
}
