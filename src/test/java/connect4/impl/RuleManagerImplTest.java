package connect4.impl;

import connect4.Color;
import connect4.Result;
import org.junit.Before;
import org.junit.Test;

import static connect4.impl.TableTestUtil.asciiArtTable;
import static org.assertj.core.api.Assertions.assertThat;

public class RuleManagerImplTest {

    private RuleManagerImpl tableEvaluator;

    @Before
    public void setUp() throws Exception {
        tableEvaluator = new RuleManagerImpl();
    }

    @Test
    public void test_validRange() {
        assertThat(tableEvaluator.validRange()).contains(0, 7);
    }

    @Test
    public void test_validMove_valid() {
        Color[][] table = TableTestUtil.createTable("" +
                "----G--" +
                "---GR--" +
                "---RR--" +
                "--RRR--" +
                "--RRG--" +
                "--RGG--");

        assertThat(tableEvaluator.isValidMove(table, 0)).isTrue();
        assertThat(tableEvaluator.isValidMove(table, 2)).isTrue();
        assertThat(tableEvaluator.isValidMove(table, 3)).isTrue();
        assertThat(tableEvaluator.isValidMove(table, 6)).isTrue();
    }

    @Test
    public void test_validMove_invalid_outsideOfTable() {
        Color[][] table = TableTestUtil.createTable("" +
                "-------" +
                "-------" +
                "---RR--" +
                "--RRR--" +
                "--RRG--" +
                "--RGG--");

        assertThat(tableEvaluator.isValidMove(table, -1)).isFalse();
        assertThat(tableEvaluator.isValidMove(table, 7)).isFalse();
        assertThat(tableEvaluator.isValidMove(table, Integer.MIN_VALUE)).isFalse();
        assertThat(tableEvaluator.isValidMove(table, Integer.MAX_VALUE)).isFalse();
    }

    @Test
    public void test_validMove_invalid_columnFull() {
        Color[][] table = TableTestUtil.createTable("" +
                "G---G--" +
                "G---G--" +
                "G--RR--" +
                "R-RRR--" +
                "R-RRG--" +
                "R-RGG--");

        assertThat(tableEvaluator.isValidMove(table, 0)).isFalse();
        assertThat(tableEvaluator.isValidMove(table, 4)).isFalse();
    }

    @Test
    public void test_updateTable_diskFallOnEmptyTable() {
        Color[][] table = TableTestUtil.createTable("" +
                "-------" +
                "-------" +
                "-------" +
                "-------" +
                "-------" +
                "-------");

        Result result = tableEvaluator.updateTable(table, Color.GREEN, 3);
        assertThat(result).isEqualTo(Result.CONTINUE);
        assertThat(asciiArtTable(table)).isEqualTo("" +
                "-------" +
                "-------" +
                "-------" +
                "-------" +
                "-------" +
                "---G---");
    }

    @Test
    public void test_updateTable_diskFallOnOtherDisks() {
        Color[][] table = TableTestUtil.createTable("" +
                "-------" +
                "------R" +
                "------G" +
                "------G" +
                "------R" +
                "--G---R");

        Result result = tableEvaluator.updateTable(table, Color.RED, 2);
        assertThat(result).isEqualTo(Result.CONTINUE);
        assertThat(asciiArtTable(table)).isEqualTo("" +
                "-------" +
                "------R" +
                "------G" +
                "------G" +
                "--R---R" +
                "--G---R");

        result = tableEvaluator.updateTable(table, Color.GREEN, 6);
        assertThat(result).isEqualTo(Result.CONTINUE);
        assertThat(asciiArtTable(table)).isEqualTo("" +
                "------G" +
                "------R" +
                "------G" +
                "------G" +
                "--R---R" +
                "--G---R");
    }


    @Test
    public void test_updateTable_invalidMoveLooses() {
        Color[][] table = TableTestUtil.createTable("" +
                "------R" +
                "------R" +
                "------G" +
                "------G" +
                "------R" +
                "--G---R");
        Result result = tableEvaluator.updateTable(table, Color.RED, 6);
        assertThat(result).isEqualTo(Result.LOST);
        result = tableEvaluator.updateTable(table, Color.GREEN, -1);
        assertThat(result).isEqualTo(Result.LOST);
    }


