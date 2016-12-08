package org.tanrabad.survey.domain.nearbyplaces;

import java.util.ArrayList;
import java.util.List;
import org.tanrabad.survey.domain.place.PlaceListPresenter;
import org.tanrabad.survey.domain.place.PlaceRepository;
import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.entity.field.Location;

/**
 * Created by CHNCS23 on 6/12/2559.
 */

public class NearbyPlacesFinderController {
    private LocationBoundary locationBoundary;
    private PlaceRepository placeRepository;
    private PlaceListPresenter placeListPresenter;
    public static final int DISTANCE_IN_KM = 5;

    public NearbyPlacesFinderController(LocationBoundary locationBoundary, PlaceRepository placeRepository,
            PlaceListPresenter placeListPresenter) {
        this.locationBoundary = locationBoundary;
        this.placeRepository = placeRepository;
        this.placeListPresenter = placeListPresenter;
    }

    public void findNearbyPlaces(Location myLocation) {
        locationBoundary.get(myLocation, DISTANCE_IN_KM);
        List<Place> places = placeRepository.find();

        if (places != null) {
            placeListPresenter.displayPlaceList(places);
        } else {
            placeListPresenter.displayPlaceNotFound();
        }
    }
}
