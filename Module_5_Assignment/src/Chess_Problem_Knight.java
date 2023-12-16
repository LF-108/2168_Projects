import java.util.Random;
import java.lang.Math;
import java.util.ArrayList;

public class Chess_Problem_Knight {

	// Checks if all the squares on the chess board have been visited
	public static boolean allVisited(boolean visited[][]) {
		boolean allVisited = true;
		for (int i = 0; i < visited.length; i++) {
			for (int j = 0; j < visited[0].length; j++) {
				if (visited[i][j] == false) {
					return false;
				}
			}
		}
		return allVisited;
	}

	// Checks that the move we want to make is valid, in that we have not already
	// been to this square or the row or column we are trying to access is not outside 
	// range of rows and columns (negative or exceeding 7 in this case)
	
	public static boolean isValidMove(int row_to_move, int col_to_move, boolean visited[][]) {
		boolean isValid = false;
		if ((row_to_move >= 0 && row_to_move <= 7) && (col_to_move >= 0 && col_to_move <= 7)
				&& visited[row_to_move][col_to_move] == false) {
			isValid = true;
		}
		return isValid;
	}

	/*
	 * Adds a string element to the ArrayList shows the knight's traversal along the
	 * chessboard. By taking in the adding the base string that contains the random
	 * initial location and adding each square as we go along, we can update the
	 * ArrayList using this method within the solve_knights_tour and return an up to
	 * date ArrayList
	 */
	public static ArrayList<String> display(String to_add, ArrayList<String> visited_string) {

		// Adds the newest visited square to the ArrayList
		visited_string.add(to_add);
		return visited_string;
	}

	public static boolean solve_knights_tour(int current_row, int current_col, boolean visited[][],
			String chess_board[][], ArrayList<String> result) {

		/*
		 * Stores all the ways a knight can move along a row and column respectively.
		 * For example, taking the first elements of row_move and col_move would
		 * represent the knight moving up two rows and one column right or the current
		 * row + 2 and the current column + 1. We will loop through all eight of these
		 * possibilities to find a valid move
		 */

		int row_Move[] = { 2, 2, 1, 1, -1, -1, -2, -2 };
		int col_Move[] = { 1, -1, 2, -2, 2, -2, 1, -1 };

		// Checks if every square on the board has been covered, returning true if this
		// is so
		if (allVisited(visited) == true) {
			return true;
		}

		// Checks if any of the 8 possible moves for the knight are valid (using the two
		// arrays above)
		for (int x = 0; x < 8; x++) {

			// Checks if the move set at x is valid, and adjusts the current row and column
			// accordingly is this is so
			if (isValidMove(current_row + row_Move[x], current_col + col_Move[x], visited) == true) {
				visited[current_row + row_Move[x]][current_col + col_Move[x]] = true;
				current_row = current_row + row_Move[x];
				current_col = current_col + col_Move[x];

				// Sets the arraylist containing the list of chess squares visited to display
				// so that the arraylist is altered as needed
				result = display(chess_board[current_row][current_col], result);

				// Uses recursion to check if we have solved the puzzle with the previous move
				if (solve_knights_tour(current_row, current_col, visited, chess_board, result) == true) {
					return true;

				}

				/*
				 * If we have not, we mark the space on visited[][] that corresponds to the
				 * space we moved as not visited, and readjust the current row and column
				 * accordingly. Removes the string representation of this square from the array
				 * as well
				 */
				else {

					// If the element was not visited due to the puzzle not being solved yet, we
					// remove
					// the element we added to the list while initially checking for a valid move
					result.remove(chess_board[current_row][current_col]);

					// Mark the square we moved to as not visited, and reassigns the values of the
					// row and column the knight is currently on
					visited[current_row][current_col] = false;
					current_row = current_row - row_Move[x];
					current_col = current_col - col_Move[x];

				}

			}

		}
		// If the puzzle is unsolvable for a certain starting point, false is returned
		// to signify that there is no solution
		return false;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Declares a 2D array that holds a string representation of the chessboard
		// positions
		String[][] chess_board = new String[8][8];

		// Decarles a 2D array that marks what squares on the chessboard have been
		// visited
		boolean[][] visited = new boolean[8][8];

		// Fills all visited[][] with value false for all elements
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				visited[i][j] = false;
			}
		}

		// Fills chess_board with string representation of chessboard locations. These
		// will be added to the visited_string arraylist as the knight visits the squares 
		// of the board
		for (int i = 0; i < 8; i++) {
			for (int j = 1; j <= 8; j++) {
				// uses the wrapper class of int to use its toString method to create a string
				// representation of a square on the board. x represents the int value in a
				// square
				// designation. ex.the 7 in a7
				Integer x = j;

				// Converts i to a char. 97 is the int representation of a, so converts values
				// from 0+97 to 7+97 to get the a-h in chess notation.
				char y = (char) (i + 97);

				// Sets a position on the chessboard to its chess notation by adding the two
				// parts created above
				chess_board[i][j - 1] = y + x.toString();
			}
		}

		// Generates the randomly the row and column that the knight will be starting
		// at. Generates random number between 0-7
		int knight_row = (int) Math.floor(Math.random() * (8));
		int knight_column = (int) Math.floor(Math.random() * (8));

		// Marks the square we initially place the knight at as visited
		visited[knight_row][knight_column] = true;

		/*
		 * A string that will keep track of what positions on the chessboard the knight
		 * has visited so far starts with the position that we initially place the
		 * knight at, and will be added to as we traverse the board
		 */
		ArrayList<String> visited_string = new ArrayList<String>();
		visited_string.add(chess_board[knight_row][knight_column]);

		// Checks if every square on the board has been covered, returning true if this
		// is so, and false if
		// otherwise. Prints out a respond accordingly
		if (solve_knights_tour(knight_row, knight_column, visited, chess_board, visited_string) == true) {
			System.out.println("Found solution:");
		} else {
			System.out.println("No solution");
		}

		// Prints out the solution. Please note that due to the recursive nature of the
		// problem it can take some time for the process to finish executing
		for (int i = 0; i < visited_string.size(); i++) {
			if (i % 7 == 0 && i != 0) {
				System.out.print((i + 1) + "." + visited_string.get(i) + " \n\n");
			} else {
				System.out.print((i + 1) + "." + visited_string.get(i) + " ");
			}
		}

	}

}
