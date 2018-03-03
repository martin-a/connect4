package connect4.impl;

import connect4.Color;
import connect4.MoveValidator;
import connect4.Player;
import connect4.Result;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StateDisplayingPlayerTest {

    private static final Color[][] EMPTY_TABLE = new Color[0][0];

    @Mock
    private MoveValidator moveValidator;
    @Mock
    private Player delegate;
    private ByteArrayOutputStream out;
    private StateDisplayingPlayer stateDisplayingPlayer;
    private PrintStream outStream;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        out = new ByteArrayOutputStream();
        outStream = new PrintStream(out);

        when(moveValidator.validRange()).thenReturn(new int[]{4, 10});
        stateDisplayingPlayer = new StateDisplayingPlayer(outStream, "test", delegate, false, moveValidator);
    }

    @Test
    public void test_notifyGameStart() {
        stateDisplayingPlayer.notifyGameStart(Color.RED);
        verify(delegate).notifyGameStart(Color.RED);
        assertThat(getOutput()).isEmpty();
    }

    @Test
    public void test_chooseColumn_printTable() {
        Color[][] table = TableTestUtil.createTable("" +
                "-G-" +
                "R--", 2, 3
        );
        stateDisplayingPlayer.chooseColumn(table);
        verify(delegate).chooseColumn(table);
        assertThat(getOutput()).startsWith("" +
                "| |G| |\n" +
                "|R| | |\n");
    }

    @Test
    public void test_chooseColumn_askForInputWithRange() {
        stateDisplayingPlayer.setColor(Color.GREEN);
        stateDisplayingPlayer.chooseColumn(EMPTY_TABLE);
        verify(delegate).chooseColumn(EMPTY_TABLE);
        assertThat(getOutput()).endsWith("test [GREEN] - choose column(5-10): ");
    }


    @Test
    public void test_notifyGameEnd_wins() {
        stateDisplayingPlayer.setColor(Color.GREEN);
        stateDisplayingPlayer.notifyGameEnd(Result.WIN, EMPTY_TABLE);
        verify(delegate).notifyGameEnd(Result.WIN, EMPTY_TABLE);
        assertThat(getOutput()).contains("test [GREEN] wins!");
    }

    @Test
    public void test_notifyGameEnd_draws() {
        stateDisplayingPlayer.setColor(Color.RED);
        stateDisplayingPlayer.notifyGameEnd(Result.DRAW, EMPTY_TABLE);
        verify(delegate).notifyGameEnd(Result.DRAW, EMPTY_TABLE);
        assertThat(getOutput()).contains("test [RED] draws");
    }


    private final String getOutput() {
        outStream.flush();
        //Normalize new lines
        return out.toString().replaceAll("\\R", "\n");
    }
}