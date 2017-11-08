package org.tanrabad.survey.nearby;

import info.debatty.java.stringsimilarity.LongestCommonSubsequence;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ImpLcsMatchPercentageCalculatorTest {

    private String tuHospital = "โรงพยาบาลธรรมศาสตร์";
    private String tuSchool = "โรงเรียนอนุบาลธรรมศาสตร์";

    @Test public void findLcsPercentage() throws Exception {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();
        StringMatchScore stringMatchScore = new LcsMatchScore();
        assertEquals(0.89473684,
                stringMatchScore.calculate(lcs.length(tuHospital, tuSchool), tuHospital.length()), 1);
    }
}
