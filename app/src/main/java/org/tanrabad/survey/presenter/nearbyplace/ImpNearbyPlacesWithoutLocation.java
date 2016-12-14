package org.tanrabad.survey.presenter.nearbyplace;

import java.util.List;
import org.tanrabad.survey.domain.nearbyplaces.NearbyPlacesWithoutLocation;
import org.tanrabad.survey.domain.place.PlaceRepository;
import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.utils.place.PlaceUtils;

/**
 * Created by CHNCS23 on 13/12/2559.
 */

public class ImpNearbyPlacesWithoutLocation implements NearbyPlacesWithoutLocation {

    private PlaceRepository placeRepository;

    public ImpNearbyPlacesWithoutLocation(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override public List<Place> getPlaces(List<Place> placeWithLocation) {
        List<Place> places = placeRepository.find();
        List<Place> placesWithoutLocationInsideSubdistrict =
                PlaceUtils.findPlacesWithoutLocationInsideSubdistrict(PlaceUtils.groupingSubdistict(places),
                        PlaceUtils.getPlacesWithoutLocation(places));

        return null;
    }
}
