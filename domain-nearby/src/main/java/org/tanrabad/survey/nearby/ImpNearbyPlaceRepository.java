package org.tanrabad.survey.nearby;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.entity.field.Location;
import org.tanrabad.survey.entity.field.LocationBound;

public class ImpNearbyPlaceRepository implements NearbyPlaceRepository {

    public static final int DISTANCE_IN_KM = 5;
    private final List<Place> places;
    private LocationBoundary locationBoundary;

    public ImpNearbyPlaceRepository(List<Place> allPlaces, LocationBoundary locationBoundary) {
        this.places = allPlaces;
        this.locationBoundary = locationBoundary;
    }

    @Override public List<Place> findByLocation(final Location location) {
        if (places == null || places.isEmpty()) return null;

        List<Place> place = getPlaceInsideLocationBoundary(places, locationBoundary.get(location, DISTANCE_IN_KM));
        Collections.sort(place, new PlaceDistanceComparator(location));
        return place.isEmpty() ? null : place;
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

    @Override
    public List<Place> findByPlaces(List<Place> nearbyPlaces) {
        List<Place> placesWithoutLocation = PlaceUtils.getPlacesWithoutLocation(places);
        if (placesWithoutLocation == null) return null;

        List<Place> placesWithoutLocationInsideSubdistrict =
            PlaceUtils.findPlacesWithoutLocationInsideSubdistrict(PlaceUtils.groupingSubdistict(nearbyPlaces),
                placesWithoutLocation);
        Map<UUID, Double> weightPlaceWithoutPlantationScore =
            WeightPlaceWithoutLocation.calculate(nearbyPlaces, placesWithoutLocationInsideSubdistrict);
        for (Place place : placesWithoutLocationInsideSubdistrict) {
            place.setWeight(weightPlaceWithoutPlantationScore.get(place.getId()));
        }
        return placesWithoutLocationInsideSubdistrict;

    }
}
