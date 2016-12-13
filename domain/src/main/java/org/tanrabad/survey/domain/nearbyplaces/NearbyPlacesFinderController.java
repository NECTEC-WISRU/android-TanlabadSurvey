package org.tanrabad.survey.domain.nearbyplaces;

import java.util.List;
import org.tanrabad.survey.domain.place.PlaceListPresenter;
import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.entity.field.Location;

public class NearbyPlacesFinderController {
    private NearbyPlacesWithLocation nearbyPlacesWithLocation;
    private NearbyPlacesWithoutLocation nearbyPlacesWithoutLocation;
    private MergeAndSortNearbyPlaces mergeAndSortNearbyPlaces;
    private PlaceListPresenter placeListPresenter;

    public NearbyPlacesFinderController(NearbyPlacesWithLocation nearbyPlacesWithLocation,
            NearbyPlacesWithoutLocation nearbyPlacesWithoutLocation, MergeAndSortNearbyPlaces mergeAndSortNearbyPlaces,
            PlaceListPresenter placeListPresenter) {
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
            placeListPresenter.displayPlaceList(placeWithLocation);
        } else {
            List<Place> nearByPlaces = mergeAndSortNearbyPlaces.mergeAndSort(placeWithLocation, placeWithoutLocation);
            placeListPresenter.displayPlaceList(nearByPlaces);
        }
    }
}
