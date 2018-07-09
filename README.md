# Conway's Game of Life
This is a simple implementation of Conway's Game of Life in Java.

# Usage
Start a new Game of Life by invoking the compiled .jar file. This will start the game with the default values. The default size of the board is 50 and the default velocity of the game cycles is 200ms. A random board will be initialized with a coverage of 30% of living cells.

# Arguments
This implementation understands several arguments covered below
* --file [-f] FILENAME.txt (initialize the board with the content of a file)
* --velocity [-v] VELOCITY (10-1000. Control the speed of a game cycle in milliseconds)
* --size [-s] SIZE (control the size of the board)
* --generations [-g] GENERATIONS (define the maximum generations to be played)
* --living [-l] LIVING (1-100. Control the percentage of living cells when initializing the board)

# Sample
Start the game with the explosion pattern
```
java -jar life.jar --file patterns/explosion.txt
```

Start a random game with a size of 100 and a velocity of 150 (ms)
```
java -jar life.jar --size 100 --velocity 150
```

Start a random game with a maximum of 10 generations and 20% living cells
```
java -jar life.jar --generations 10 --living 20
```
