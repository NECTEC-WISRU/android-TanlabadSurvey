package org.tanrabad.survey.presenter.nearbyplace;

import java.util.ArrayList;
import java.util.List;
import org.tanrabad.survey.domain.nearbyplaces.NearbyPlacesFilter;
import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.entity.field.Location;
import org.tanrabad.survey.entity.field.LocationBound;

public class NearbyPlacesFilterImplement implements NearbyPlacesFilter {

    @Override public List<Place> findWithoutLocation(List<Place> places) {
        List<Place> placesWithoutLocation = new ArrayList<>();
        for (Place eachPlace : places) {
            if (eachPlace.getLocation() == null) placesWithoutLocation.add(eachPlace);
        }
        return placesWithoutLocation.isEmpty() ? null : placesWithoutLocation;
    }

    @Override public List<Place> findInBoundary(List<Place> places, LocationBound locationBoundary) {

        return null;
    }

    @Override public List<Place> sortDistance(List<Place> places, Location myLocation) {
        return null;
    }

    @Override public List<String> groupingSubdistrictCode(List<Place> places) {
        return null;
    }

    @Override public List<Place> weightScoreForPlacesWithoutLocation(List<String> subdistrictCodes,
            List<Place> placeWithoutLocation) {
        return null;
    }

    @Override public List<Place> trimCommonPlaceName(List<Place> places) {
        return null;
    }

    @Override public List<Place> findAverageLcsPercentageForPlaceWithoutLocation(List<Place> placesWithoutLocation,
            List<Place> placeWithLocation) {
        return null;
    }

    @Override public List<Place> addWeightFromCalculatedAverageLcsScore(List<Place> weightedPlaceWithoutLocation,
            List<Place> averageLcsForPlaceWithoutLocation) {
        return null;
    }

    @Override
    public List<Place> mergeAndSortPlace(List<Place> placeWithLocation, List<Place> weightedPlaceWithoutLocation) {
        return null;
    }
}
