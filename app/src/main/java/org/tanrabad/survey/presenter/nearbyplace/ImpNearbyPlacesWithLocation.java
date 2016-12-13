package org.tanrabad.survey.presenter.nearbyplace;

import java.util.ArrayList;
import java.util.List;
import org.tanrabad.survey.domain.nearbyplaces.LocationBoundary;
import org.tanrabad.survey.domain.nearbyplaces.NearbyPlacesWithLocation;
import org.tanrabad.survey.domain.place.PlaceRepository;
import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.entity.field.Location;
import org.tanrabad.survey.entity.field.LocationBound;

public class ImpNearbyPlacesWithLocation implements NearbyPlacesWithLocation {

    public static final int DISTANCE_IN_KM = 5;
    private PlaceRepository placeRepository;
    private LocationBoundary locationBoundary;

    public ImpNearbyPlacesWithLocation(PlaceRepository placeRepository, LocationBoundary locationBoundary) {
        this.placeRepository = placeRepository;
        this.locationBoundary = locationBoundary;
    }

    @Override public List<Place> getPlaces(Location myLocation) {
        List<Place> places = placeRepository.find();

        if (places == null || places.isEmpty()) return null;

        return getPlaceInsideLocationBoundary(places, locationBoundary.get(myLocation, DISTANCE_IN_KM));
    }

    private List<Place> getPlaceInsideLocationBoundary(List<Place> places, LocationBound locationBoundary) {
        List<Place> placeInsideLocation = new ArrayList<>();
        Location minimumLocation = locationBoundary.getMinimumLocation();
        Location maximumLocation = locationBoundary.getMaximumLocation();
        for (Place eachPlace : places) {
            Location placeLocation = eachPlace.getLocation();
            if (placeLocation == null) continue;

            if (placeLocation.isLocationInsideBoundary(minimumLocation, maximumLocation)) {
                placeInsideLocation.add(eachPlace);
            }
        }
        return placeInsideLocation.isEmpty() ? null : placeInsideLocation;
    }
}
