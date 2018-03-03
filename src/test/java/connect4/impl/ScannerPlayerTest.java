package connect4.impl;

import connect4.Color;
import connect4.MoveValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

public class ScannerPlayerTest {
    private static final Color[][] TABLE = new Color[0][0];

    @Mock
    private MoveValidator moveValidator;
    private ByteArrayOutputStream out;
    private PrintStream errorStream;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        out = new ByteArrayOutputStream();
        errorStream = new PrintStream(out);

        when(moveValidator.isValidMove(any(), anyInt())).thenReturn(true);
    }

    private ScannerPlayer createPlayer(String inputs) {
        return new ScannerPlayer(new Scanner(inputs), errorStream, moveValidator);
    }

    @Test
    public void test_takeFromInput_convertTo0Base() {
        ScannerPlayer player = createPlayer("1\n3\ninvalid\n  7  \n");
        assertThat(player.chooseColumn(TABLE)).isEqualTo(0);
        assertThat(player.chooseColumn(TABLE)).isEqualTo(2);
        assertThat(player.chooseColumn(TABLE)).isEqualTo(6);
    }


    @Test
    public void test_invalidInput_NaN() {
        ScannerPlayer player = createPlayer("invalid\n1");
        player.chooseColumn(TABLE);
        assertThat(getErrorOutput()).contains("Invalid input: invalid");
    }


    @Test
    public void test_invalidInput_InvalidMove() {
        when(moveValidator.isValidMove(any(), anyInt())).thenReturn(false, true);
        ScannerPlayer player = createPlayer("10\n9");
        player.chooseColumn(TABLE);
        assertThat(getErrorOutput()).contains("Invalid move: 10");
    }

    private final String getErrorOutput() {
        errorStream.flush();
        return out.toString();
    }
}