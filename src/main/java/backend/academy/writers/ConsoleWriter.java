package backend.academy.writers;

import java.io.PrintStream;

public class ConsoleWriter implements Writer {
    private final PrintStream printer;

    public ConsoleWriter(PrintStream printStream) {
        printer = printStream;
    }

    @Override
    public void writeMessage(String message) {
        printer.print(message);
    }
}
