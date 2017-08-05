package org.tanrabad.survey.nearby;

public interface LcsMatchPercentageCalculator {
    double calculate(int lcsLength, int compareStringLength);
}
