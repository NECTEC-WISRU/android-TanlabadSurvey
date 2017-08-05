package org.tanrabad.survey.domain.nearbyplaces;

import java.util.List;
import org.tanrabad.survey.entity.Place;

public interface MergeAndSortNearbyPlaces {
    List<Place> mergeAndSort(List<Place> placeWithLocation, List<Place> placeWithoutLocation);
}
