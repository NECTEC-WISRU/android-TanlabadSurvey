package org.tanrabad.survey.nearby;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.entity.field.Location;

public class NearbyPlaceFinderTest {
    @Rule public JUnitRuleMockery context = new JUnitRuleMockery();

    @Mock private NearbyPlacesWithLocation nearbyPlacesWithLocation;
    @Mock private NearbyPlacesWithoutLocation nearbyPlacesWithoutLocation;
    @Mock private MergeAndSortNearbyPlaces mergeAndSortNearbyPlaces;
    @Mock private NearbyPlacePresenter placeListPresenter;

    private Location myLocation = new Location(20, 20);
    private Place place1 = new Place(UUID.nameUUIDFromBytes("2".getBytes()), "a");
    private Place place2 = new Place(UUID.nameUUIDFromBytes("4".getBytes()), "b");
    private Place place3 = new Place(UUID.nameUUIDFromBytes("6".getBytes()), "c");
    private Place place4 = new Place(UUID.nameUUIDFromBytes("8".getBytes()), "d");
    private Place place5 = new Place(UUID.nameUUIDFromBytes("10".getBytes()), "e");

    @Test public void testGetNearByPlacesWithWithoutLocation() throws Exception {

        final List<Place> placeWithLocation = new ArrayList<>();
        placeWithLocation.add(place2);
        placeWithLocation.add(place3);
        placeWithLocation.add(place4);
        placeWithLocation.add(place5);

        final List<Place> placeWithoutLocation = new ArrayList<>();
        placeWithoutLocation.add(place1);

        final List<Place> filteredPlaces = new ArrayList<>();
        filteredPlaces.add(place1);
        filteredPlaces.add(place2);
        filteredPlaces.add(place3);
        filteredPlaces.add(place4);
        filteredPlaces.add(place5);

        context.checking(new Expectations() {
            {
                oneOf(nearbyPlacesWithLocation).getPlaces(myLocation);
                will(returnValue(placeWithLocation));
                oneOf(nearbyPlacesWithoutLocation).getPlaces(placeWithLocation);
                will(returnValue(placeWithoutLocation));
                oneOf(mergeAndSortNearbyPlaces).mergeAndSort(placeWithLocation, placeWithoutLocation);
                will(returnValue(filteredPlaces));
                oneOf(placeListPresenter).displayNearbyPlaces(filteredPlaces);
            }
        });

        NearbyPlacesFinderController nearbyPlacesFinderController =
                new NearbyPlacesFinderController(nearbyPlacesWithLocation, nearbyPlacesWithoutLocation,
                        mergeAndSortNearbyPlaces, placeListPresenter);

        nearbyPlacesFinderController.findNearbyPlaces(myLocation);
    }

    @Test public void testGetNearByPlacesButNoWithoutLocation() throws Exception {

        final List<Place> placeWithLocation = new ArrayList<>();
        placeWithLocation.add(place2);
        placeWithLocation.add(place3);
        placeWithLocation.add(place4);
        placeWithLocation.add(place5);

        final List<Place> placeWithoutLocation = new ArrayList<>();
        placeWithoutLocation.add(place1);

        context.checking(new Expectations() {
            {
                oneOf(nearbyPlacesWithLocation).getPlaces(myLocation);
                will(returnValue(placeWithLocation));
                oneOf(nearbyPlacesWithoutLocation).getPlaces(placeWithLocation);
                will(returnValue(null));
                never(mergeAndSortNearbyPlaces).mergeAndSort(placeWithLocation, placeWithoutLocation);
                oneOf(placeListPresenter).displayNearbyPlaces(placeWithLocation);
            }
        });

        NearbyPlacesFinderController nearbyPlacesFinderController =
                new NearbyPlacesFinderController(nearbyPlacesWithLocation, nearbyPlacesWithoutLocation,
                        mergeAndSortNearbyPlaces, placeListPresenter);

        nearbyPlacesFinderController.findNearbyPlaces(myLocation);
    }

    @Test public void testGetNearByPlacesNotFound() throws Exception {

        context.checking(new Expectations() {
            {
                oneOf(nearbyPlacesWithLocation).getPlaces(myLocation);
                will(returnValue(null));
                never(nearbyPlacesWithoutLocation).getPlaces(null);
                never(mergeAndSortNearbyPlaces).mergeAndSort(null, null);
                oneOf(placeListPresenter).displayPlaceNotFound();
            }
        });

        NearbyPlacesFinderController nearbyPlacesFinderController =
                new NearbyPlacesFinderController(nearbyPlacesWithLocation, nearbyPlacesWithoutLocation,
                        mergeAndSortNearbyPlaces, placeListPresenter);

        nearbyPlacesFinderController.findNearbyPlaces(myLocation);
    }
}
