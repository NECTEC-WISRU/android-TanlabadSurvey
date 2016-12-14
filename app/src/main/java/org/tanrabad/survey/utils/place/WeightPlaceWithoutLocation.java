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
        removeCommonPlaceName(placeWithLocation);
        removeCommonPlaceName(placeWithoutLocation);
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();
        LcsMatchPercentageCalculator lcsMatchPercentageCalculator = new ImpLcsMatchPercentageCalculator();
        for (Place eachPlaceWithLocation : placeWithLocation) {
            for (Place eachPlaceWithoutLocation : placeWithoutLocation) {
                String placeWithLocationName = eachPlaceWithLocation.getName();
                int lcsLengthOfPlaceWithoutLocation =
                        lcs.length(placeWithLocationName, eachPlaceWithoutLocation.getName());
                double lcsWeightPercentage = lcsMatchPercentageCalculator.calculate(lcsLengthOfPlaceWithoutLocation,
                        placeWithLocationName.length());

                if (weightedScoreOfPlaceWithoutLocation.containsKey(eachPlaceWithoutLocation.getId())) {
                    Double oldWeight = weightedScoreOfPlaceWithoutLocation.get(eachPlaceWithoutLocation.getId());
                    lcsWeightPercentage += oldWeight;
                }
                weightedScoreOfPlaceWithoutLocation.put(eachPlaceWithoutLocation.getId(), lcsWeightPercentage);
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

    private static void removeCommonPlaceName(List<Place> places) {
        for (Place place : places) {
            place.setName(CommonPlaceTypeRemover.remove(place.getName()));
        }
    }
}
