package backend.academy.maze;

import backend.academy.elements.Cell;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class Maze {
    private final int height;
    private final int width;
    private final Cell[][] grid;
    public static final int DEFAULT_HEIGHT = 10;
    public static final int DEFAULT_WIDTH = 20;
    public static final int MAX_HEIGHT = 40;
    public static final int MAX_WIDTH = 80;
    public static final int MIN_HEIGHT = 3;
    public static final int MIN_WIDTH = 3;
}
