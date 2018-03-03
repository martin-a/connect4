package connect4.impl;

import connect4.GameRunner;
import connect4.MoveValidator;
import connect4.Player;
import connect4.RuleManager;
import connect4.experimental.MonteCarloPlayer;
import connect4.experimental.RandomPlayer;

import java.util.Random;
import java.util.Scanner;

public class Players {
    private Players() {
    }

    public static Player newHumanPlayer(String name, MoveValidator moveValidator) {
        return new StateDisplayingPlayer(System.out, name,
                new ScannerPlayer(new Scanner(System.in), System.out, moveValidator),
                false, moveValidator);
    }


    /**
     * @deprecated experimental
     */
    @Deprecated
    public static Player newRandomAIPlayer(String name, MoveValidator moveValidator) {
        return new StateDisplayingPlayer(System.out, name,
                new RandomPlayer(new Random(), moveValidator),
                true, moveValidator);
    }

    /**
     * @deprecated experimental
     */
    @Deprecated
    public static Player newMonteCarloAIPlayer(String name, GameRunner gameRunner) {
        RuleManager ruleManager = gameRunner.getRuleManager();
        return new StateDisplayingPlayer(System.out, name,
                new MonteCarloPlayer(gameRunner, new Random(), ruleManager, 1_000),
                true, ruleManager);
    }
}
