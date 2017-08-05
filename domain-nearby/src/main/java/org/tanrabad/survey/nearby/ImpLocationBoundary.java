package org.tanrabad.survey.nearby;

import org.tanrabad.survey.entity.field.Location;
import org.tanrabad.survey.entity.field.LocationBound;

public class ImpLocationBoundary implements LocationBoundary {

    private static final double KM_PER_LNG = 111.699;
    private static final double KM_PER_LAT = 110.567;

    @Override public LocationBound get(Location location, int distance) {
        Location minLocation = getMinLocation(location, distance);
        Location maxLocation = getMaxLocation(location, distance);
        return new LocationBound(minLocation, maxLocation);
    }

    private Location getMinLocation(Location currentLocation, double distanceInKm) {
        double longitudeDegreeFromKm = distanceInKm / KM_PER_LNG;
        double latitudeDegreeFromKm = distanceInKm / KM_PER_LAT;
        double minimumLongitude = currentLocation.getLongitude() - longitudeDegreeFromKm;
        double minimumLatitude = currentLocation.getLatitude() - latitudeDegreeFromKm;

        return new Location(minimumLatitude, minimumLongitude);
    }

    private Location getMaxLocation(Location currentLocation, double distanceInKm) {
        double longitudeDegreeFromKm = distanceInKm / KM_PER_LNG;
        double latitudeDegreeFromKm = distanceInKm / KM_PER_LAT;
        double maximumLongitude = currentLocation.getLongitude() + longitudeDegreeFromKm;
        double maximumLatitude = currentLocation.getLatitude() + latitudeDegreeFromKm;

        return new Location(maximumLatitude, maximumLongitude);
    }
}
