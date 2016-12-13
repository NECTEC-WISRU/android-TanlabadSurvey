package org.tanrabad.survey.utils.place;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Test;
import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.entity.field.Location;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PlaceUtilsTest {
    @Test public void findPlaceWithoutLocationTest() throws Exception {
        Place place1 = new Place(UUID.nameUUIDFromBytes("2".getBytes()), "a");
        Place place2 = new Place(UUID.nameUUIDFromBytes("4".getBytes()), "b");
        Place place3 = new Place(UUID.nameUUIDFromBytes("6".getBytes()), "c");
        place3.setLocation(new Location(11, 3));

        List<Place> places = new ArrayList<>();
        places.add(place1);
        places.add(place2);
        places.add(place3);

        List<Place> placesWithoutLocation = new ArrayList<>();
        placesWithoutLocation.add(place1);
        placesWithoutLocation.add(place2);

        assertEquals(placesWithoutLocation, PlaceUtils.getPlacesWithoutLocation(places));
    }

    @Test public void findPlaceWithoutLocationByAllPlacesHaveLocationTest() throws Exception {
        Place place1 = new Place(UUID.nameUUIDFromBytes("1".getBytes()), "d");
        place1.setLocation(new Location(7, 5));
        Place place2 = new Place(UUID.nameUUIDFromBytes("3".getBytes()), "e");
        place2.setLocation(new Location(9, 6));
        Place place3 = new Place(UUID.nameUUIDFromBytes("5".getBytes()), "f");
        place3.setLocation(new Location(11, 3));

        List<Place> places = new ArrayList<>();
        places.add(place1);
        places.add(place2);
        places.add(place3);

        assertNull(PlaceUtils.getPlacesWithoutLocation(places));
    }
}
