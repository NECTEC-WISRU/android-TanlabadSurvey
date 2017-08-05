package org.tanrabad.survey.nearby;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.entity.field.Location;
import org.tanrabad.survey.entity.field.LocationBound;

public class ImpNearbyPlacesWithLocation implements NearbyPlacesWithLocation {

    public static final int DISTANCE_IN_KM = 5;
    private final List<Place> places;
    private LocationBoundary locationBoundary;

    public ImpNearbyPlacesWithLocation(List<Place> places, LocationBoundary locationBoundary) {
        this.places = places;
        this.locationBoundary = locationBoundary;
    }

    @Override public List<Place> getPlaces(final Location myLocation) {
        if (places == null || places.isEmpty()) return null;

        List<Place> placeInsideLocationBoundary =
                getPlaceInsideLocationBoundary(places, locationBoundary.get(myLocation, DISTANCE_IN_KM));
        Collections.sort(placeInsideLocationBoundary, new PlaceDistanceComparator(myLocation));
        return placeInsideLocationBoundary.isEmpty() ? null : placeInsideLocationBoundary;
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
        return placeInsideLocation;
    }
}
