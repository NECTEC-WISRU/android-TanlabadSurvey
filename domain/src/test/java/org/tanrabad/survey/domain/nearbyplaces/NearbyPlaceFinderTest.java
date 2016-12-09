package org.tanrabad.survey.domain.nearbyplaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.tanrabad.survey.domain.place.PlaceListPresenter;
import org.tanrabad.survey.domain.place.PlaceRepository;
import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.entity.field.Location;
import org.tanrabad.survey.entity.field.LocationBound;

public class NearbyPlaceFinderTest {
    private static final int DISTANCE_IN_KM = 5;
    @Rule public JUnitRuleMockery context = new JUnitRuleMockery();

    @Mock private PlaceRepository placeRepository;
    @Mock private LocationBoundary locationBoundary;
    @Mock private PlaceListPresenter placeListPresenter;
    @Mock private NearbyPlacesFilter nearbyPlacesFilter;
    private Location myLocation;

    private LocationBound locationBoundaryBox;
    private List<Place> allPlaces;

    @Before public void setup() {
        myLocation = new Location(20, 20);
        locationBoundaryBox = new LocationBound(new Location(10, 10), new Location(10, 10));

        allPlaces = new ArrayList<>();
        Place place1 = new Place(UUID.nameUUIDFromBytes("2".getBytes()), "a");
        allPlaces.add(place1);
        Place place2 = new Place(UUID.nameUUIDFromBytes("4".getBytes()), "b");
        allPlaces.add(place2);
        Place place3 = new Place(UUID.nameUUIDFromBytes("6".getBytes()), "c");
        allPlaces.add(place3);
        Place place4 = new Place(UUID.nameUUIDFromBytes("8".getBytes()), "d");
        allPlaces.add(place4);
        Place place5 = new Place(UUID.nameUUIDFromBytes("10".getBytes()), "e");
        allPlaces.add(place5);
        Place place6 = new Place(UUID.nameUUIDFromBytes("12".getBytes()), "f");
        allPlaces.add(place6);
        Place place7 = new Place(UUID.nameUUIDFromBytes("14".getBytes()), "g");
        allPlaces.add(place7);
        Place place8 = new Place(UUID.nameUUIDFromBytes("16".getBytes()), "h");
        allPlaces.add(place8);
        Place place9 = new Place(UUID.nameUUIDFromBytes("18".getBytes()), "i");
        allPlaces.add(place9);
        Place place10 = new Place(UUID.nameUUIDFromBytes("20".getBytes()), "j");
        allPlaces.add(place10);
    }

    @Test public void testGetNearByPlaces() throws Exception {

        final Place place1 = new Place(UUID.nameUUIDFromBytes("2".getBytes()), "a");
        final Place place2 = new Place(UUID.nameUUIDFromBytes("4".getBytes()), "b");
        final Place place3 = new Place(UUID.nameUUIDFromBytes("6".getBytes()), "c");
        final Place place4 = new Place(UUID.nameUUIDFromBytes("8".getBytes()), "d");
        final Place place5 = new Place(UUID.nameUUIDFromBytes("10".getBytes()), "e");

        final List<Place> placeWithoutLocation = new ArrayList<>();
        placeWithoutLocation.add(place1);

        final List<Place> weightedPlaceWithoutLocation = new ArrayList<>();
        weightedPlaceWithoutLocation.add(place1);

        final List<Place> placeWithLocation = new ArrayList<>();
        placeWithLocation.add(place2);
        placeWithLocation.add(place3);
        placeWithLocation.add(place4);
        placeWithLocation.add(place5);

        final List<Place> sortedPlaceWithLocation = new ArrayList<>();
        placeWithLocation.add(place3);
        placeWithLocation.add(place5);
        placeWithLocation.add(place2);
        placeWithLocation.add(place4);

        final List<Place> trimmedPlaceNameWithoutLocation = new ArrayList<>();
        trimmedPlaceNameWithoutLocation.add(place1);

        final List<Place> trimmedPlaceNameWithLocation = new ArrayList<>();
        trimmedPlaceNameWithLocation.add(place3);
        trimmedPlaceNameWithLocation.add(place5);
        trimmedPlaceNameWithLocation.add(place2);
        trimmedPlaceNameWithLocation.add(place4);

        final List<Place> weightedLcsPlaceWithoutLocation = new ArrayList<>();
        trimmedPlaceNameWithLocation.add(place1);

        final List<Place> filteredPlaces = new ArrayList<>();
        filteredPlaces.add(place1);
        filteredPlaces.add(place2);
        filteredPlaces.add(place3);
        filteredPlaces.add(place4);
        filteredPlaces.add(place5);

        final List<String> subdistrictCodeOfPlaceWithLocation = Arrays.asList("100101", "200101");

        context.checking(new Expectations() {
            {
                oneOf(locationBoundary).get(myLocation, DISTANCE_IN_KM);
                will(returnValue(locationBoundaryBox));
                oneOf(placeRepository).find();
                will(returnValue(allPlaces));
                oneOf(nearbyPlacesFilter).findWithoutLocation(allPlaces);
                will(returnValue(placeWithoutLocation));
                oneOf(nearbyPlacesFilter).findInBoundary(allPlaces, locationBoundaryBox);
                will(returnValue(placeWithLocation));
                oneOf(nearbyPlacesFilter).sortDistance(placeWithLocation, myLocation);
                will(returnValue(sortedPlaceWithLocation));
                oneOf(nearbyPlacesFilter).groupingSubdistrictCode(placeWithLocation);
                will(returnValue(subdistrictCodeOfPlaceWithLocation));
                oneOf(nearbyPlacesFilter).weightScoreForPlacesWithoutLocation(subdistrictCodeOfPlaceWithLocation,
                        placeWithoutLocation);
                will(returnValue(placeWithoutLocation));
                oneOf(nearbyPlacesFilter).trimCommonPlaceName(weightedPlaceWithoutLocation);
                will(returnValue(trimmedPlaceNameWithoutLocation));
                oneOf(nearbyPlacesFilter).trimCommonPlaceName(sortedPlaceWithLocation);
                will(returnValue(trimmedPlaceNameWithLocation));
                oneOf(nearbyPlacesFilter).findAverageLcsPercentageForPlaceWithoutLocation(
                        trimmedPlaceNameWithoutLocation,
                        trimmedPlaceNameWithLocation);
                will(returnValue(weightedLcsPlaceWithoutLocation));
                oneOf(nearbyPlacesFilter).addWeightFromCalculatedAverageLcsScore(weightedPlaceWithoutLocation,
                        weightedLcsPlaceWithoutLocation);
                will(returnValue(weightedPlaceWithoutLocation));
                oneOf(nearbyPlacesFilter).mergeAndSortPlace(sortedPlaceWithLocation, weightedPlaceWithoutLocation);
                will(returnValue(filteredPlaces));

                oneOf(placeListPresenter).displayPlaceList(filteredPlaces);
            }
        });

        NearbyPlacesFinderController nearbyPlacesFinderController =
                new NearbyPlacesFinderController(locationBoundary, placeRepository, placeListPresenter,
                        nearbyPlacesFilter);

        nearbyPlacesFinderController.findNearbyPlaces(myLocation);
    }
}
