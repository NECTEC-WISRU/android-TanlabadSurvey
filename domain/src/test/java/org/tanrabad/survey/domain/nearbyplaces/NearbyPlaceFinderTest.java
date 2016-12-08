package org.tanrabad.survey.domain.nearbyplaces;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.tanrabad.survey.domain.place.PlaceRepository;
import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.entity.field.Location;

public class NearbyPlaceFinderTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    NearbyPlacesFinderController nearbyPlacesFinderController;
    private PlaceRepository placeRepository;

    @Before
    public void setup(){
        placeRepository = context.mock(PlaceRepository.class);
    }

    @Test public void testGetNearByPlaces() throws Exception {
        final List<Place> filteredPlaces = new ArrayList<>();
        Place place1 = new Place(UUID.nameUUIDFromBytes("2".getBytes()), "a");
        filteredPlaces.add(place1);
        Place place2 = new Place(UUID.nameUUIDFromBytes("4".getBytes()), "b");
        filteredPlaces.add(place2);
        Place place3 = new Place(UUID.nameUUIDFromBytes("6".getBytes()), "c");
        filteredPlaces.add(place3);
        Place place4 = new Place(UUID.nameUUIDFromBytes("8".getBytes()), "d");
        filteredPlaces.add(place4);
        Place place5 = new Place(UUID.nameUUIDFromBytes("10".getBytes()), "e");
        filteredPlaces.add(place5);
        Place place6 = new Place(UUID.nameUUIDFromBytes("12".getBytes()), "f");
        filteredPlaces.add(place6);
        Place place7 = new Place(UUID.nameUUIDFromBytes("14".getBytes()), "g");
        filteredPlaces.add(place7);
        Place place8 = new Place(UUID.nameUUIDFromBytes("16".getBytes()), "h");
        filteredPlaces.add(place8);
        Place place9 = new Place(UUID.nameUUIDFromBytes("18".getBytes()), "i");
        filteredPlaces.add(place9);
        Place place10 = new Place(UUID.nameUUIDFromBytes("20".getBytes()), "j");
        filteredPlaces.add(place10);


        context.checking(new Expectations(){
            {
                oneOf(placeRepository).find();
                will(returnValue(filteredPlaces));
            }
        });

        nearbyPlacesFinderController = new NearbyPlacesFinderController(placeRepository);
        Location myLocation = new Location(100,100);
        nearbyPlacesFinderController.findNearbyPlaces(myLocation);


    }
}
