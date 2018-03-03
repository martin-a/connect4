package connect4;

import connect4.impl.GameRunners;
import connect4.impl.Players;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        GameRunner gameRunner = GameRunners.newGameRunner();
        RuleManager ruleManager = gameRunner.getRuleManager();

        Player humanPlayer1 = Players.newHumanPlayer("Player 1", ruleManager);
        if (Arrays.stream(args).anyMatch(s -> s.equals("--random-ai"))) {
            gameRunner.run(humanPlayer1,
                    Players.newRandomAIPlayer("Bot 2 (Random)", ruleManager));
        } else if (Arrays.stream(args).anyMatch(s -> s.equals("--mc-ai"))) {
            gameRunner.run(humanPlayer1,
                    Players.newMonteCarloAIPlayer("Bot 2 (MonteCarlo)", gameRunner));
        } else if (Arrays.stream(args).anyMatch(s -> s.equals("--ai-only"))) {
            gameRunner.run(Players.newMonteCarloAIPlayer("Bot 1 (MonteCarlo)", gameRunner),
                    Players.newMonteCarloAIPlayer("Bot 2 (MonteCarlo)", gameRunner));
        } else {
            gameRunner.run(humanPlayer1, Players.newHumanPlayer("Player 2", ruleManager));
        }
    }
}
