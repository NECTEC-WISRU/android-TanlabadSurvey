package org.tanrabad.survey.domain.nearbyplaces;

public interface LcsMatchPercentageCalculator {
    double calculate(int lcsLength, int compareStringLength);
}
