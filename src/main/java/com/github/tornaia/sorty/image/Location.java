package com.github.tornaia.sorty.image;

public class Location {

    private final double latitude;

    private final double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Distance distanceFrom(Location otherLocation) {
        double distanceInMeter = distance(latitude, otherLocation.latitude, longitude, otherLocation.longitude, 0.0D, 0.0D);
        return Distance.ofMeter(distanceInMeter);
    }

    /*
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns distance in meters
     */
    private static double distance(double lat1, double lat2, double lon1, double lon2, double el1, double el2) {
        double earthRadiusInKilometer = 6371.0D;

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distanceInMeters = earthRadiusInKilometer * c * 1000;

        double height = el1 - el2;

        distanceInMeters = Math.pow(distanceInMeters, 2) + Math.pow(height, 2);

        return Math.sqrt(distanceInMeters);
    }
}
