package backend.academy.session;

import backend.academy.elements.Cell;
import backend.academy.elements.Coordinates;
import backend.academy.generators.Generator;
import backend.academy.input.InputReader;
import backend.academy.maze.Maze;
import backend.academy.renderers.Renderer;
import backend.academy.solvers.Solver;
import backend.academy.writers.Writer;
import java.util.InputMismatchException;
import java.util.List;

public class Session {
    private Writer writer;
    private Renderer renderer;
    private InputReader inputReader;
    private Generator generator;
    private Solver solver;
    private String pathInfo = """
        Please, enter Path`s parameters
        (start and end coordinates like '1 1 2 2').
        Value must be greater 0 and less maze parameters.
        """;
    private String wallInfo = "You chose cells-wall.\n";
    private int mazeHeight = Maze.DEFAULT_HEIGHT;
    private int mazeWidth = Maze.DEFAULT_WIDTH;

    public Session(
        Writer writer, Renderer renderer, InputReader inputReader,
        Generator generator, Solver solver
    ) {
        this.writer = writer;
        this.renderer = renderer;
        this.inputReader = inputReader;
        this.generator = generator;
        this.solver = solver;
    }

    public void startSession() {
        writer.writeMessage(
            """
                Please, enter the maze parameters (height and width).
                If you enter non-positive integer values, the maze will have the default parameters (10x20).
                If you enter too large an integer, the maze will have the maximum parameters.
                If you enter too small an integer, the maze will have the minimum parameters.
                Maximum height - 40.
                Maximum width - 80.
                Minimum height - 3.
                Minimum width – 3.
                """
        );
        int[] mazeParams = inputReader.getMazeParams();
        int height = mazeParams[0];
        int width = mazeParams[1];

        writer.writeMessage("Maze is " + height + "x" + width + "\n");

        Maze maze = generator.generate(height, width);
        renderer.render(maze);

        makePath(maze);
    }

    private void makePath(Maze maze) {
        writer.writeMessage(pathInfo);
        boolean flag = false;
        Coordinates[] startAndEndCoord = null;
        while (!flag) {
            try {
                startAndEndCoord = inputReader.getPathParams();
                if (isValidPathCoordinates(startAndEndCoord[0], startAndEndCoord[1])) {
                    flag = true;
                } else {
                    writer.writeMessage(pathInfo);
                }
            } catch (InputMismatchException e) {
                writer.writeMessage(pathInfo);
            }
        }
        Coordinates start = startAndEndCoord[0];
        Coordinates end = startAndEndCoord[1];

        if (isWallCells(maze, start, end)) {
            writer.writeMessage(wallInfo);
            if (willContinue()) {
                makePath(maze);
            }
            return;
        }

        List<Coordinates> coordinatesList = solver.solve(maze, start, end);
        renderer.render(maze, coordinatesList);
        if (willContinue()) {
            makePath(maze);
        }
    }

    private boolean willContinue() {
        writer.writeMessage("If you want see another path at this maze, please enter 'yes'. Else - 'no'.\n");
        return inputReader.getContinueInfo();
    }

    private boolean isWallCells(Maze maze, Coordinates start, Coordinates end) {
        return maze.grid()[start.y()][start.x()] == Cell.WALL || maze.grid()[end.y()][end.x()] == Cell.WALL;
    }

    private boolean isValidPathCoordinates(Coordinates start, Coordinates end) {
        return start.y() < mazeHeight && start.x() < mazeWidth
            && end.y() < mazeHeight && end.x() < mazeWidth;
    }
}
