package com.github.lkarlason.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DayNineSolution {
    private static final Logger logger = LoggerFactory.getLogger(DayNineSolution.class);

    private DayNineSolution() {
    }

    public static void main(String[] args) {
        solve("./data/day9.txt");
    }

    public static void solve(final String fileName) {
        try {
            final String[] input = Files.readString(Path.of(fileName)).split("");
            final long partOneSum = partOne(input);
            final long partTwoSum = partTwo(input);

            logger.info("Day 9 part 1: {}", partOneSum);
            logger.info("Day 9 part 2: {}", partTwoSum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long partTwo(final String[] input) {
        final Map<Integer, Pair<Integer, Integer>> blocks = new HashMap<>();
        final List<Pair<Integer, Integer>> emptyBlocks = new ArrayList<>();
        int currIdx = 0;
        int fileId = 0;
        for (int i = 0; i < input.length; ++i) {
            final var size = Integer.parseInt(input[i]);
            if (i % 2 == 0) {
                blocks.put(fileId, Pair.with(currIdx, size));
                fileId += 1;
            } else {
                if (size != 0) {
                    emptyBlocks.add(Pair.with(currIdx, size));
                }
            }
            currIdx += size;
        }
        
        while (fileId > 0) {
            fileId--;
            final var block = blocks.get(fileId);
            for (int i = 0; i < emptyBlocks.size(); ++i) {
                final var emptyBlock = emptyBlocks.get(i);
                if (emptyBlock.getValue0() >= block.getValue0()) {
                    emptyBlocks.removeAll(emptyBlocks.subList(i, emptyBlocks.size()));
                    break;
                }
                if (block.getValue1() <= emptyBlock.getValue1()) {
                    blocks.put(fileId, Pair.with(emptyBlock.getValue0(), block.getValue1()));
                    if (block.getValue1().equals(emptyBlock.getValue1())) {
                        emptyBlocks.remove(i);
                    } else {
                        emptyBlocks.set(i, Pair.with(emptyBlock.getValue0() + block.getValue1(), emptyBlock.getValue1() - block.getValue1()));
                    }
                    break;
                }
            }
        }

        long sum = 0L;
        for(var entry: blocks.entrySet()) {
            final var id = entry.getKey();
            final var range = entry.getValue();
            for(var i: IntStream.range(range.getValue0(), range.getValue0() + range.getValue1()).toArray()) {
                sum += id * i;
            }
        }
        return sum;
    }

    public static long partOne(final String[] input) {
        final List<Integer> blocks = new ArrayList<>();
        final List<Integer> emptyBlocks = new ArrayList<>();
        int currIdx = 0;
        for (int i = 0; i < input.length; ++i) {
            final var size = Integer.parseInt(input[i]);
            final var range = IntStream.range(currIdx, currIdx + size).boxed().collect(Collectors.toList());
            if (i % 2 == 0) {
                final var fileId = i / 2;
                blocks.addAll(range.stream().map(item -> fileId).toList());
            } else {
                blocks.addAll(range.stream().map(item -> -1).toList());
                emptyBlocks.addAll(range);
            }
            currIdx += size;
        }

        for (var emptyBlock : emptyBlocks) {
            while (blocks.get(blocks.size() - 1) == -1) {
                blocks.remove(blocks.size() - 1);
            }
            if (blocks.size() <= emptyBlock) {
                break;
            }
            blocks.set(emptyBlock, blocks.remove(blocks.size() - 1));
        }

        long sum = 0L;
        for (int i = 0; i < blocks.size(); ++i) {
            sum += i * blocks.get(i);
        }
        return sum;
    }
}
