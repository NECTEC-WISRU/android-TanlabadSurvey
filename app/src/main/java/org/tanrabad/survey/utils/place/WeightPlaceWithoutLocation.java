package org.tanrabad.survey.utils.place;

import info.debatty.java.stringsimilarity.LongestCommonSubsequence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.tanrabad.survey.domain.nearbyplaces.LcsMatchPercentageCalculator;
import org.tanrabad.survey.entity.Place;

public class WeightPlaceWithoutLocation {
    public static Map<UUID, Double> calculate(List<Place> placeWithLocation, List<Place> placeWithoutLocation) {
        Map<UUID, Double> weightedScoreOfPlaceWithoutLocation = new HashMap<>();
        Map<UUID, String> trimmedPlaceNameWithLocation = removeCommonPlaceName(placeWithLocation);
        Map<UUID, String> trimmedPlaceNameWithoutLocation = removeCommonPlaceName(placeWithoutLocation);
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();
        LcsMatchPercentageCalculator lcsMatchPercentageCalculator = new ImpLcsMatchPercentageCalculator();
        for (Map.Entry<UUID, String> placeNameWithLocationEntry : trimmedPlaceNameWithLocation.entrySet()) {
            for (Map.Entry<UUID, String> placeNameWithoutLocationEntry : trimmedPlaceNameWithoutLocation.entrySet()) {
                String placeWithLocationName = placeNameWithLocationEntry.getValue();
                int lcsLengthOfPlaceWithoutLocation =
                        lcs.length(placeWithLocationName, placeNameWithoutLocationEntry.getValue());
                double lcsWeightPercentage = lcsMatchPercentageCalculator.calculate(lcsLengthOfPlaceWithoutLocation,
                        placeWithLocationName.length());

                if (weightedScoreOfPlaceWithoutLocation.containsKey(placeNameWithoutLocationEntry.getKey())) {
                    Double oldWeight = weightedScoreOfPlaceWithoutLocation.get(placeNameWithoutLocationEntry.getKey());
                    lcsWeightPercentage += oldWeight;
                }
                weightedScoreOfPlaceWithoutLocation.put(placeNameWithoutLocationEntry.getKey(), lcsWeightPercentage);
            }
        }

        calculateAverageMatchPercentage(placeWithLocation, weightedScoreOfPlaceWithoutLocation);

        return weightedScoreOfPlaceWithoutLocation;
    }

    private static void calculateAverageMatchPercentage(List<Place> placeWithLocation,
            Map<UUID, Double> weightedScoreOfPlaceWithoutLocation) {
        for (Map.Entry<UUID, Double> entry : weightedScoreOfPlaceWithoutLocation.entrySet()) {
            double averageMatchPercentageOfEachPlace = entry.getValue() / placeWithLocation.size();
            entry.setValue(averageMatchPercentageOfEachPlace);
        }
    }

    private static Map<UUID, String> removeCommonPlaceName(List<Place> places) {
        Map<UUID, String> removeCommonPlaceNameMap = new HashMap<>();
        for (Place place : places) {
            removeCommonPlaceNameMap.put(place.getId(), CommonPlaceTypeRemover.remove(place.getName()));
        }
        return removeCommonPlaceNameMap;
    }
}
