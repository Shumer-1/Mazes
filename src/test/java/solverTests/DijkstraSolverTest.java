package solverTests;

import backend.academy.elements.Cell;
import backend.academy.elements.Coordinates;
import backend.academy.maze.Maze;
import backend.academy.solvers.DijkstraSolver;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class DijkstraSolverTest {

    private static final String GRID_NO_PATH = """
        🌳🌳🌳🌳🌳
        🌳🟫🟫🌳🌳
        🌳🌳🌳🟫🌳
        🌳🟫🟫🟫🌳
        🌳🌳🌳🌳🌳
        """;

    private static final String GRID_PRIORITY_PATH = """
        🌳🌳🌳🌳🌳
        🌳🟫🟫🌊🌳
        🌳🟫🟫🟫🌳
        🌳🪙🟫🟫🌳
        🌳🌳🌳🌳🌳
        """;

    private static final String GRID_BIG_PATH = """
        🌳🌳🌳🌳🌳🌳
        🌳🟫🌳🟫🌳🌳
        🌳🟫🌳🟫🟫🌳
        🌳🟫🌳🌳🟫🌳
        🌳🟫🟫🟫🟫🌳
        🌳🌳🌳🌳🌳🌳
        """;

    @ParameterizedTest
    @MethodSource("getArgumentsForSolveTest")
    void solveTest(Maze maze, Coordinates start, Coordinates end, List<Coordinates> expectedResult) {
        var solver = new DijkstraSolver();

        List<Coordinates> actualResult = solver.solve(maze, start, end);

        assertThat(expectedResult.size()).isEqualTo(actualResult.size());

        boolean flag = true;
        for (int i = 0; i < expectedResult.size(); i++) {
            if (!expectedResult.get(i).equals(actualResult.get(i))) {
                flag = false;
                break;
            }
        }

        assertThat(flag).isEqualTo(true);
    }

    private static Stream<Arguments> getArgumentsForSolveTest() {
        return Stream.of(
            Arguments.of(new Maze(5, 5, convertGridToCells(GRID_NO_PATH)),
                new Coordinates(1, 1), new Coordinates(3, 3), new ArrayList<Coordinates>()),
            Arguments.of(new Maze(5, 5, convertGridToCells(GRID_PRIORITY_PATH)),
                new Coordinates(1, 1), new Coordinates(3, 3), List.of(new Coordinates(1, 1),
                    new Coordinates(2, 1), new Coordinates(3, 1), new Coordinates(3, 2),
                    new Coordinates(3, 3))),
            Arguments.of(new Maze(5, 5, convertGridToCells(GRID_PRIORITY_PATH)),
                new Coordinates(3, 3), new Coordinates(1, 1), List.of(new Coordinates(3, 3),
                    new Coordinates(3, 2), new Coordinates(3, 1), new Coordinates(2, 1),
                    new Coordinates(1, 1))),
            Arguments.of(new Maze(5, 5, convertGridToCells(GRID_NO_PATH)),
                new Coordinates(0, 0), new Coordinates(3, 3), new ArrayList<Coordinates>()),
            Arguments.of(new Maze(5, 5, convertGridToCells(GRID_BIG_PATH)),
                new Coordinates(1, 1), new Coordinates(1, 3), List.of(new Coordinates(1, 1),
                    new Coordinates(2, 1), new Coordinates(3, 1), new Coordinates(4, 1),
                    new Coordinates(4, 2), new Coordinates(4, 3), new Coordinates(4, 4),
                    new Coordinates(3, 4), new Coordinates(2, 4),
                    new Coordinates(2, 3), new Coordinates(1, 3)))
        );
    }

    public static Cell[][] convertGridToCells(String grid) {
        String[] rows = grid.split("\n");

        int height = rows.length;
        int width = rows[0].strip().codePointCount(0, rows[0].strip().length());

        Cell[][] cellGrid = new Cell[height][width];

        for (int i = 0; i < height; i++) {
            String row = rows[i].strip();
            int[] codePoints = row.codePoints().toArray();

            for (int j = 0; j < width; j++) {
                String symbol = new String(Character.toChars(codePoints[j]));

                if (symbol.equals(Cell.WALL.cellImage())) {
                    cellGrid[i][j] = Cell.WALL;
                } else if (symbol.equals(Cell.USUAL.cellImage())) {
                    cellGrid[i][j] = Cell.USUAL;
                } else if (symbol.equals(Cell.SWAMP.cellImage())) {
                    cellGrid[i][j] = Cell.SWAMP;
                } else if (symbol.equals(Cell.COIN.cellImage())) {
                    cellGrid[i][j] = Cell.COIN;
                }
            }
        }

        return cellGrid;
    }

}

