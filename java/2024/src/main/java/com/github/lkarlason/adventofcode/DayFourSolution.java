package com.github.lkarlason.adventofcode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * A fucking ugly solution, to be improved
 */
public class DayFourSolution {
    private static final Logger logger = LoggerFactory.getLogger(DayFourSolution.class);

    public static void solve(final String fileName) {
        final char[][] grid = readData(fileName);
        logger.info("Day 4 part 1: {}", partOne(grid));
        logger.info("Day 4 part 2: {}", partTwo(grid));
    }

    public static long partOne(final char[][] grid) {
        long matches = 0L;

        int n = grid.length;
        int m = grid[0].length;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (grid[i][j] == 'X') {
                    if (i + 3 < n) {
                        String wordDown = new String(
                                new char[] { grid[i][j], grid[i + 1][j], grid[i + 2][j], grid[i + 3][j] });
                        if (wordDown.equals("XMAS")) {
                            matches++;
                        }

                        if (j - 3 >= 0) {
                            String wordDownLeft = new String(new char[] { grid[i][j], grid[i + 1][j - 1],
                                    grid[i + 2][j - 2], grid[i + 3][j - 3] });
                            if (wordDownLeft.equals("XMAS")) {
                                matches++;
                            }
                        }

                        if (j + 3 < m) {
                            String wordDownRight = new String(new char[] { grid[i][j], grid[i + 1][j + 1],
                                    grid[i + 2][j + 2], grid[i + 3][j + 3] });
                            if (wordDownRight.equals("XMAS")) {
                                matches++;
                            }
                        }
                    }

                    if (i - 3 >= 0) {
                        String wordUp = new String(
                                new char[] { grid[i][j], grid[i - 1][j], grid[i - 2][j], grid[i - 3][j] });
                        if (wordUp.equals("XMAS")) {
                            matches++;
                        }

                        if (j - 3 >= 0) {
                            String wordUpLeft = new String(new char[] { grid[i][j], grid[i - 1][j - 1],
                                    grid[i - 2][j - 2], grid[i - 3][j - 3] });
                            if (wordUpLeft.equals("XMAS")) {
                                matches++;
                            }
                        }

                        if (j + 3 < m) {
                            String wordUpRight = new String(new char[] { grid[i][j], grid[i - 1][j + 1],
                                    grid[i - 2][j + 2], grid[i - 3][j + 3] });
                            if (wordUpRight.equals("XMAS")) {
                                matches++;
                            }
                        }
                    }

                    if (j + 3 < m) {
                        String wordRight = new String(
                                new char[] { grid[i][j], grid[i][j + 1], grid[i][j + 2], grid[i][j + 3] });
                        if (wordRight.equals("XMAS")) {
                            matches++;
                        }
                    }

                    if (j - 3 >= 0) {
                        String wordLeft = new String(
                                new char[] { grid[i][j], grid[i][j - 1], grid[i][j - 2], grid[i][j - 3] });
                        if (wordLeft.equals("XMAS")) {
                            matches++;
                        }
                    }
                }
            }
        }
        return matches;
    }

    public static long partTwo(final char[][] grid) {
        long matches = 0L;

        int n = grid.length;
        int m = grid[0].length;
        for (int i = 1; i < n-1; ++i) {
            for (int j = 1; j < m-1; ++j) {
                if (grid[i][j] == 'A') {
                    final String downLeft = new String(
                            new char[] { grid[i - 1][j - 1], grid[i][j], grid[i + 1][j + 1] });
                    final String upRight = new String(
                            new char[] { grid[i + 1][j - 1], grid[i][j], grid[i - 1][j + 1] });

                    if ((downLeft.equals("MAS") || downLeft.equals("SAM"))
                            && (upRight.equals("MAS") || upRight.equals("SAM"))) {
                        matches++;
                    }
                }
            }
        }
        return matches;
    }

    public static char[][] readData(final String fileName) {
        try {
            final List<String> lines = Files.readAllLines(Path.of(fileName));
            final char[][] grid = new char[lines.size()][lines.get(0).length()];

            int i = 0;
            for (var line : lines) {
                int j = 0;
                for (char c : line.toCharArray()) {
                    grid[i][j] = c;
                    j++;
                }
                i++;
            }
            return grid;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
