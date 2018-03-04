package connect4.impl;

import connect4.Color;
import connect4.Player;
import connect4.Result;
import connect4.RuleManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class GameRunnerImplTest {
    @Mock
    private Player redP;
    @Mock
    private Player greenP;
    @Mock
    private RuleManager ruleManager;
    private GameRunnerImpl gameRunner;
    private Color[][] table;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        table = new Color[2][2];
        when(ruleManager.newTable()).thenReturn(table);

        // ruleManager mock defaults to win in 3 turns
        when(ruleManager.updateTable(any(), any(), anyInt())).thenReturn(
                Result.CONTINUE, Result.CONTINUE, Result.WIN);

        // red always returns column 0
        when(redP.chooseColumn(any())).thenReturn(0);
        // green always returns column 1
        when(greenP.chooseColumn(any())).thenReturn(1);

        gameRunner = new GameRunnerImpl(ruleManager);
    }

    @Test
    public void test_run_winningGame() {
        gameRunner.run(redP, greenP);

        InOrder inOrder = inOrder(redP, greenP, ruleManager);
        //Both players should be notified about game start
        inOrder.verify(redP).notifyGameStart(Color.RED);
        inOrder.verify(greenP).notifyGameStart(Color.GREEN);

        inOrder.verify(ruleManager).newTable();

        //red always starts
        inOrder.verify(redP).chooseColumn(table);
        inOrder.verify(ruleManager).updateTable(table, Color.RED, 0);
        inOrder.verify(greenP).chooseColumn(table);
        inOrder.verify(ruleManager).updateTable(table, Color.GREEN, 1);
        inOrder.verify(redP).chooseColumn(table);
        inOrder.verify(ruleManager).updateTable(table, Color.RED, 0);
        inOrder.verify(redP).notifyGameEnd(Result.WIN, table);
        inOrder.verify(greenP).notifyGameEnd(Result.LOST, table);

        verifyNoMoreInteractions(redP, greenP, ruleManager);
    }


    @Test
    public void test_run_drawGame() {
        when(ruleManager.updateTable(any(), any(), anyInt())).thenReturn(
                Result.CONTINUE, Result.CONTINUE, Result.DRAW);
        gameRunner.run(redP, greenP);

        InOrder inOrder = inOrder(redP, greenP, ruleManager);
        //Both players should be notified about game start
        inOrder.verify(redP).notifyGameStart(Color.RED);
        inOrder.verify(greenP).notifyGameStart(Color.GREEN);

        inOrder.verify(ruleManager).newTable();

        //red always starts
        inOrder.verify(redP).chooseColumn(table);
        inOrder.verify(ruleManager).updateTable(table, Color.RED, 0);
        inOrder.verify(greenP).chooseColumn(table);
        inOrder.verify(ruleManager).updateTable(table, Color.GREEN, 1);
        inOrder.verify(redP).chooseColumn(table);
        inOrder.verify(ruleManager).updateTable(table, Color.RED, 0);
        inOrder.verify(redP).notifyGameEnd(Result.DRAW, table);
        inOrder.verify(greenP).notifyGameEnd(Result.DRAW, table);

        verifyNoMoreInteractions(redP, greenP, ruleManager);
    }

    @Test
    public void test_resume_greenStarts() {
        table[0][0] = Color.RED;

        gameRunner.resume(redP, greenP, table);

        InOrder inOrder = inOrder(redP, greenP);
        //green starts
        inOrder.verify(greenP).chooseColumn(table);
        inOrder.verify(redP).chooseColumn(table);
        inOrder.verify(greenP).chooseColumn(table);

        //None of the player should be notified about game start
        verify(greenP, never()).notifyGameStart(any());
        verify(redP, never()).notifyGameStart(any());
    }

    @Test
    public void test_resume_redStarts() {
        table[0][0] = Color.RED;
        table[1][1] = Color.GREEN;

        gameRunner.resume(redP, greenP, table);

        InOrder inOrder = inOrder(redP, greenP);
        //green starts
        inOrder.verify(redP).chooseColumn(table);
        inOrder.verify(greenP).chooseColumn(table);
        inOrder.verify(redP).chooseColumn(table);

        //None of the player should be notified about game start
        verify(greenP, never()).notifyGameStart(any());
        verify(redP, never()).notifyGameStart(any());
    }
}