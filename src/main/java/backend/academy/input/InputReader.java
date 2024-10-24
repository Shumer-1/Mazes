package backend.academy.input;

import backend.academy.elements.Coordinates;
import java.util.InputMismatchException;

public interface InputReader {
    int[] getMazeParams();

    Coordinates[] getPathParams() throws InputMismatchException;

    boolean getContinueInfo();

    String getSpecificGenerator();

    String getSpecificSolver();
}
