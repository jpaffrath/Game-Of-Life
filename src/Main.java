import java.io.IOException;

public class Main {
	private static String file = "";
	private static int velocity = 200;
	private static int size = 50;
	private static int generations = -1;
	private static int live = 30;
	
	private static int parseIntegerArgument(String arg) {
		int ret = 0;
		
		try {
			ret = Integer.parseInt(arg);
		}
		catch (NumberFormatException e) {
			System.out.println("Argument " + arg + " is not a number!");
			System.exit(0);
		}
		
		return ret;
	}
	
	private static void parseArgs(String [] args) {
		if (args.length <= 0) return;
		
		for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
			case "--file":
			case "-f":
				file = args[++i]; break;
			
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
			}
		}
	}
	
	public static void main(String[] args) throws java.lang.InterruptedException, IOException {
		parseArgs(args);
		
		GameOfLife life;
		if (file.isEmpty()) {
			life = new GameOfLife(size, live);
		}
		else {
			life = new GameOfLife(file);
		}
		
		life.drawWorld();
		
		if (generations == -1) {
			while (true) {
				Thread.sleep(velocity);
				life.nextGeneration();
				life.drawWorld();
			}
		}
		else {
			for (int i = 0; i < generations; i++) {
				Thread.sleep(velocity);
				life.nextGeneration();
				life.drawWorld();
			}
		}
	}
}