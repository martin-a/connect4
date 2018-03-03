package connect4.experimental;

import connect4.*;

import java.util.Random;

/**
 * A simple AI using Monte Carlo rollouts. (This is not a MCTS implementation)
 */
public class MonteCarloPlayer implements Player {
    private final GameRunner gameRunner;
    private final Random random;
    private final RuleManager ruleManager;
    private final int rollouts;
    private Color color;


    public MonteCarloPlayer(GameRunner gameRunner, Random random, RuleManager ruleManager, int rollouts) {
        this.gameRunner = gameRunner;
        this.random = random;
        this.ruleManager = ruleManager;
        this.rollouts = rollouts;
    }

    @Override
    public void notifyGameStart(Color color) {
        this.color = color;
    }

    @Override
    public int chooseColumn(Color[][] table) {
        int maxWeight = -Integer.MAX_VALUE;
        int bestColumn = -1;
        int[] validRange = ruleManager.validRange();
        for (int i = validRange[0]; i < validRange[1]; i++) {
            int weight = doRollouts(i, table);
            if (weight > maxWeight) {
                bestColumn = i;
                maxWeight = weight;
            }
        }
        return bestColumn;
    }

    private int doRollouts(int selectedColumn, Color[][] table) {
        int weight = 0;
        for (int i = 0; i < rollouts; i++) {
            Result result = doRollout(selectedColumn, table);
            if (result == Result.WIN) {
                weight++;
            } else if (result == Result.LOST) {
                weight--;
            }
        }
        return weight;
    }

    /**
     * play game until the end from current state
     */
    private Result doRollout(int selectedColumn, Color[][] table) {
        //Clone the table (we do not want to alter current game table)
        Color[][] clone = new Color[table.length][];
        for (int i = 0; i < table.length; i++) {
            clone[i] = table[i].clone();
        }
        Result result = ruleManager.updateTable(clone, color, selectedColumn);
        if (result != Result.CONTINUE) {
            return result;
        }

        // resume game using cloned table
        RandomPlayer red = new RandomPlayer(random, ruleManager);
        RandomPlayer green = new RandomPlayer(random, ruleManager);
        gameRunner.resume(red, green, clone);
        if (color == Color.RED) {
            return red.getResult();
        }
        return green.getResult();
    }

    @Override
    public void notifyGameEnd(Result result, Color[][] table) {
        //Do nothing
    }
}
