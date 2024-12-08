package com.github.lkarlason.adventofcode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaySevenSolution {
    private static final Logger logger = LoggerFactory.getLogger(DaySevenSolution.class);

    private DaySevenSolution() {}

    public static void solve(final String fileName) {
        long partOne = 0;
        long partTwo = 0;
        try (final BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                final var targetAndSequence = getTargetAndSequence(line);
                final var target = targetAndSequence.getValue0();
                final var sequence = targetAndSequence.getValue1();
                if (canCombinePartOne(sequence, target)) {
                    partOne += target;
                } else if (canCombinePartTwo(sequence, target)) {
                    partTwo += target;
                }
            }
        } catch (IOException e) {
            logger.error("Could not open data file {}", fileName);
        }

        logger.info("Day 7 part 1: {}", partOne);
        logger.info("Day 7 part 2: {}", partOne + partTwo);
    }

    private static boolean canCombinePartTwo(final List<Long> items, final long target) {
        final var current = items.get(0);
        if (items.size() == 1) {
            return current == target;
        }
        final var next = items.get(1);
        final var sum = current + next;
        final var mult = current * next;
        final var concat = Long.parseLong(current.toString() + next.toString());

        final var copy = new ArrayList<>(items.subList(1, items.size()));
        copy.set(0, sum);

        if (canCombinePartTwo(copy, target)) {
            return true;
        }
        copy.set(0, mult);

        if (canCombinePartTwo(copy, target)) {
            return true;
        }

        copy.set(0, concat);
        return canCombinePartTwo(copy, target);
    }

    private static boolean canCombinePartOne(final List<Long> items, final long target) {
        boolean sum = false;
        boolean mult = false;
        final var current = items.get(items.size() - 1);

        if (items.size() == 1) {
            return current == target;
        }

        if (target % current == 0) {
            mult = canCombinePartOne(items.subList(0, items.size() - 1), target / current);
        }

        if (!mult) {
            sum = canCombinePartOne(items.subList(0, items.size() - 1), target - current);
        }

        return sum || mult;
    }

    private static Pair<Long, List<Long>> getTargetAndSequence(final String line) {
        final String[] splitted = line.split(":");
        return Pair.with(Long.parseLong(splitted[0]),
                Arrays.stream(splitted[1].trim().split(" ")).map(Long::parseLong).toList());
    }
}
