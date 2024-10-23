package backend.academy.renderers;

import backend.academy.elements.Cell;
import backend.academy.elements.Coordinates;
import backend.academy.maze.Maze;
import java.io.PrintStream;
import java.util.List;

public class ConsoleRenderer implements Renderer {

    private final PrintStream printer;

    public ConsoleRenderer(PrintStream printStream) {
        printer = printStream;
    }

    @Override
    public void render(Maze maze) {
        for (Cell[] row : maze.grid()) {
            for (Cell cell : row) {
                printer.print(cell.cellImage());
            }
            printer.println();
        }
    }

    @Override
    public void render(Maze maze, List<Coordinates> path) {
        for (int i = 0; i < maze.height(); i++) {
            for (int j = 0; j < maze.width(); j++) {
                if (path.contains(new Coordinates(i, j))) {
                    printer.print(PATH_IMAGE);
                } else {
                    printer.print(maze.grid()[i][j].cellImage());
                }
            }
            printer.println();
        }
    }
}
