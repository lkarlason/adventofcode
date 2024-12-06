package com.github.lkarlason.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

enum MoveDirection {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    private static final Set<Character> directions = Set.of('<', '^', '>', 'v');

    public static MoveDirection fromChar(final char c) {
        return switch (c) {
            case '^' -> UP;
            case '>' -> RIGHT;
            case 'v' -> DOWN;
            case '<' -> LEFT;
            default -> throw new IllegalArgumentException("Invalid direction");
        };
    }

    public static MoveDirection rotate(final MoveDirection direction) {
        return switch (direction) {
            case UP -> RIGHT;
            case LEFT -> UP;
            case DOWN -> LEFT;
            case RIGHT -> DOWN;
        };
    }

    public static boolean isDirection(final char c) {
        return directions.contains(c);
    }
}

public class DaySixSolution {
    private static final Logger logger = LoggerFactory.getLogger(DaySixSolution.class);

    private DaySixSolution() {}

    public static void solve(final String fileName) {
        try {
            final char[][] grid = getGrid(fileName);
            final var start = getStart(grid);

            final var visited = traverse(grid, start).getValue0();

            logger.info("Day 6 part 1: {}", visited.size());
            logger.info("Day 6 part 2: {}", findLoops(grid, start, visited));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Pair<Set<Pair<Integer, Integer>>, Boolean> traverse(final char[][] grid,
            final Triplet<Integer, Integer, MoveDirection> start) {
        var position = start;
        final Set<Triplet<Integer, Integer, MoveDirection>> visited = new HashSet<>();
        visited.add(position);
        int rows = grid.length;
        int cols = grid[0].length;
        boolean isLoop = false;
        while (true) {
            var next = move(Pair.with(position.getValue0(), position.getValue1()), position.getValue2());
            
            if (isOutOfBounds(next, rows, cols)) {
                break;
            }
            
            if (grid[next.getValue0()][next.getValue1()] == '#') {
                position = position.setAt2(MoveDirection.rotate(position.getValue2()));
            } else {
                position = Triplet.with(next.getValue0(), next.getValue1(), position.getValue2());
                if (visited.contains(position)) {
                    isLoop = true;
                    break;
                }
                visited.add(position);
            }
        }

        return Pair.with(visited.stream().map(triple -> Pair.with(triple.getValue0(), triple.getValue1()))
                .collect(Collectors.toSet()), isLoop);
    }

    private static long findLoops(final char[][] grid, final Triplet<Integer, Integer, MoveDirection> start,
            final Set<Pair<Integer, Integer>> visited) {
        long loops = 0L;
        for (var visit : visited) {
            grid[visit.getValue0()][visit.getValue1()] = '#';
            if (traverse(grid, start).getValue1().booleanValue()) {
                loops++;
            }
            grid[visit.getValue0()][visit.getValue1()] = '.';
        }
        return loops;
    }

    private static boolean isOutOfBounds(final Pair<Integer, Integer> position, final int rows, final int cols) {
        return position.getValue0() < 0 || position.getValue0() >= rows || position.getValue1() < 0
                || position.getValue1() >= cols;
    }

    private static Pair<Integer, Integer> move(final Pair<Integer, Integer> position, final MoveDirection direction) {
        return switch (direction) {
            case UP -> Pair.with(position.getValue0() - 1, position.getValue1());
            case DOWN -> Pair.with(position.getValue0() + 1, position.getValue1());
            case LEFT -> Pair.with(position.getValue0(), position.getValue1() - 1);
            case RIGHT -> Pair.with(position.getValue0(), position.getValue1() + 1);
        };
    }

    private static Triplet<Integer, Integer, MoveDirection> getStart(final char[][] grid) {
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[0].length; ++j) {
                if (MoveDirection.isDirection(grid[i][j])) {
                    return Triplet.with(i, j, MoveDirection.fromChar(grid[i][j]));
                }
            }
        }
        throw new IllegalArgumentException("Could not find starting position");
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
