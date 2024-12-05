package com.github.lkarlason.adventofcode.solution;

public interface Solution {
    void solve(final String fileName);

    <T> T partOne();

    <T> T partTwo();
}
