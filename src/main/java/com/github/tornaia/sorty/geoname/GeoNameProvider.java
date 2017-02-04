package com.github.tornaia.sorty.geoname;

import com.github.tornaia.sorty.image.Distance;
import com.github.tornaia.sorty.image.Location;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class GeoNameProvider {

    private Set<GeoName> geoNames = new HashSet<>();

    public GeoNameProvider() {
        String huTxt;
        try {
            InputStream systemResourceAsStream = ClassLoader.getSystemResourceAsStream("HU.txt");
            huTxt = IOUtils.toString(systemResourceAsStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read db file", e);
        }
        String[] lines = huTxt.split("\n");
        int nameIdx = 1;
        int latIdx = 4;
        int lonIdx = 5;
        for (String line : lines) {
            String[] values = line.split("\t");
            String name = values[nameIdx];
            Location location = new Location(Double.parseDouble(values[latIdx]), Double.parseDouble(values[lonIdx]));
            GeoName geoName = new GeoName(name, location);
            geoNames.add(geoName);
        }

        if (geoNames.isEmpty()) {
            throw new IllegalStateException("GeoName db is empty");
        }
    }

    public GeoName find(Location location) {
        GeoName closestGeoName = null;
        for (GeoName candidateGeoName : geoNames) {
            if (candidateGeoName.getName().equals("Ecsér")) {
                continue;
            }

            if (closestGeoName == null) {
                closestGeoName = candidateGeoName;
                continue;
            }
            Distance candidateDistanceFromLocation = candidateGeoName.getLocation().distanceFrom(location);
            Distance currentDistanceFromLocation = closestGeoName.getLocation().distanceFrom(location);
            if (candidateDistanceFromLocation.isSmaller(currentDistanceFromLocation)) {
                closestGeoName = candidateGeoName;
            }
        }
        return closestGeoName;
    }
}
