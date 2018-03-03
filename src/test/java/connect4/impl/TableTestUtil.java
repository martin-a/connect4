package connect4.impl;

import connect4.Color;

import java.util.Arrays;

public class TableTestUtil {

    public static String asciiArtTable(Color[][] table) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < table.length; i++) {
            Color[] row = table[i];
            Arrays.stream(row).forEach(cell -> {
                if (cell == Color.GREEN) {
                    stringBuilder.append("G");
                } else if (cell == Color.RED) {
                    stringBuilder.append("R");
                } else {
                    stringBuilder.append("-");
                }
            });
        }
        return stringBuilder.toString();
    }


    public static Color[][] createTable(String asciiArtTable) {
        return createTable(asciiArtTable, 6, 7);
    }

    public static Color[][] createTable(String s, int rows, int cols) {
        Color[][] table = new Color[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int charIndex = i * cols + j;
                char c = s.charAt(charIndex);
                if (c == 'R') {
                    table[i][j] = Color.RED;
                } else if (c == 'G') {
                    table[i][j] = Color.GREEN;
                }
            }
        }
        return table;
    }

}
