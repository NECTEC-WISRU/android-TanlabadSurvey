package org.tanrabad.survey.nearby;

import org.tanrabad.survey.entity.field.Location;
import org.tanrabad.survey.entity.field.LocationBound;

public class ImpLocationBoundary implements LocationBoundary {
    @Override public LocationBound get(Location location, int distance) {
        FilterBoundaryCalculate filterBoundaryCalculate = new FilterBoundaryCalculate();
        Location minLocation = filterBoundaryCalculate.getMinLocation(location, distance);
        Location maxLocation = filterBoundaryCalculate.getMaxLocation(location, distance);
        return new LocationBound(minLocation, maxLocation);
    }
}
