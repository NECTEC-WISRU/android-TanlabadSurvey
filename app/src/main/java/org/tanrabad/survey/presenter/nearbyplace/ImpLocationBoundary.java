package org.tanrabad.survey.presenter.nearbyplace;

import android.util.Log;
import org.tanrabad.survey.domain.geographic.FilterBoundaryCalculate;
import org.tanrabad.survey.domain.nearbyplaces.LocationBoundary;
import org.tanrabad.survey.entity.field.Location;
import org.tanrabad.survey.entity.field.LocationBound;

public class ImpLocationBoundary implements LocationBoundary {
    @Override public LocationBound get(Location location, int distance) {
        FilterBoundaryCalculate filterBoundaryCalculate = new FilterBoundaryCalculate();
        Location minLocation = filterBoundaryCalculate.getMinLocation(location, distance);
        Location maxLocation = filterBoundaryCalculate.getMaxLocation(location, distance);
        Log.d("minLocation", minLocation.toString());
        Log.d("maxLocation", maxLocation.toString());
        return new LocationBound(minLocation, maxLocation);
    }
}
