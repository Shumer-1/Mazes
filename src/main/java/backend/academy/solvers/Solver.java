package backend.academy.solvers;

import backend.academy.elements.Coordinates;
import backend.academy.maze.Maze;
import java.util.List;

public interface Solver {
    List<Coordinates> solve(Maze maze, Coordinates start, Coordinates end);
}
