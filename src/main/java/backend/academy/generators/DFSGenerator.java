package backend.academy.generators;

import backend.academy.elements.Cell;
import backend.academy.maze.Maze;
import java.util.Random;

public class DFSGenerator implements Generator {
    private static final double COIN_PROB = 0.2;
    private static final double SWAMP_PROB = 0.2;
    private static boolean[][] isVisited;
    private final Random random;

    public DFSGenerator(Random random) {
        this.random = random;
    }

    @Override
    public Maze generate(int height, int width) {
        Cell[][] maze = new Cell[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                maze[i][j] = Cell.WALL;
            }
        }
        isVisited = new boolean[height][width];

        generatePaths(maze, 1, 1);

        placeCoinsAndSwamps(maze);

        return new Maze(height, width, maze);
    }

    private void generatePaths(Cell[][] maze, int y, int x) {
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        shuffleArray(directions);

        isVisited[y][x] = true;
        maze[y][x] = Cell.USUAL;

        for (int[] dir : directions) {
            int ny = y + dir[0] * 2;
            int nx = x + dir[1] * 2;

            if (ny == maze.length - 1 || nx == maze[0].length - 1) {
                maze[y + dir[0]][x + dir[1]] = Cell.USUAL;
            }
            if (ny > 0 && ny < maze.length - 1 && nx > 0 && nx < maze[0].length - 1 && !isVisited[ny][nx]) {
                maze[y + dir[0]][x + dir[1]] = Cell.USUAL;
                generatePaths(maze, ny, nx);
            }
        }
    }

    private void placeCoinsAndSwamps(Cell[][] maze) {
        for (int i = 1; i < maze.length - 1; i++) {
            for (int j = 1; j < maze[0].length - 1; j++) {
                if (maze[i][j] == Cell.USUAL) {
                    if (random.nextDouble() < COIN_PROB) {
                        maze[i][j] = Cell.COIN;
                    } else if (random.nextDouble() < SWAMP_PROB) {
                        maze[i][j] = Cell.SWAMP;
                    }
                }
            }
        }
    }

    private void shuffleArray(int[][] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int[] temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

}

