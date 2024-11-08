package backend.academy.game;

import backend.academy.generators.DFSGenerator;
import backend.academy.generators.Generator;
import backend.academy.generators.PrimGenerator;
import backend.academy.input.ConsoleInputReader;
import backend.academy.renderers.ConsoleRenderer;
import backend.academy.session.Session;
import backend.academy.solvers.AStarSolver;
import backend.academy.solvers.DijkstraSolver;
import backend.academy.solvers.Solver;
import backend.academy.writers.ConsoleWriter;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private final Map<String, Generator> generatorMap = new HashMap<>() {{
        put("1", new PrimGenerator(new Random(System.currentTimeMillis())));
        put("2", new DFSGenerator(new Random(System.currentTimeMillis())));
    }};
    private final Map<String, Solver> solverMap = new HashMap<>() {{
        put("1", new AStarSolver());
        put("2", new DijkstraSolver());
    }};

    public void startGame() {
        var printer = new PrintStream(System.out);
        var inputReader = new ConsoleInputReader(new Scanner(System.in));
        var consoleRenderer = new ConsoleRenderer(printer);
        var writer = new ConsoleWriter(printer);

        writer.writeMessage("""
            Please, choose generator: enter '1' (prim) or '2' (dfs).
            '1' generates imperfect mazes.
            """);

        String generatorAnswer = inputReader.getSpecificGenerator(generatorMap.keySet());
        Generator generator = generatorMap.get(generatorAnswer);

        writer.writeMessage("""
            Please, choose solver: enter '1' (aStar) or '2' (dijkstra).
            """);

        String solverAnswer = inputReader.getSpecificSolver(solverMap.keySet());
        Solver solver = solverMap.get(solverAnswer);

        Session session = new Session(writer, consoleRenderer, inputReader, generator, solver);
        session.startSession();
    }
}

