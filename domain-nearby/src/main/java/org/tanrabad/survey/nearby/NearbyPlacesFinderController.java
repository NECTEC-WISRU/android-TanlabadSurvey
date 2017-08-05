package org.tanrabad.survey.nearby;

import java.util.List;
import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.entity.field.Location;

public class NearbyPlacesFinderController {
    private NearbyPlacesWithLocation nearbyPlacesWithLocation;
    private NearbyPlacesWithoutLocation nearbyPlacesWithoutLocation;
    private MergeAndSortNearbyPlaces mergeAndSortNearbyPlaces;
    private NearbyPlacePresenter placeListPresenter;

    public NearbyPlacesFinderController(NearbyPlacesWithLocation nearbyPlacesWithLocation,
            NearbyPlacesWithoutLocation nearbyPlacesWithoutLocation, MergeAndSortNearbyPlaces mergeAndSortNearbyPlaces,
            NearbyPlacePresenter placeListPresenter) {
        this.nearbyPlacesWithLocation = nearbyPlacesWithLocation;
        this.nearbyPlacesWithoutLocation = nearbyPlacesWithoutLocation;
        this.mergeAndSortNearbyPlaces = mergeAndSortNearbyPlaces;
        this.placeListPresenter = placeListPresenter;
    }

    public void findNearbyPlaces(Location myLocation) {
        List<Place> placeWithLocation = nearbyPlacesWithLocation.getPlaces(myLocation);

        if (placeWithLocation == null) {
            placeListPresenter.displayPlaceNotFound();
            return;
        }

        List<Place> placeWithoutLocation = nearbyPlacesWithoutLocation.getPlaces(placeWithLocation);

        if (placeWithoutLocation == null) {
            placeListPresenter.displayNearbyPlaces(placeWithLocation);
        } else {
            List<Place> nearByPlaces = mergeAndSortNearbyPlaces.mergeAndSort(placeWithLocation, placeWithoutLocation);
            placeListPresenter.displayNearbyPlaces(nearByPlaces);
        }
    }
}
