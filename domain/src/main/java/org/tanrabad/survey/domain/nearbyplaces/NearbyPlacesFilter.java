package org.tanrabad.survey.domain.nearbyplaces;

import java.util.List;
import org.tanrabad.survey.entity.LocationEntity;
import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.entity.field.Location;
import org.tanrabad.survey.entity.field.LocationBound;
import org.tanrabad.survey.entity.utils.WeightEntity;

/**
 * Created by CHNCS23 on 8/12/2559.
 */
public interface NearbyPlacesFilter {
    List<Place> findWithoutLocation(List<Place> places);

    List<LocationEntity> findInBoundary(List<Place> places, LocationBound locationBoundary);

    List<LocationEntity> sortDistance(List<LocationEntity> places, Location myLocation);

    List<String> groupingSubdistrictCode(List<LocationEntity> places);

    List<WeightEntity> weightScoreForPlacesWithoutLocation(List<String> subdistrictCodes, List<Place> placeWithoutLocation);
}
