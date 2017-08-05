package org.tanrabad.survey.nearby;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.tanrabad.survey.entity.Place;

public class ImpNearbyPlacesWithoutLocation implements NearbyPlacesWithoutLocation {

    private final List<Place> places;

    public ImpNearbyPlacesWithoutLocation(List<Place> places) {
        this.places = places;
    }

    @Override public List<Place> getPlaces(List<Place> placeWithLocation) {
        List<Place> placesWithoutLocation = PlaceUtils.getPlacesWithoutLocation(places);
        if (placesWithoutLocation == null) return null;

        List<Place> placesWithoutLocationInsideSubdistrict =
                PlaceUtils.findPlacesWithoutLocationInsideSubdistrict(PlaceUtils.groupingSubdistict(placeWithLocation),
                        placesWithoutLocation);
        Map<UUID, Double> weightPlaceWithoutPlantationScore =
                WeightPlaceWithoutLocation.calculate(placeWithLocation, placesWithoutLocationInsideSubdistrict);
        for (Place place : placesWithoutLocationInsideSubdistrict) {
            place.setWeight(weightPlaceWithoutPlantationScore.get(place.getId()));
        }
        return placesWithoutLocationInsideSubdistrict;
    }
}
