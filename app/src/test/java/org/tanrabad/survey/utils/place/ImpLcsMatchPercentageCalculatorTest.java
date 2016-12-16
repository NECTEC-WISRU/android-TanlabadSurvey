package org.tanrabad.survey.utils.place;

import info.debatty.java.stringsimilarity.LongestCommonSubsequence;
import org.junit.Test;
import org.tanrabad.survey.domain.nearbyplaces.LcsMatchPercentageCalculator;

import static org.junit.Assert.assertEquals;

public class ImpLcsMatchPercentageCalculatorTest {

    private String tuHospital = "โรงพยาบาลธรรมศาสตร์";
    private String tuSchool = "โรงเรียนอนุบาลธรรมศาสตร์";

    @Test public void findLcsPercentage() throws Exception {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();
        LcsMatchPercentageCalculator lcsMatchPercentageCalculator = new ImpLcsMatchPercentageCalculator();
        assertEquals(0.89473684,
                lcsMatchPercentageCalculator.calculate(lcs.length(tuHospital, tuSchool), tuHospital.length()));
    }
}
