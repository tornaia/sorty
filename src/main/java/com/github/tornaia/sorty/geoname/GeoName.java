package com.github.tornaia.sorty.geoname;

import com.github.tornaia.sorty.image.Location;

public class GeoName {

    private final String name;
    private final Location location;

    public GeoName(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }
}
