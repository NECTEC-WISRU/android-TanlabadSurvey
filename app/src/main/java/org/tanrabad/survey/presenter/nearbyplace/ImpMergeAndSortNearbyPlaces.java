package org.tanrabad.survey.presenter.nearbyplace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.tanrabad.survey.domain.nearbyplaces.MergeAndSortNearbyPlaces;
import org.tanrabad.survey.entity.Place;

public class ImpMergeAndSortNearbyPlaces implements MergeAndSortNearbyPlaces {
    @Override public List<Place> mergeAndSort(List<Place> placesWithLocation, List<Place> placesWithoutLocation) {
        List<Place> mergeAndSortPlaces = new ArrayList<>();

        mergeAndSortPlaces = placesWithLocation;

        Collections.sort(placesWithoutLocation, new PlaceWeightScoreComparator());
        mergeAndSortPlaces.addAll(placesWithoutLocation);

        return mergeAndSortPlaces;
    }
}
