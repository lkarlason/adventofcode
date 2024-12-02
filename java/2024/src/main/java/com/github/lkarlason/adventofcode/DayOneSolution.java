package com.github.lkarlason.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DayOneSolution {

    private static final Pattern pattern = Pattern.compile(" +");
    private static final Logger logger = LoggerFactory.getLogger(DayOneSolution.class);

    private DayOneSolution() {}

    public static void solve(final String fileName) {
        logger.info("Day 1 part 1: {}", partOne(fileName));
        logger.info("Day 1 part 2: {}", partTwo(fileName));
    }

    private static long partOne(final String fileName) {
        final List<Long> leftList = new ArrayList<>();
        final List<Long> rightList = new ArrayList<>();
        long distance = 0L;
        try {
            Files.readAllLines(Path.of(fileName)).stream().map(pattern::split).forEach(pair -> {
                leftList.add(Long.parseLong(pair[0]));
                rightList.add(Long.parseLong(pair[1]));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        leftList.sort(null);
        rightList.sort(null);

        for(int i = 0; i < leftList.size(); i++) {
            distance += Math.abs(leftList.get(i) - rightList.get(i));
        }
        return distance;
    }

    private static long partTwo(final String fileName) {
        final Map<String, Long> countMap = new HashMap<>();
        final List<String> leftList = new ArrayList<>();
        try {
            Files.readAllLines(Path.of(fileName)).stream().map(pattern::split).forEach(pair -> {
                leftList.add(pair[0]);
                countMap.merge(pair[1], 1L, Long::sum);
            });
        } catch(IOException e) {
            e.printStackTrace();
        }

        long similarity = 0L;
        for(var num: leftList) {
            similarity += Long.parseLong(num) * countMap.getOrDefault(num, 0L);
        }
        return similarity;
    }
}
