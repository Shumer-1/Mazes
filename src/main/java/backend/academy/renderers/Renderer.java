package backend.academy.renderers;

import backend.academy.elements.Coordinates;
import backend.academy.maze.Maze;
import java.util.List;

public interface Renderer {
    String PATH_IMAGE = "\uD83D\uDFE5";

    void render(Maze maze);

    void render(Maze maze, List<Coordinates> path);
}
