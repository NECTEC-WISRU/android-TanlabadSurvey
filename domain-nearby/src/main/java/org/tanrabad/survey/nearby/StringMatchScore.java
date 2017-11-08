package org.tanrabad.survey.nearby;

public interface StringMatchScore {
    double calculate(int lcsLength, int compareStringLength);
}
