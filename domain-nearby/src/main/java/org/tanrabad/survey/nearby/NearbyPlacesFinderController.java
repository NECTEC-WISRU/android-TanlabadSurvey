package org.tanrabad.survey.nearby;

import java.util.List;
import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.entity.field.Location;

public class NearbyPlacesFinderController {
    private NearbyPlaceRepository nearbyPlaceRepository;
    private MergeAndSortNearbyPlaces mergeAndSortNearbyPlaces;
    private NearbyPlacePresenter placeListPresenter;

    public NearbyPlacesFinderController(NearbyPlaceRepository nearbyPlaceRepository,
                                        MergeAndSortNearbyPlaces mergeAndSortNearbyPlaces,
                                        NearbyPlacePresenter placeListPresenter) {
        this.nearbyPlaceRepository = nearbyPlaceRepository;
        this.mergeAndSortNearbyPlaces = mergeAndSortNearbyPlaces;
        this.placeListPresenter = placeListPresenter;
    }

    public void findNearbyPlaces(Location myLocation) {
        List<Place> nearbyPlace = nearbyPlaceRepository.findByLocation(myLocation);

        if (nearbyPlace == null) {
            placeListPresenter.displayPlaceNotFound();
            return;
        }
        List<Place> nearbyPlaceWithoutLo = nearbyPlaceRepository.findByPlaces(nearbyPlace);

        if (nearbyPlaceWithoutLo == null) {
            placeListPresenter.displayNearbyPlaces(nearbyPlace);
        } else {
            List<Place> nearByPlaces = mergeAndSortNearbyPlaces.mergeAndSort(nearbyPlace, nearbyPlaceWithoutLo);
            placeListPresenter.displayNearbyPlaces(nearByPlaces);
        }
    }
}
