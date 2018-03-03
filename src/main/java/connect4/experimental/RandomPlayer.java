package connect4.experimental;

import connect4.Color;
import connect4.MoveValidator;
import connect4.Player;
import connect4.Result;

import java.util.Random;

/**
 * A bot selecting random valid moves
 */
public class RandomPlayer implements Player {
    private final Random random;
    private final MoveValidator moveValidator;
    private Color color;
    private Result result;


    public RandomPlayer(Random random, MoveValidator moveValidator) {
        this.random = random;
        this.moveValidator = moveValidator;
    }

    @Override
    public void notifyGameStart(Color color) {
        this.color = color;
    }

    @Override
    public int chooseColumn(Color[][] table) {
        int move;
        do {
            int[] validRange = moveValidator.validRange();
            move = random.nextInt(validRange[1] - validRange[0]) + validRange[0];
        } while (!moveValidator.isValidMove(table, move));
        return move;
    }

    @Override
    public void notifyGameEnd(Result result, Color[][] table) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }
}
