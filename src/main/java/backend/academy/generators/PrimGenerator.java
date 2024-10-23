package backend.academy.generators;

import backend.academy.elements.Cell;
import backend.academy.maze.Maze;
import java.util.LinkedList;
import java.util.Random;

public class PrimGenerator implements Generator {
    private final double coinProb = 0.2;
    private final double swampProb = 0.2;
    private final double cycleProb = 0.2;
    private int height;
    private int width;
    private final Random random;
    private final int startRow = 1;
    private final int startCol = 1;
    private final int[] rowDirection = {-1, 0, 1, 0};
    private final int[] colDirection = {0, 1, 0, -1};
    private final int neighborsNumber = 4;

    public PrimGenerator(Random random) {
        this.random = random;
    }

    @SuppressWarnings("MagicNumber")
    public Maze generate(int height, int width) {
        LinkedList<int[]> wallList = new LinkedList<>();
        Cell[][] maze = new Cell[height][width];
        this.height = height;
        this.width = width;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                maze[i][j] = Cell.WALL;
            }
        }

        maze[startRow][startCol] = Cell.USUAL;
        addWalls(startRow, startCol, wallList, maze);

        while (!wallList.isEmpty()) {
            int[] wall = wallList.remove(random.nextInt(wallList.size()));

            int row = wall[0];
            int col = wall[1];

            int pathCount = 0;
            for (int i = 0; i < neighborsNumber; i++) {
                int newRow = row + rowDirection[i];
                int newCol = col + colDirection[i];

                if (newRow >= 0 && newRow < height && newCol >= 0 && newCol < width
                    && maze[newRow][newCol] == Cell.USUAL) {
                    pathCount++;
                }
            }

            if (pathCount < 2 || random.nextDouble() < cycleProb) {
                maze[row][col] = Cell.USUAL;
                addWalls(row, col, wallList, maze);
            }
        }

        placeCoinsAndSwamps(maze);

        return new Maze(height, width, maze);
    }

    private void addWalls(int row, int col, LinkedList<int[]> wallList, Cell[][] maze) {
        for (int i = 0; i < neighborsNumber; i++) {
            int newRow = row + rowDirection[i];
            int newCol = col + colDirection[i];

            if (newRow > 0 && newRow < height - 1 && newCol > 0 && newCol < width - 1
                && maze[newRow][newCol] == Cell.WALL) {
                wallList.add(new int[] {newRow, newCol});
            }
        }
    }

    private void placeCoinsAndSwamps(Cell[][] maze) {
        for (int i = 1; i < maze.length - 1; i++) {
            for (int j = 1; j < maze[0].length - 1; j++) {
                if (maze[i][j] == Cell.USUAL) {
                    if (random.nextDouble() < coinProb) {
                        maze[i][j] = Cell.COIN;
                    } else if (random.nextDouble() < swampProb) {
                        maze[i][j] = Cell.SWAMP;
                    }
                }
            }
        }
    }
}
