package org.tanrabad.survey.nearby;

public class ImpLcsMatchPercentageCalculator implements LcsMatchPercentageCalculator {
    @Override public double calculate(int lcsLength, int compareStringLength) {
        return (double) lcsLength / (double) compareStringLength;
    }
}
