package org.tanrabad.survey.domain.nearbyplaces;

import java.util.List;
import org.tanrabad.survey.domain.place.PlaceRepository;
import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.entity.field.Location;

/**
 * Created by CHNCS23 on 6/12/2559.
 */

public class NearbyPlacesFinderController {
    private PlaceRepository placeRepository;

    public NearbyPlacesFinderController(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public List<Place> findNearbyPlaces(Location myLocation) {
        return placeRepository.find();
    }
}
