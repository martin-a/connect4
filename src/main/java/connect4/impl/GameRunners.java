package connect4.impl;

import connect4.GameRunner;

public class GameRunners {
    private GameRunners() {
    }

    public static GameRunner newGameRunner() {
        return new GameRunnerImpl(new RuleManagerImpl());
    }

}
