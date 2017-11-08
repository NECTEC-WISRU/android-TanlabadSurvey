package org.tanrabad.survey.nearby;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.entity.field.Location;
import org.tanrabad.survey.entity.field.LocationBound;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ImpNearbyPlacesWithLocationTest {
    @Mock LocationBoundary locationBoundary;
    private ImpNearbyPlacesWithLocation impNearbyPlacesWithLocation;
    private Location myLocation = new Location(5, 5);
    private LocationBound locationBound = new LocationBound(new Location(0, 0), new Location(10, 10));


    @Test public void testFindPlaceInsideLocation() throws Exception {

        Place place1 = new Place(UUID.nameUUIDFromBytes("2".getBytes()), "a");
        place1.setLocation(new Location(1, 2));
        Place place2 = new Place(UUID.nameUUIDFromBytes("4".getBytes()), "b");
        place2.setLocation(new Location(2, 3));
        Place place3 = new Place(UUID.nameUUIDFromBytes("6".getBytes()), "c");
        place3.setLocation(new Location(11, 3));

        List<Place> places = new ArrayList<>();
        places.add(place1);
        places.add(place2);
        places.add(place3);

        List<Place> filteredPlace = new ArrayList<>();
        filteredPlace.add(place2);
        filteredPlace.add(place1);

        when(locationBoundary.get(myLocation, 5)).thenReturn(locationBound);

        impNearbyPlacesWithLocation = new ImpNearbyPlacesWithLocation(places, locationBoundary);

        assertEquals(filteredPlace, impNearbyPlacesWithLocation.getPlaces(myLocation));
    }

    @Test public void testFindPlaceInsideLocationNotFound() throws Exception {
        Place place1 = new Place(UUID.nameUUIDFromBytes("1".getBytes()), "d");
        place1.setLocation(new Location(12, 7));
        Place place2 = new Place(UUID.nameUUIDFromBytes("3".getBytes()), "e");
        place2.setLocation(new Location(15, 15));
        Place place3 = new Place(UUID.nameUUIDFromBytes("5".getBytes()), "f");
        place3.setLocation(new Location(11, 9));

        List<Place> places = new ArrayList<>();
        places.add(place1);
        places.add(place2);
        places.add(place3);

        when(locationBoundary.get(myLocation, 5)).thenReturn(locationBound);

        impNearbyPlacesWithLocation = new ImpNearbyPlacesWithLocation(places, locationBoundary);

        assertNull(impNearbyPlacesWithLocation.getPlaces(myLocation));
    }
}
