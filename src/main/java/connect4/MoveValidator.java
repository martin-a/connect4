package connect4;

/**
 * Validator for correctness of the moves according to game rules
 */
public interface MoveValidator {
    /**
     * Return the range of moves allowed by the game rules
     *
     * @return Minimal column number (inclusive) as first element, Maximal column number (exclusive) as second element.
     */
    int[] validRange();

    /**
     * Check if the move is valid in consideration with the current state of the table
     *
     * @return <code>true</code> if move valid, <code>false</code> otherwise
     */
    boolean isValidMove(Color[][] table, int selectedColumn);
}
