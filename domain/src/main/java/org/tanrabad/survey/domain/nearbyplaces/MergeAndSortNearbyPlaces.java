package org.tanrabad.survey.domain.nearbyplaces;

import java.util.List;
import org.tanrabad.survey.entity.Place;

/**
 * Created by CHNCS23 on 13/12/2559.
 */

public interface MergeAndSortNearbyPlaces {
    List<Place> mergeAndSort(List<Place> placeWithLocation, List<Place> placeWithoutLocation);
}
