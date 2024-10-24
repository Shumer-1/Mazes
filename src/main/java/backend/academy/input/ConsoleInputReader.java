package backend.academy.input;

import backend.academy.elements.Coordinates;
import backend.academy.maze.Maze;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleInputReader implements InputReader {
    private final Scanner scanner;

    public ConsoleInputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public int[] getMazeParams() {
        int height;
        int width;

        String input = scanner.nextLine();

        if (!Pattern.matches("\\d+ \\d+", input)) {
            height = Maze.DEFAULT_HEIGHT;
            width = Maze.DEFAULT_WIDTH;
        } else {
            String[] strings = input.split(" ");
            try {
                height = Integer.parseInt(strings[0]);
                width = Integer.parseInt(strings[1]);
            } catch (NumberFormatException e) {
                height = Maze.DEFAULT_HEIGHT;
                width = Maze.DEFAULT_WIDTH;
            }

        }

        return new int[] {height, width};
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public Coordinates[] getPathParams() throws InputMismatchException {
        String input = scanner.nextLine();

        if (!Pattern.matches("\\d+ \\d+ \\d+ \\d+", input)) {
            throw new InputMismatchException("Coordinates must be positive integer");
        }
        String[] strings = input.split(" ");

        int startY = Integer.parseInt(strings[0]);
        int startX = Integer.parseInt(strings[1]);
        int endY = Integer.parseInt(strings[2]);
        int endX = Integer.parseInt(strings[3]);

        return new Coordinates[] {new Coordinates(startY, startX), new Coordinates(endY, endX)};
    }

    @Override
    public boolean getContinueInfo() {
        String answer = scanner.nextLine();
        while (!answer.equals("yes") && !answer.equals("no")) {
            answer = scanner.nextLine();
        }
        return answer.equals("yes");
    }

    @Override
    public String getSpecificGenerator() {
        String answer = scanner.nextLine();
        while (!answer.equals("prim") && !answer.equals("dfs")) {
            answer = scanner.nextLine();
        }
        return answer;
    }

    @Override
    public String getSpecificSolver() {
        String answer = scanner.nextLine();
        while (!answer.equals("dijkstra") && !answer.equals("aStar")) {
            answer = scanner.nextLine();
        }
        return answer;
    }
}
