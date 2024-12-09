package com.github.lkarlason.adventofcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

enum Days {
    DAY_ONE,
    DAY_TWO,
    DAY_THREE,
    DAY_FOUR,
    DAY_FIVE,
    DAY_SIX,
    DAY_SEVEN,
    DAY_EIGHT,
    DAY_NINE;
}

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        final Days day = Days.valueOf(args[0]);
        logger.info("Running {}", day);
        switch (day) {
            case DAY_ONE -> DayOneSolution.solve("./data/day1.txt");
            case DAY_TWO -> DayTwoSolution.solve("./data/day2.txt");
            case DAY_THREE -> DayThreeSolution.solve("./data/day3.txt");
            case DAY_FOUR -> DayFourSolution.solve("./data/day4.txt");
            case DAY_FIVE -> DayFiveSolution.solve("./data/day5.txt");
            case DAY_SIX -> DaySixSolution.solve("./data/day6.txt");
            case DAY_SEVEN -> DaySevenSolution.solve("./data/day7.txt");
            case DAY_EIGHT -> DayEightSolution.solve("./data/day8.txt");
            case DAY_NINE -> DayNineSolution.solve("./data/day9.txt");
        }
    }
}
