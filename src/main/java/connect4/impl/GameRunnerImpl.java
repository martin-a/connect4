package connect4.impl;

import connect4.*;

class GameRunnerImpl implements GameRunner {
    private final RuleManager ruleManager;

    GameRunnerImpl(RuleManager ruleManager) {
        this.ruleManager = ruleManager;
    }

    @Override
    public void run(Player redPlayer, Player greenPlayer) {
        redPlayer.notifyGameStart(Color.RED);
        greenPlayer.notifyGameStart(Color.GREEN);
        resume(redPlayer, greenPlayer, ruleManager.newTable());
    }

    @Override
    public void resume(Player redPlayer, Player greenPlayer, Color[][] table) {
        Color turn = getTurnColor(table);
        Result result;
        do {
            Player currentPlayer = turn == Color.GREEN ? greenPlayer : redPlayer;
            Player otherPlayer = turn == Color.RED ? greenPlayer : redPlayer;
            int column = currentPlayer.chooseColumn(table);
            result = ruleManager.updateTable(table, turn, column);
            turn = turn == Color.GREEN ? Color.RED : Color.GREEN;
            currentPlayer.notifyGameEnd(result, table);
            otherPlayer.notifyGameEnd(result.oppositeResult(), table);
        } while (result == Result.CONTINUE);

    }

    /**
     * Get the color of the player to play next move.
     * Assumes first player to play on an empty table is always the red one.
     */
    private static Color getTurnColor(Color[][] table) {
        int greenCells = 0;
        int redCells = 0;
        for (int i = 0; i < table.length; i++) {
            Color[] rows = table[i];
            for (int j = 0; j < rows.length; j++) {
                if (rows[j] == Color.GREEN) {
                    greenCells++;
                } else if (rows[j] == Color.RED) {
                    redCells++;
                }
            }
        }
        Color turn = Color.RED;
        if (redCells > greenCells) {
            turn = Color.GREEN;
        }
        return turn;
    }

    @Override
    public RuleManager getRuleManager() {
        return ruleManager;
    }
}
