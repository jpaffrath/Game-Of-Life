import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GameOfLife {
	private static final char CHAR_CELL = '0';
	private static final char CHAR_EMPTY = '.';
	
	private boolean[][] world;
	private int size, live;
	private long generation;
	
	/**
	 * Creates a new object with a given size and a given chance of live
	 * 
	 * @param size square dimension of the world
	 * @param live amount of living cells in the world for initialization
	 */
	GameOfLife(int size, int live) {
		this.generation = 0;
		this.size = size;
		this.live = live;
		
		initializeWorld();
	}
	
	/**
	 * Creates a new object based on a given input file
	 * 
	 * @param filename path to the input file
	 * @throws IOException
	 */
	GameOfLife(String filename) throws IOException {
		this.generation = 0;
		initializeWorld(filename);
	}

	/**
	 * Initializes a new world with the given input parameters
	 * 
	 */
	private void initializeWorld(){
		this.world = new boolean[this.size][this.size];
		
		for (int row = 0; row < this.size; row++){
			for (int col = 0; col < this.size; col++){
				this.world[row][col] = (Math.random() < (this.live / 100.0));
			}
		}
	}
	
	/**
	 * Initializes a new world based on the given input file
	 * 
	 * @param filename path to the input file
	 * @throws IOException
	 */
	private void initializeWorld(String filename) throws IOException {
		List<String> content = Files.readAllLines(Paths.get(filename));
		
		this.size = Integer.parseInt(content.get(0));
		this.world = new boolean[this.size][this.size];
		
		for (int i = 1; i < content.size()-1; i++) {
			char[] row = content.get(i).toCharArray();
			
			for (int j = 0; j < row.length; j++) {
				if (row[j] == CHAR_CELL) {
					this.world[i][j] = true;
				}
				else {
					this.world[i][j] = false;
				}
			}
		}
	}

	/**
	 * Draws the current state of the world to the standard output
	 * 
	 */
	public void drawWorld(){
		// clears the terminal (works on Linux and Mac)
		System.out.print("\033[H\033[2J");
		
		for (int row = 0; row < this.size; row++) {
			for (int col = 0; col < this.size; col++ ) {
				 System.out.print(world[row][col] ? CHAR_CELL : CHAR_EMPTY);
				 System.out.print(' ');
			}
			System.out.println();
		}
		System.out.println("Generation " + generation);
	}

	/**
	 * Calculates the new generation based on the current generation in the world
	 * 
	 */
	public void nextGeneration(){
		boolean[][] newWorld = new boolean[this.size][this.size];
		
		for (int row = 0; row < newWorld.length; row++) {
			for (int col = 0; col < newWorld[row].length; col++) {
				newWorld[row][col] = isAlive(row, col);
			}
		}
		
		if (this.isStillLife(world, newWorld)) {
			System.out.println("Still life detected!");
			System.exit(0);
		}
		
		world = newWorld;
		generation++;
	}
	
	/**
	 * Checks if the current game is a still life
	 * 
	 * @param oldWorld the old world
	 * @param newWorld the new world
	 * @return true if the current game is a still life
	 */
	private boolean isStillLife(boolean[][] oldWorld, boolean[][] newWorld) {
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if (oldWorld[i][j] != newWorld[i][j]) {
					return false;
				}
			}
		}
		
		return true;
	}

	/**
	 * Calculates if a given cell is alive in the next generation
	 * 
	 * @param row row coordinate in the world for the current cell
	 * @param col column coordinate in the world for the current cell
	 * @return true if the given cell is alive
	 */
	private boolean isAlive(int row, int col){
		boolean curCel = world[row][col];
		int liveCount = 0;

		for (int r = -1; r <= 1; r++) {
			int currentRow = row + r;
			currentRow = (currentRow < 0) ? this.size - 1: currentRow;
			currentRow = (currentRow >= this.size) ? 0 : currentRow;
			
			for (int c = -1; c <= 1; c++) {
				int currentCol = col + c;
				currentCol = (currentCol < 0) ? this.size - 1: currentCol;
				currentCol = (currentCol >= this.size) ? 0 : currentCol;
				
				if (world[currentRow][currentCol]) {
					liveCount++;
				}
			}
		}

		// If the current cell was alive, subtract 1 because it was calculated to the liveCount
		if (curCel) {
			liveCount--;
		}
		
		/*
		 * Conway's four rules:
		 * 
		 * 1. Any live cell with fewer than two live neighbours dies (referred to as underpopulation or exposure[1]).
		 * 2. Any live cell with more than three live neighbours dies (referred to as overpopulation or overcrowding).
		 * 3. Any live cell with two or three live neighbours lives, unchanged, to the next generation.
		 * 4. Any dead cell with exactly three live neighbours will come to life.
		 * 
		 * http://www.conwaylife.com/w/index.php?title=Conway%27s_Game_of_Life#Rules
		 */
		if ((liveCount == 2 && curCel) || liveCount == 3) {
			return true;
		}
		
		return false;
	}
}