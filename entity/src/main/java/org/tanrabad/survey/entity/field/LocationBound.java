package org.tanrabad.survey.entity.field;

/**
 * Created by CHNCS23 on 8/12/2559.
 */

public class LocationBound {
    private Location minimumLocation;
    private Location maximumLocation;

    public LocationBound(Location minimumLocation, Location maximumLocation) {
        this.minimumLocation = minimumLocation;
        this.maximumLocation = maximumLocation;
    }

    public Location getMinimumLocation() {
        return minimumLocation;
    }

    public void setMinimumLocation(Location minimumLocation) {
        this.minimumLocation = minimumLocation;
    }

    public Location getMaximumLocation() {
        return maximumLocation;
    }

    public void setMaximumLocation(Location maximumLocation) {
        this.maximumLocation = maximumLocation;
    }
}
