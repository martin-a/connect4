package connect4;

/**
 * A player of the game
 */
public interface Player {
    /**
     * Notifies the player the game starts
     *
     * @param color the color of the player
     */
    void notifyGameStart(Color color);

    /**
     * Asks the player for its next move
     *
     * @param table The game table. Must not be modified.
     * @return the selected move.
     */
    int chooseColumn(Color[][] table);

    /**
     * Notifies the player the game ends
     *
     * @param result the result of the game, from the player point of view
     * @param table  the game table
     */
    void notifyGameEnd(Result result, Color[][] table);
}
