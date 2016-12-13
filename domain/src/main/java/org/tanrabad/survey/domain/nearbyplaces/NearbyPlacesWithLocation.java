package org.tanrabad.survey.domain.nearbyplaces;

import java.util.List;
import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.entity.field.Location;

public interface NearbyPlacesWithLocation {
    List<Place> getPlaces(Location myLocation);
}
