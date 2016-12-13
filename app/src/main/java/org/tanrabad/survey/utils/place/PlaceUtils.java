package org.tanrabad.survey.utils.place;

import java.util.ArrayList;
import java.util.List;
import org.tanrabad.survey.entity.Place;

/**
 * Created by CHNCS23 on 13/12/2559.
 */

public class PlaceUtils {
    public static List<Place> getPlacesWithoutLocation(List<Place> places) {
        ArrayList<Place> placesWithoutLocation = new ArrayList<>();
        for (Place place : places) {
            if (place.getLocation() == null) {
                placesWithoutLocation.add(place);
            }
        }
        return placesWithoutLocation.isEmpty() ? null : placesWithoutLocation;
    }
}
