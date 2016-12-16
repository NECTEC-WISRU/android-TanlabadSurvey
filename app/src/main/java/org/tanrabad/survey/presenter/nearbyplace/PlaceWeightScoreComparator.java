package org.tanrabad.survey.presenter.nearbyplace;

import java.util.Comparator;
import org.tanrabad.survey.entity.Place;

/**
 * Created by CHNCS23 on 15/12/2559.
 */
public class PlaceWeightScoreComparator implements Comparator<Place> {
    @Override public int compare(Place place, Place anotherPlace) {

        double placeWeight = place.getWeight();
        double anotherPlaceWeight = anotherPlace.getWeight();
        if (placeWeight > anotherPlaceWeight) {
            return -1;
        }

        if (placeWeight < anotherPlaceWeight) {
            return 1;
        }

        return 0;
    }
}
