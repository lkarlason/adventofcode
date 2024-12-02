package com.github.lkarlason.adventofcode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

enum Direction {
    INCREASING,
    DECREASING,
    NONE;

    public static Direction fromInt(final int n) {
        int unit = Math.abs(n) - (n-1);
        return switch(unit) {
            case 1 ->  INCREASING;
            case -1 -> DECREASING;
            default -> NONE;
        };
    }
}

public class DayTwoSolution {
    private static final Logger logger = LoggerFactory.getLogger(DayTwoSolution.class);

    private DayTwoSolution() {}

    public static void solve(final String fileName) {
        logger.info("Day 2 part 1: {}", solve(fileName, false));
        logger.info("Day 2 part 2: {}", solve(fileName, true));
    }

    private static long solve(final String fileName, final boolean partTwo) {
        long safeLevels = 0L;
        try (final BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                List<Integer> report = Stream.of(line.split(" +")).map(Integer::parseInt).toList();
                if (isSafe(report)) {
                    safeLevels++;
                } else if (partTwo) {
                    for (int i = 0; i < report.size(); i++) {
                        List<Integer> oneLevelRemoved = new ArrayList<>(report);
                        oneLevelRemoved.remove(i);
                        if (isSafe(oneLevelRemoved)) {
                            safeLevels++;
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return safeLevels;
    }

    private static boolean isSafe(List<Integer> levels) {
        Direction direction = Direction.NONE;
        for (int i = 0; i < levels.size() - 1; ++i) {
            int diff = levels.get(i + 1) - levels.get(i);
            if (i == 0) {
                direction = Direction.fromInt(diff);
            }

            if (Math.abs(diff) > 3 || Math.abs(diff) < 1) {
                return false;
            }

            if (!Direction.fromInt(diff).equals(direction)) {
                return false;
            }
        }

        return true;
    }
}
