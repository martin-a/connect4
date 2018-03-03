package connect4.impl;

import connect4.Color;
import connect4.Result;
import connect4.RuleManager;

class RuleManagerImpl implements RuleManager {

    private static final int NUMBER_OF_COLUMNS = 7;
    private static final int NUMBER_OF_ROWS = 6;
    private static final int CONSECUTIVE_SAME_COLOR_TO_WIN = 4;

    @Override
    public boolean isValidMove(Color[][] table, int selectedColumn) {
        return selectedColumn >= 0 && selectedColumn < NUMBER_OF_COLUMNS
                && table[0][selectedColumn] == null;
    }

    @Override
    public int[] validRange() {
        return new int[]{0, NUMBER_OF_COLUMNS};
    }

    @Override
    public Color[][] newTable() {
        return new Color[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
    }

    @Override
    public Result updateTable(Color[][] table, Color color, int selectedColumn) {
        if (!isValidMove(table, selectedColumn)) {
            // consider any invalid move as loosing
            return Result.LOST;
        }

        // Make the color disk 'fall' into the specified column
        int row = NUMBER_OF_ROWS - 1;
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            if (table[i][selectedColumn] != null) {
                row = i - 1;
                break;
            }
        }
        table[row][selectedColumn] = color;

        // Check if added disk is winning
        if (hasWinFromCell(table, row, selectedColumn)) {
            return Result.WIN;
        }

        // Check if table is full (draw game)
        boolean tableFull = true;
        for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
            if (table[0][i] == null) {
                tableFull = false;
                break;
            }
        }
        if (tableFull) {
            return Result.DRAW;
        }
        return Result.CONTINUE;
    }


    /**
     * Check if the specified cell is part of a winning pattern (4 consecutive same color)
     */
    boolean hasWinFromCell(Color[][] table, int row, int column) {
        boolean match = false;
        //Check vertical match
        match = match || hasWinFromCell(table, row, column, 1, 0);
        //Check horizontal match
        match = match || hasWinFromCell(table, row, column, 0, 1);
        //Check top-left -> bottom-right diagonal match
        match = match || hasWinFromCell(table, row, column, 1, 1);
        //Check bottom-left -> top-right diagonal match
        match = match || hasWinFromCell(table, row, column, -1, 1);
        return match;
    }

    private boolean hasWinFromCell(Color[][] table, int row, int column,
                                   int rowCoef, int columnCoef) {
        int consecutiveSameColor = 0;
        Color color = table[row][column];
        if (color == null) {
            //Current cell is empty, no win for any player possible from this cell
            return false;
        }
        int rangeToCheck = CONSECUTIVE_SAME_COLOR_TO_WIN - 1;
        for (int i = -rangeToCheck; i <= rangeToCheck; i++) {
            int x = column + columnCoef * i;
            int y = row + rowCoef * i;
            if (x < 0 || x >= NUMBER_OF_COLUMNS || y < 0 || y >= NUMBER_OF_ROWS) {
                //Outside of the table
                consecutiveSameColor = 0;
                continue;
            }
            if (table[y][x] == color) {
                consecutiveSameColor++;
            } else {
                //Cell empty or different color
                consecutiveSameColor = 0;
            }
            if (consecutiveSameColor >= CONSECUTIVE_SAME_COLOR_TO_WIN) {
                //Found a match
                return true;
            }
        }
        return false;
    }
}
