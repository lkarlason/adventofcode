package com.github.lkarlason.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DayFiveSolution {
    private static final Logger logger = LoggerFactory.getLogger(DayFiveSolution.class);

    private DayFiveSolution() {}

    public static void solve(final String fileName) {
        final List<String> input = readLines(fileName);
        final List<String> updates = input.stream().filter(s -> s.contains(",")).toList();
        final Set<String> rules = input.stream().filter(s -> s.contains("|")).collect(Collectors.toSet());
        long partOneSum = 0L;
        long partTwoSum = 0L;
        for(var update: updates) {
            final String[] path = update.split(",");
            Arrays.sort(path, (String a, String b) -> {
                final int tempExpression = rules.contains(b + "|" + a) ? 1 : 0;
                return rules.contains(a + "|" + b) ? -1 : tempExpression;
            });
            if(String.join(",", path).equals(update)) {
                partOneSum += Long.parseLong(path[path.length / 2]);
            } else {
                partTwoSum += Long.parseLong(path[path.length / 2]);
            }
        }

        logger.info("Day 5 part 1: {}", partOneSum);
        logger.info("Day 5 part 2: {}", partTwoSum);
    }

    private static List<String> readLines(final String fileName) {
        try {
            return Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return List.of();
    }
}
