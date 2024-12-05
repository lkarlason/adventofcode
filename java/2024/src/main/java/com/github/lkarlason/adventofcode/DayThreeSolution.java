package com.github.lkarlason.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DayThreeSolution {
    private static final Logger logger = LoggerFactory.getLogger(DayThreeSolution.class);
    private static final Pattern patternPartOne = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
    private static final Pattern patternPartTwo = Pattern
            .compile("(do\\(\\)|don't\\(\\)|mul\\((\\d{1,3}),(\\d{1,3})\\))");

    private DayThreeSolution() {}

    public static void solve(final String fileName) {
        try {
            final String input = Files.readString(Path.of(fileName));
            logger.info("Day 3 part 1: {}", partOne(input));
            logger.info("Day 3 part 2: {}", partTwo(input));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static long partOne(final String input) {
        long result = 0L;
        final Matcher matcher = patternPartOne.matcher(input);
        List<Long[]> matches = matcher.results()
                .map(m -> new Long[] { Long.parseLong(matcher.group(1)), Long.parseLong(matcher.group(2)) })
                .toList();
        for (var pair : matches) {
            result += pair[0] * pair[1];
        }

        return result;
    }

    private static long partTwo(final String input) {
        long result = 0L;
        final Matcher matcher = patternPartTwo.matcher(input);
        boolean doMultiply = true;
        while (matcher.find()) {
            final String match = matcher.group(1);
            if (match.equals("do()")) {
                doMultiply = true;
            } else if (match.equals("don't()")) {
                doMultiply = false;
            } else if(doMultiply){
                result += Long.parseLong(matcher.group(2)) * Long.parseLong(matcher.group(3));
            }
        }
        return result;
    }
}
