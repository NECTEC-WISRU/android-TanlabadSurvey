package org.tanrabad.survey.domain.nearbyplaces;

import java.util.List;
import org.tanrabad.survey.domain.place.PlaceListPresenter;
import org.tanrabad.survey.domain.place.PlaceRepository;
import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.entity.field.Location;
import org.tanrabad.survey.entity.field.LocationBound;

/**
 * Created by CHNCS23 on 6/12/2559.
 */

public class NearbyPlacesFinderController {
    private static final int DISTANCE_IN_KM = 5;
    private LocationBoundary locationBoundary;
    private PlaceRepository placeRepository;
    private PlaceListPresenter placeListPresenter;
    private NearbyPlacesFilter nearbyPlacesFilter;

    public NearbyPlacesFinderController(LocationBoundary locationBoundary, PlaceRepository placeRepository,
            PlaceListPresenter placeListPresenter, NearbyPlacesFilter nearbyPlacesFilter) {
        this.locationBoundary = locationBoundary;
        this.placeRepository = placeRepository;
        this.placeListPresenter = placeListPresenter;
        this.nearbyPlacesFilter = nearbyPlacesFilter;
    }

    public void findNearbyPlaces(Location myLocation) {
        LocationBound locationBound = locationBoundary.get(myLocation, DISTANCE_IN_KM);
        List<Place> places = placeRepository.find();
        List<Place> placeWithoutLocation = nearbyPlacesFilter.findWithoutLocation(places);
        List<Place> placeInBoundary = nearbyPlacesFilter.findInBoundary(places, locationBound);
        List<Place> sortedPlaceInBoundaryByDistance = nearbyPlacesFilter.sortDistance(placeInBoundary, myLocation);
        List<String> subdistrictOfPlaceInBoundary = nearbyPlacesFilter.groupingSubdistrictCode(placeInBoundary);
        List<Place> weightedPlaceWithoutLocation =
                nearbyPlacesFilter.weightScoreForPlacesWithoutLocation(subdistrictOfPlaceInBoundary,
                        placeWithoutLocation);
        List<Place> trimmedPlaceWithoutLocation = nearbyPlacesFilter.trimCommonPlaceName(weightedPlaceWithoutLocation);
        List<Place> trimmedPlaceInBoundary = nearbyPlacesFilter.trimCommonPlaceName(sortedPlaceInBoundaryByDistance);
        List<Place> averageLcsForPlaceWithoutLocation =
                nearbyPlacesFilter.findAverageLcsPercentageForPlaceWithoutLocation(trimmedPlaceWithoutLocation,
                        trimmedPlaceInBoundary);
        nearbyPlacesFilter.addWeightFromCalculatedAverageLcsScore(weightedPlaceWithoutLocation,
                averageLcsForPlaceWithoutLocation);

        if (places != null) {
            placeListPresenter.displayPlaceList(places);
        } else {
            placeListPresenter.displayPlaceNotFound();
        }
    }
}
