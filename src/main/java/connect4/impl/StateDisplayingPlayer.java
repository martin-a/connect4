package connect4.impl;

import connect4.Color;
import connect4.MoveValidator;
import connect4.Player;
import connect4.Result;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Decorator class that displays the state of the game on a {@link PrintStream}
 */
class StateDisplayingPlayer implements Player {
    private final PrintStream printStream;
    private final String playerName;
    private final Player delegate;
    private final boolean printSelection;
    private final MoveValidator moveValidator;
    private Color color;

    /**
     * @param printStream    The output stream. Will not close.
     * @param playerName     The player name
     * @param delegate       Delegate player
     * @param printSelection <code>true</code> to echo the the delegate player move on the stream
     * @param moveValidator  Move validator on the player side
     */
    public StateDisplayingPlayer(PrintStream printStream, String playerName, Player delegate,
                                 boolean printSelection, MoveValidator moveValidator) {
        this.printStream = printStream;
        this.playerName = playerName;
        this.delegate = delegate;
        this.printSelection = printSelection;
        this.moveValidator = moveValidator;
    }

    @Override
    public void notifyGameStart(Color color) {
        this.color = color;
        delegate.notifyGameStart(color);
    }

    @Override
    public int chooseColumn(Color[][] table) {
        displayTable(table);
        printStream.print(String.format(
                "%s [%s] - choose column(%d-%d): ",
                playerName, color, moveValidator.validRange()[0] + 1, moveValidator.validRange()[1]));
        int selectedColumn = delegate.chooseColumn(table);
        if (printSelection) {
            printStream.println("* Selected column " + (selectedColumn + 1) + " *");
        }
        return selectedColumn;
    }

    private void displayTable(Color[][] table) {
        for (int i = 0; i < table.length; i++) {
            Color[] row = table[i];
            String rowString = Arrays.stream(row).map(cell -> {
                if (cell == null) {
                    return " ";
                }
                switch (cell) {
                    case RED:
                        return "R";
                    case GREEN:
                        return "G";
                    default:
                        return "?";
                }
            }).collect(Collectors.joining("|", "|", "|"));
            printStream.println(rowString);
        }
    }

    @Override
    public void notifyGameEnd(Result result, Color[][] table) {
        if (result == Result.WIN) {
            displayTable(table);
            printStream.println(playerName + " [" + color + "] wins!");
        }
        if (result == Result.DRAW) {
            displayTable(table);
            printStream.println(playerName + " [" + color + "] draws.");
        }
        delegate.notifyGameEnd(result, table);
    }

    void setColor(Color color) {
        this.color = color;
    }
}
