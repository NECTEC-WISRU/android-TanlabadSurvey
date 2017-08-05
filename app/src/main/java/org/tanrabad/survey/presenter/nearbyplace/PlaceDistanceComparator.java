package org.tanrabad.survey.presenter.nearbyplace;

import java.util.Comparator;
import org.tanrabad.survey.domain.geographic.distance.EllipsoidDistance;
import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.entity.field.Location;

class PlaceDistanceComparator implements Comparator<Place> {
    private final Location myLocation;

    public PlaceDistanceComparator(Location myLocation) {
        this.myLocation = myLocation;
    }

    @Override public int compare(Place place, Place anotherPlace) {
        EllipsoidDistance ellipsoidDistance = new EllipsoidDistance();
        double thisPlaceDistance = ellipsoidDistance.calculate(place.getLocation(), myLocation);
        double thatPlaceDistance = ellipsoidDistance.calculate(anotherPlace.getLocation(), myLocation);
        if (thisPlaceDistance > thatPlaceDistance) {
            return 1;
        } else if (thisPlaceDistance < thatPlaceDistance) {
            return -1;
        } else {
            return 0;
        }
    }
}