    @Test
    public void test_updateTable_drawGame() {
        Color[][] table = TableTestUtil.createTable("" +
                "RR-GGGR" +
                "GGGRRRG" +
                "RRRGGGR" +
                "GGGRRRG" +
                "RRRGGGR" +
                "GGGRRRG");
        Result result = tableEvaluator.updateTable(table, Color.RED, 2);
        assertThat(result).isEqualTo(Result.DRAW);
    }

    @Test
    public void test_updateTable_winGame() {
        Color[][] table = TableTestUtil.createTable("" +
                "-------" +
                "------R" +
                "------G" +
                "--R---G" +
                "--R---R" +
                "--GGG-R");
        Result result = tableEvaluator.updateTable(table, Color.GREEN, 1);
        assertThat(result).isEqualTo(Result.WIN);
    }


    @Test
    public void test_hasWinFromCell_diagonal1() {
        String redDiagonal1 = "" +
                "-------" +
                "-------" +
                "---RR--" +
                "--RRR--" +
                "-RRR---" +
                "-RR----";

        Color[][] diag1 = TableTestUtil.createTable(redDiagonal1);
        assertThat(tableEvaluator.hasWinFromCell(diag1, 5, 1)).isTrue();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 4, 2)).isTrue();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 3, 3)).isTrue();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 2, 4)).isTrue();

        assertThat(tableEvaluator.hasWinFromCell(diag1, 5, 0)).isFalse();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 4, 1)).isFalse();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 5, 2)).isFalse();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 4, 3)).isFalse();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 1, 5)).isFalse();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 0, 6)).isFalse();
    }

    @Test
    public void test_hasWinFromCell_diagonal2() {
        String greenDiagonal = "" +
                "-R-----" +
                "G-G----" +
                "-R-G---" +
                "--G-G--" +
                "---G-G-" +
                "----G-R";

        Color[][] diag1 = TableTestUtil.createTable(greenDiagonal);
        assertThat(tableEvaluator.hasWinFromCell(diag1, 1, 2)).isTrue();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 2, 3)).isTrue();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 3, 4)).isTrue();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 4, 5)).isTrue();

        assertThat(tableEvaluator.hasWinFromCell(diag1, 0, 1)).isFalse();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 5, 6)).isFalse();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 1, 0)).isFalse();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 2, 1)).isFalse();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 3, 2)).isFalse();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 4, 3)).isFalse();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 5, 4)).isFalse();
    }

    @Test
    public void test_hasWinFromCell_horizontal() {
        String greenDiagonal = "" +
                "-------" +
                "-------" +
                "GGGG---" +
                "-RRR-RR" +
                "-------" +
                "-------";

        Color[][] diag1 = TableTestUtil.createTable(greenDiagonal);
        assertThat(tableEvaluator.hasWinFromCell(diag1, 2, 0)).isTrue();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 2, 1)).isTrue();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 2, 2)).isTrue();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 2, 3)).isTrue();


        assertThat(tableEvaluator.hasWinFromCell(diag1, 1, 3)).isFalse();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 2, 4)).isFalse();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 3, 0)).isFalse();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 3, 1)).isFalse();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 3, 2)).isFalse();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 3, 4)).isFalse();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 3, 5)).isFalse();
    }

    @Test
    public void test_hasWinFromCell_vertical() {
        String greenDiagonal = "" +
                "--R----" +
                "G-R----" +
                "G-R----" +
                "G------" +
                "G-R----" +
                "--R----";

        Color[][] diag1 = TableTestUtil.createTable(greenDiagonal);
        assertThat(tableEvaluator.hasWinFromCell(diag1, 1, 0)).isTrue();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 2, 0)).isTrue();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 3, 0)).isTrue();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 4, 0)).isTrue();


        assertThat(tableEvaluator.hasWinFromCell(diag1, 0, 0)).isFalse();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 5, 0)).isFalse();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 2, 1)).isFalse();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 0, 2)).isFalse();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 1, 2)).isFalse();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 3, 3)).isFalse();
        assertThat(tableEvaluator.hasWinFromCell(diag1, 4, 3)).isFalse();
    }
}