package org.tanrabad.survey.domain.nearbyplaces;

import java.util.List;
import org.tanrabad.survey.entity.Place;

public interface NearbyPlacesWithoutLocation {
    List<Place> getPlaces(List<Place> placeWithLocation);
}
