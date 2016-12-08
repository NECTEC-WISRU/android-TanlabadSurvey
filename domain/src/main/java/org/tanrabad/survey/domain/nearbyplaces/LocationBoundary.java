package org.tanrabad.survey.domain.nearbyplaces;

import org.tanrabad.survey.entity.field.Location;
import org.tanrabad.survey.entity.field.LocationBound;

public interface LocationBoundary {
    LocationBound get(Location location, int distance);
}
