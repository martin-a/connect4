package connect4;

/**
 * In charge of running a game between players up to the end.
 */
public interface GameRunner {

    /**
     * Run a new game between two players
     *
     * @param redPlayer   the first player to play
     * @param greenPlayer the second player to play
     */
    void run(Player redPlayer, Player greenPlayer);


    /**
     * Resumes a game between two players
     *
     * @param redPlayer   the first player to play
     * @param greenPlayer the second player to play
     * @param table       the state of the table when game was interrupted.
     */
    void resume(Player redPlayer, Player greenPlayer, Color[][] table);

    /**
     * @return The class managing rules of the game
     */
    RuleManager getRuleManager();
}
