package connect4.impl;

import connect4.Color;
import connect4.MoveValidator;
import connect4.Player;
import connect4.Result;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A player taking its input from a {@link Scanner}
 */
class ScannerPlayer implements Player {
    private final Scanner scanner;
    private final PrintStream errorStream;
    private final MoveValidator moveValidator;

    /**
     * @param scanner       The input scanner
     * @param errorStream   Stream to output errors when input invalid. Will not close.
     * @param moveValidator Move validator on the player side
     */
    ScannerPlayer(Scanner scanner, PrintStream errorStream, MoveValidator moveValidator) {
        this.scanner = scanner;
        this.errorStream = errorStream;
        this.moveValidator = moveValidator;
    }

    @Override
    public void notifyGameStart(Color color) {
        //Do nothing
    }

    @Override
    public int chooseColumn(Color[][] table) {
        int res = -1;
        while (res == -1) {
            try {
                int input = scanner.nextInt();
                res = input - 1;
                if (!moveValidator.isValidMove(table, res)) {
                    errorStream.println("Invalid move: " + input);
                    res = -1;
                }
            } catch (InputMismatchException e) {
                errorStream.println("Invalid input: " + scanner.next());
            }

        }
        return res;
    }

    @Override
    public void notifyGameEnd(Result result, Color[][] table) {
        //Do nothing
    }
}
