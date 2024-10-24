package inputTests;

import backend.academy.elements.Coordinates;
import backend.academy.input.ConsoleInputReader;
import backend.academy.maze.Maze;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ConsoleInputReaderTest {
    private final static Scanner MOCK_SCANNER = Mockito.mock(Scanner.class);

    @ParameterizedTest
    @MethodSource("getArgumentsForGetMazeParamsTest")
    void getMazeParamsTest(String inputValue, int[] expectedResult) {
        Mockito.when(MOCK_SCANNER.nextLine()).thenReturn(inputValue);
        var inputReader = new ConsoleInputReader(MOCK_SCANNER);

        int[] actualResult = inputReader.getMazeParams();

        assertAll(
            () -> assertThat(actualResult[0]).isEqualTo(expectedResult[0]),
            () -> assertThat(actualResult[1]).isEqualTo(expectedResult[1])
        );
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForGetPathParamsTest")
    void getPathParamsTest(String inputValue, Coordinates[] expectedResult, boolean willException) {
        Mockito.when(MOCK_SCANNER.nextLine()).thenReturn(inputValue);
        var inputReader = new ConsoleInputReader(MOCK_SCANNER);

        if (willException) {
            assertThrows(Exception.class, () -> {
                Coordinates[] actualResult = inputReader.getPathParams();
            });
        } else {
            Coordinates[] actualResult = inputReader.getPathParams();

            assertAll(
                () -> assertThat(actualResult[0]).isEqualTo(expectedResult[0]),
                () -> assertThat(actualResult[1]).isEqualTo(expectedResult[1])
            );
        }
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForGetContinueInfoTest")
    void getContinueInfoTest(List<String> inputValues, boolean expectedResult) {
        var mockSequence = Mockito.when(MOCK_SCANNER.nextLine()).thenReturn(inputValues.getFirst());
        for (int i = 1; i < inputValues.size(); i++) {
            mockSequence.thenReturn(inputValues.get(i));
        }
        var inputReader = new ConsoleInputReader(MOCK_SCANNER);

        boolean actualResult = inputReader.getContinueInfo();

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForGetSpecificGeneratorTest")
    void getSpecificGeneratorTest(List<String> inputValues, String expectedResult){
        var mockSequence = Mockito.when(MOCK_SCANNER.nextLine()).thenReturn(inputValues.getFirst());
        for (int i = 1; i < inputValues.size(); i++) {
            mockSequence.thenReturn(inputValues.get(i));
        }
        var inputReader = new ConsoleInputReader(MOCK_SCANNER);

        String actualResult = inputReader.getSpecificGenerator();

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForGetSpecificSolverTest")
    void getSpecificSolverTest(List<String> inputValues, String expectedResult){
        var mockSequence = Mockito.when(MOCK_SCANNER.nextLine()).thenReturn(inputValues.getFirst());
        for (int i = 1; i < inputValues.size(); i++) {
            mockSequence.thenReturn(inputValues.get(i));
        }
        var inputReader = new ConsoleInputReader(MOCK_SCANNER);

        String actualResult = inputReader.getSpecificSolver();

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> getArgumentsForGetSpecificSolverTest() {
        return Stream.of(
            Arguments.of(List.of("dikstra", "a*****", "dijkstra", "dfs", "?"), "dijkstra"),
            Arguments.of(List.of("a*", "aStar", "dijkstra", "prim", "!"), "aStar"),
            Arguments.of(List.of("aStar"), "aStar"),
            Arguments.of(List.of("dijkstra"), "dijkstra")
        );
    }

    private static Stream<Arguments> getArgumentsForGetSpecificGeneratorTest() {
        return Stream.of(
            Arguments.of(List.of("pram", "ghl", "prim", "dfs", "NO!!"), "prim"),
            Arguments.of(List.of("dfs", "ssd", "prim", "prim", "prim!!"), "dfs"),
            Arguments.of(List.of("dfs"), "dfs"),
            Arguments.of(List.of("prim"), "prim")
        );
    }

    private static Stream<Arguments> getArgumentsForGetContinueInfoTest() {
        return Stream.of(
            Arguments.of(List.of("a", "b", "yes", "no", "NO!!"), true),
            Arguments.of(List.of("no", "yes", "yes", "no", "NO!!"), false),
            Arguments.of(List.of("yes"), true),
            Arguments.of(List.of("no"), false)
        );
    }

    private static Stream<Arguments> getArgumentsForGetMazeParamsTest() {
        return Stream.of(
            Arguments.of("adssds asda adasd asd asd a", new int[] {Maze.DEFAULT_HEIGHT, Maze.DEFAULT_WIDTH}),
            Arguments.of("1 1", new int[] {Maze.MIN_HEIGHT, Maze.MIN_WIDTH}),
            Arguments.of("1000 1000", new int[] {Maze.MAX_HEIGHT, Maze.MAX_WIDTH}),
            Arguments.of("150 50", new int[] {Maze.MAX_HEIGHT, 50}),
            Arguments.of("39 39", new int[] {39, 39}),
            Arguments.of("0 -1", new int[] {Maze.DEFAULT_HEIGHT, Maze.DEFAULT_WIDTH}),
            Arguments.of("10000000000000000000 1000000000000000", new int[] {Maze.DEFAULT_HEIGHT, Maze.DEFAULT_WIDTH})
        );
    }

    private static Stream<Arguments> getArgumentsForGetPathParamsTest() {
        return Stream.of(
            Arguments.of("as asd as sd", null, true),
            Arguments.of("1000000000000000000000 1 1 1", null, true),
            Arguments.of("as 1 2 3", null, true),
            Arguments.of("100 100 100 100", new Coordinates[] {new Coordinates(100, 100),
                new Coordinates(100, 100)}, false),
            Arguments.of("-1 -1 -100 -100", null, true),
            Arguments.of("10 10 20 20", new Coordinates[] {new Coordinates(10, 10),
                new Coordinates(20, 20)}, false)
        );
    }

}
