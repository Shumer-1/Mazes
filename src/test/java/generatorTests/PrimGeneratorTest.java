package generatorTests;

import backend.academy.elements.Cell;
import backend.academy.generators.PrimGenerator;
import backend.academy.maze.Maze;
import java.util.Random;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class PrimGeneratorTest {
    private final Random random = Mockito.mock(Random.class);
    private final PrimGenerator generator = new PrimGenerator(random);

    @ParameterizedTest
    @MethodSource("getArgumentsForGenerateParamsTest")
    void generateParamsTest(int height, int width) {
        Mockito.when(random.nextDouble()).thenReturn(0.5);
        Mockito.when(random.nextInt()).thenReturn(1);

        Maze maze = generator.generate(height, width);

        assertAll(
            () -> assertThat(maze.height()).isEqualTo(height),
            () -> assertThat(maze.width()).isEqualTo(width)
        );
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForGenerateCorrectBordersTest")
    void generateCorrectBordersTest(int height, int width) {
        Mockito.when(random.nextDouble()).thenReturn(0.5);
        Mockito.when(random.nextInt()).thenReturn(1);

        Maze maze = generator.generate(height, width);
        boolean flag = true;
        for (int i = 0; i < maze.height(); i++) {
            for (int j = 0; j < maze.width(); j++) {
                if (i == 0 || j == 0 || i == maze.height() - 1
                    || j == maze.width() - 1) {
                    flag = flag && maze.grid()[i][j] == Cell.WALL;
                }
            }
        }

        assertThat(flag).isEqualTo(true);
    }

    private static Stream<Arguments> getArgumentsForGenerateCorrectBordersTest() {
        return Stream.of(
            Arguments.of(10, 10),
            Arguments.of(20, 20),
            Arguments.of(30, 30)
        );
    }

    private static Stream<Arguments> getArgumentsForGenerateParamsTest() {
        return Stream.of(
            Arguments.of(10, 10),
            Arguments.of(20, 20),
            Arguments.of(30, 30)
        );
    }
}
