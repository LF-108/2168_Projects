import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class MazeGridPanel extends JPanel {
	private int rows;
	private int cols;
	private Cell[][] maze;

	// Extra credit
	public void generateMazeByDFS() {
		boolean[][] visited = new boolean[rows][cols];
		Stack<Cell> stack = new Stack<Cell>();
		Cell start = maze[0][0];
		stack.push(start);
		visited[0][0] = true;
		
		Cell current_cell = start;
		
		//While the stack is empty, keeps creating the maze
		while(stack.isEmpty()==false) {
			
			//Pops an element of the stack, and checks if it has any neighbors we can fill
			current_cell = stack.pop();
			
			//Checks for bounds and if southern square is visited, then marks as visited if not and pushes onto stack
			if(current_cell.row + 1 <= rows-1 && visited[current_cell.row+1][current_cell.col]==false) {
				
				visited[current_cell.row+1][current_cell.col]=true;
				   
				stack.push(maze[current_cell.row+1][current_cell.col]);			
			}
			
			//Checks for bounds and if norther square is visited, then marks as visited if not and pushes onto stack
			else if(current_cell.row - 1 >= 0 && visited[current_cell.row-1][current_cell.col]==false) {
				
				visited[current_cell.row-1][current_cell.col]=true;
				
				stack.push(maze[current_cell.row-1][current_cell.col]);
				
			}
			
			//Checks for bounds and if easter square is visited, then marks as visited if not and pushes onto stack
			else if(current_cell.col + 1 <= cols-1 && visited[current_cell.row][current_cell.col+1]==false) {
				
				visited[current_cell.row][current_cell.col+1]=true;
				
				stack.push(maze[current_cell.row][current_cell.col+1]);
			}
			
			//Checks for bounds and if western square is visited, then marks as visited if not and pushes onto stack
			else if(current_cell.col - 1 >= 0 && visited[current_cell.row][current_cell.col-1]==false) {
				
				visited[current_cell.row][current_cell.col-1]=true;
				
				stack.push(maze[current_cell.row][current_cell.col-1]);
			}
			
			//Once element in pushed, removes boundaries as needed and repaints. Doing it at the end prevents there being
	        //boundaries between all the squares in all directions
			
			//Removes western and eastern boundaries for west edge and east edge
			if(current_cell.row == 0 && current_cell.col > 0) {
		    	maze[current_cell.row][current_cell.col].westWall = false;
				maze[current_cell.row][current_cell.col - 1].eastWall = false;
		    }
			
			//Removes norther and southern boundaries for north and south edge
		    else if(current_cell.col == 0 && current_cell.row > 0) {
		    	maze[current_cell.row][current_cell.col].northWall = false;
				maze[current_cell.row - 1][current_cell.col].southWall = false;
		    }
			
			//Removes boundaries for interior squares
		    else if(current_cell.row > 0 && current_cell.col > 0) {
			boolean north = Math.random() < 0.5;
			if (north) {
				maze[current_cell.row][current_cell.col].northWall = false;
				maze[current_cell.row - 1][current_cell.col].southWall = false;
			} else {
				maze[current_cell.row][current_cell.col].westWall = false;
				maze[current_cell.row][current_cell.col - 1].eastWall = false;
			}
			maze[current_cell.row][current_cell.col].repaint();
		    }
			
			this.repaint();
		}	
		
		
	}

	public void solveMaze() {
		Stack<Cell> stack = new Stack<Cell>();
		Cell start = maze[0][0];
		start.setBackground(Color.GREEN);
		Cell finish = maze[rows - 1][cols - 1];
		finish.setBackground(Color.RED);
		stack.push(start);
        
		//Sets current_position to start (maze[0][0]) to start, then adjusts as we traverse through the maze
		Cell current_position = start;
		
		while(current_position!=finish && stack.isEmpty()==false) {
			
			//Finds the current position
			current_position = stack.peek();
			
			//Has south and west as the top two directions, then east and north after, checking for walls and repainting the maze as needed
			if(current_position.southWall == false && visited(current_position.row+1, current_position.col) == false) {
				stack.push(maze[current_position.row+1][current_position.col]);
				current_position.setBackground(Color.GREEN);
			}
			
			else if(current_position.eastWall == false && visited(current_position.row, current_position.col+1)== false) {
				stack.push(maze[current_position.row][current_position.col+1]);
				current_position.setBackground(Color.GREEN);
			}
			
			else if(current_position.westWall == false && visited(current_position.row, current_position.col-1)== false) {
				stack.push(maze[current_position.row][current_position.col-1]);
				current_position.setBackground(Color.GREEN);
			}
			
			else if(current_position.northWall == false && visited(current_position.row-1, current_position.col)== false) {
				stack.push(maze[current_position.row-1][current_position.col]);
				current_position.setBackground(Color.GREEN);
			}
			
			//If we hit a dead end and can't move, pops the last move off the stack and paints the cell blue so we know
			//not to return to this location
			else {
				current_position.setBackground(Color.BLUE);
				stack.pop();
			}	
		}
		start.setBackground(Color.GREEN);
		finish.setBackground(Color.RED);
	}

	public boolean visited(int row, int col) {
		Cell c = maze[row][col];
		Color status = c.getBackground();
		if (status.equals(Color.WHITE) || status.equals(Color.RED)) {
			return false;
		}

		return true;
	}

	public void generateMaze() {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if (row == 0 && col == 0) {
					continue;
				} else if (row == 0) {
					maze[row][col].westWall = false;
					maze[row][col - 1].eastWall = false;
				} else if (col == 0) {
					maze[row][col].northWall = false;
					maze[row - 1][col].southWall = false;
				} else {
					boolean north = Math.random() < 0.5;
					if (north) {
						maze[row][col].northWall = false;
						maze[row - 1][col].southWall = false;
					} else {
						maze[row][col].westWall = false;
						maze[row][col - 1].eastWall = false;
					}
					maze[row][col].repaint();
				}
			}
		}

		this.repaint();
	}

	public MazeGridPanel(int rows, int cols) {
		this.setPreferredSize(new Dimension(800, 800));
		this.rows = rows;
		this.cols = cols;
		this.setLayout(new GridLayout(rows, cols));
		this.maze = new Cell[rows][cols];
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				maze[row][col] = new Cell(row, col);
				this.add(maze[row][col]);
			}
		}
		this.generateMazeByDFS();
		//this.generateMaze();
		this.solveMaze();
	}
}
