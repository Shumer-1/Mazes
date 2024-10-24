package backend.academy;

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
import java.util.Random;
import java.util.Scanner;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        var printer = new PrintStream(System.out);
        var inputReader = new ConsoleInputReader(new Scanner(System.in));
        var consoleRenderer = new ConsoleRenderer(printer);
        var writer = new ConsoleWriter(printer);

        writer.writeMessage("""
            Please, choose generator: enter 'prim' or 'dfs'.
            'prim' generates imperfect mazes.
            """);

        String generatorAnswer = inputReader.getSpecificGenerator();
        Generator generator;

        if (generatorAnswer.equals("prim")) {
            generator = new PrimGenerator(new Random(System.currentTimeMillis()));
        } else {
            generator = new DFSGenerator(new Random(System.currentTimeMillis()));
        }

        writer.writeMessage("""
            Please, choose solver: enter 'aStar' or 'dijkstra'.
            """);

        String solverAnswer = inputReader.getSpecificSolver();
        Solver solver;

        if (solverAnswer.equals("aStar")) {
            solver = new AStarSolver();
        } else {
            solver = new DijkstraSolver();
        }

        Session session = new Session(writer, consoleRenderer, inputReader, generator, solver);
        session.startSession();
    }
}
