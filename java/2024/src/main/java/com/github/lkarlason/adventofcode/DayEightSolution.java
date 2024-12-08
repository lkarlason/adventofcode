package com.github.lkarlason.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DayEightSolution {

    private static final Logger logger = LoggerFactory.getLogger(DayEightSolution.class);

    private DayEightSolution() {
    }

    public static void solve(final String fileName) {
        try {
            final var grid = getGrid(fileName);
            final var locations = getLocations(grid);

            final long partOneResult = partOne(grid, locations);
            final long partTwoResult = partTwo(grid, locations);
            logger.info("Day 8 part 1: {}", partOneResult);
            logger.info("Day 8 part 2: {}", partTwoResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long partOne(final char[][] grid, final List<Triplet<Integer, Integer, Character>> locations) {
        final Set<Triplet<Integer, Integer, Character>> seen = new HashSet<>();
        final Set<Pair<Integer, Integer>> antiNodes = new HashSet<>();
        final int rows = grid.length;
        final int cols = grid[0].length;
        for (var location : locations) {
            seen.add(location);
            final var currentLocation = Pair.with(location.getValue0(), location.getValue1());
            final var matches = locations.stream()
                    .filter(l -> l.getValue2().equals(location.getValue2()) && !seen.contains(l))
                    .map(l -> Pair.with(l.getValue0(), l.getValue1())).toList();

            for(var match: matches) {
                final var direction = getDirection(currentLocation, match);
                final var antiNode1 = Pair.with(currentLocation.getValue0() - direction.getValue0(), currentLocation.getValue1() - direction.getValue1());
                final var antiNode2 = Pair.with(match.getValue0() + direction.getValue0(), match.getValue1() + direction.getValue1());

                if(antiNode1.getValue0() >= 0 && antiNode1.getValue0() < rows && antiNode1.getValue1() >= 0 && antiNode1.getValue1() < cols) {
                    antiNodes.add(antiNode1);
                }

                if(antiNode2.getValue0() >= 0 && antiNode2.getValue0() < rows && antiNode2.getValue1() >= 0 && antiNode2.getValue1() < cols) {
                    antiNodes.add(antiNode2);
                }
            }

        }
        return antiNodes.size();
    }

    private static long partTwo(final char[][] grid, final List<Triplet<Integer, Integer, Character>> locations) {
        final Set<Triplet<Integer, Integer, Character>> seen = new HashSet<>();
        final Set<Pair<Integer, Integer>> antiNodes = new HashSet<>();
        final int rows = grid.length;
        final int cols = grid[0].length;
        for (var location : locations) {
            seen.add(location);
            final var currentLocation = Pair.with(location.getValue0(), location.getValue1());
            final var matches = locations.stream()
                    .filter(l -> l.getValue2().equals(location.getValue2()) && !seen.contains(l))
                    .map(l -> Pair.with(l.getValue0(), l.getValue1())).toList();

            for(var match: matches) {
                final var direction = getDirection(currentLocation, match);
                var newAntiNode = Pair.with(currentLocation.getValue0() - direction.getValue0(), currentLocation.getValue1() - direction.getValue1());
                while(newAntiNode.getValue0() >= 0 && newAntiNode.getValue0() < rows && newAntiNode.getValue1() >= 0 && newAntiNode.getValue1() < cols) {
                    antiNodes.add(newAntiNode);
                    newAntiNode = Pair.with(newAntiNode.getValue0() - direction.getValue0(), newAntiNode.getValue1() - direction.getValue1());
                }

                newAntiNode = Pair.with(match.getValue0() + direction.getValue0(), match.getValue1() + direction.getValue1());
                while(newAntiNode.getValue0() >= 0 && newAntiNode.getValue0() < rows && newAntiNode.getValue1() >= 0 && newAntiNode.getValue1() < cols) {
                    antiNodes.add(newAntiNode);
                    newAntiNode = Pair.with(newAntiNode.getValue0() + direction.getValue0(), newAntiNode.getValue1() + direction.getValue1());
                }
            }
            antiNodes.add(currentLocation);
        }
        return antiNodes.size();
    }

    private static Pair<Integer, Integer> getDirection(final Pair<Integer, Integer> first, final Pair<Integer, Integer> second) {
        return Pair.with(second.getValue0() - first.getValue0(), second.getValue1() - first.getValue1());
    }

    private static List<Triplet<Integer, Integer, Character>> getLocations(final char[][] grid) {
        final List<Triplet<Integer, Integer, Character>> locations = new ArrayList<>();
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[i].length; ++j) {
                if (Character.isLetterOrDigit(grid[i][j])) {
                    locations.add(Triplet.with(i, j, grid[i][j]));
                }
            }
        }
        return locations;
    }

    private static char[][] getGrid(final String fileName) throws IOException {
        final List<String> lines = Files.readAllLines(Path.of(fileName));
        final char[][] grid = new char[lines.size()][];
        for (int i = 0; i < lines.size(); ++i) {
            grid[i] = lines.get(i).toCharArray();
        }
        return grid;
    }
}
