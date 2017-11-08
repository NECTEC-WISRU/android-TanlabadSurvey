package org.tanrabad.survey.nearby;

public class LcsMatchScore implements StringMatchScore {
    @Override public double calculate(int result, int compareStringLength) {
        return (double) result / (double) compareStringLength;
    }
}
