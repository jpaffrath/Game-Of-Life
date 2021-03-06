import java.io.IOException;

public class Main {
	private static String inputFile = "";
	private static String outputFile = "";
	private static int velocity = 200;
	private static int size = 50;
	private static int generations = -1;
	private static int live = 30;
	
	/**
	 * Tries to parse a given string into an integer 
	 * 
	 * @param arg a string containing an integer
	 * @return the parsed integer
	 */
	private static int parseIntegerArgument(String arg) {
		int ret = 0;
		
		try {
			ret = Integer.parseInt(arg);
		}
		catch (NumberFormatException e) {
			System.out.println("Argument " + arg + " is not a valid number!");
			System.exit(0);
		}
		
		if (ret <= 0) {
			System.out.println("Negative numbers are not allowed!");
			System.exit(0);
		}
		
		return ret;
	}
	
	/**
	 * Parses the provided command line arguments
	 * 
	 * @param args the command line arguments
	 */
	private static void parseArgs(String [] args) {
		if (args.length <= 0) return;
		
		for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
			case "--file":
			case "-f":
				inputFile = args[++i]; break;
			
			case "--velocity":
			case "-v":
				velocity = parseIntegerArgument(args[++i]); break;
			
			case "--size":
			case "-s":
				size = parseIntegerArgument(args[++i]); break;
			
			case "--generations":
			case "-g":
				generations = parseIntegerArgument(args[++i]); break;
			
			case "--living":
			case "-l":
				live = parseIntegerArgument(args[++i]); break;
			
			case "--output":
			case "-o":
				outputFile = args[++i]; break;
			}
		}
	}
	
	/**
	 * Program entry point
	 * 
	 * @param args provided command line arguments
	 * @throws java.lang.InterruptedException
	 * @throws IOException
	 */
	public static void main(String[] args) throws java.lang.InterruptedException, IOException {
		parseArgs(args);
		
		// initialize a new game either with a provided file or random
		GameOfLife life;
		if (inputFile.isEmpty()) {
			if (live > 100) {
				System.out.println("Amount of live " + live + " is out of range! (0-100)");
				System.exit(0);
			}
			
			life = new GameOfLife(size, live);
		}
		else {
			life = new GameOfLife(inputFile);
		}
		
		life.drawWorld();
		
		// if no maximum amount of generations is provided, loop forever
		if (generations == -1) {
			while (true) {
				Thread.sleep(velocity);
				life.nextGeneration();
				life.drawWorld();
			}
		}
		// loop only the provided number of generations and stop
		else {
			for (int i = 0; i < generations; i++) {
				Thread.sleep(velocity);
				life.nextGeneration();
				life.drawWorld();
			}
			
			// if an output file is provided, write current world to the file
			if (outputFile.isEmpty() == false) {
				life.writeWorldToFile(outputFile);
			}
		}
	}
}