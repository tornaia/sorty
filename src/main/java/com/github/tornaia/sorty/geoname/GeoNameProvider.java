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

    private static final Set<GeoName> GEO_NAMES = new HashSet<>();

    static {
        load("HU");
        load("CH");
    }

    public GeoName find(Location location) {
        GeoName closestGeoName = null;
        for (GeoName candidateGeoName : GEO_NAMES) {
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

    private static void load(String countryCode) {
        String countryTxt;
        try {
            InputStream systemResourceAsStream = ClassLoader.getSystemResourceAsStream(countryCode + ".txt");
            countryTxt = IOUtils.toString(systemResourceAsStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read db file", e);
        }
        String[] lines = countryTxt.split("\n");
        int nameIdx = 1;
        int latIdx = 4;
        int lonIdx = 5;
        for (String line : lines) {
            String[] values = line.split("\t");
            String name = values[nameIdx];
            Location location = new Location(Double.parseDouble(values[latIdx]), Double.parseDouble(values[lonIdx]));
            GeoName geoName = new GeoName(name, location);
            GEO_NAMES.add(geoName);
        }
    }
}
