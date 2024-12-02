package com.github.lkarlason.adventofcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

enum Days {
    DAY_ONE,
    DAY_TWO;
}

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        final Days day = Days.valueOf(args[0]);
        logger.info("Running {}", day);
        switch (day) {
            case DAY_ONE -> DayOneSolution.solve("./data/day1.txt");
            case DAY_TWO -> DayTwoSolution.solve("./data/day2.txt");
        }
    }
}
