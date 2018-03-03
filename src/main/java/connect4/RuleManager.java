package connect4;

/**
 * Manages rules of the game
 */
public interface RuleManager extends MoveValidator {
    /**
     * Creates a 2d table for a new game.
     * <p>
     * Accessing specific cell: <code>table[row][column]</code>
     * With <code>table[0][0]</code> the top-left cell.
     */
    Color[][] newTable();

    /**
     * Update the table with specified move.
     *
     * @param table          the game table
     * @param color          the color of the player playing
     * @param selectedColumn the selected move
     * @return the game state after the move according to the rules
     */
    Result updateTable(Color[][] table, Color color, int selectedColumn);
}
