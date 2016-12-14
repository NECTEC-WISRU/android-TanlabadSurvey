package org.tanrabad.survey.utils.place;

import org.tanrabad.survey.domain.nearbyplaces.LcsMatchPercentageCalculator;

public class ImpLcsMatchPercentageCalculator implements LcsMatchPercentageCalculator {
    @Override public double calculate(int lcsLength, int compareStringLength) {
        return (double) lcsLength / (double) compareStringLength;
    }
}
