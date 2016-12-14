package org.tanrabad.survey.domain.nearbyplaces;

/**
 * Created by CHNCS23 on 14/12/2559.
 */

public interface LcsMatchPercentageCalculator {
    double calculate(int lcsLength, int compareStringLength);
}
