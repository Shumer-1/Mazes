package backend.academy.input;

import backend.academy.elements.Coordinates;
import java.util.InputMismatchException;
import java.util.Set;

public interface InputReader {
    int[] getMazeParams();

    Coordinates[] getPathParams() throws InputMismatchException;

    boolean getContinueInfo();

    String getSpecificGenerator(Set<String> generatorNumbers);

    String getSpecificSolver(Set<String> solverNumbers);
}
