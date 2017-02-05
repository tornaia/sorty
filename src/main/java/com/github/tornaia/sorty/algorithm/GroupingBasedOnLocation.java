package com.github.tornaia.sorty.algorithm;

import com.github.tornaia.sorty.album.Album;
import com.github.tornaia.sorty.album.Albums;
import com.github.tornaia.sorty.geoname.GeoNameProvider;
import com.github.tornaia.sorty.image.Distance;
import com.github.tornaia.sorty.image.Image;
import com.github.tornaia.sorty.image.Images;
import com.github.tornaia.sorty.image.Location;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupingBasedOnLocation implements SortingStrategy {

    private GeoNameProvider geoNameProvider = new GeoNameProvider();

    private Distance tolerance = Distance.ofKilometer(20);

    @Override
    public Albums sort(Images images) {
        Dataset unorganizedDataset = new DefaultDataset();

        images.apply(image -> image.getImageMetaInfo().getLocation().ifPresent(location -> unorganizedDataset.add(new DenseInstance(new double[]{location.getLatitude(), location.getLongitude()}))));

        Dataset[] clusters = null;
        outer:
        for (int numberOfClusters = 1; numberOfClusters <= unorganizedDataset.size(); ++numberOfClusters) {
            KMeans kMeans = new KMeans(numberOfClusters);
            clusters = kMeans.cluster(unorganizedDataset);
            for (Dataset cluster : clusters) {
                Distance maxDistance = getMaxDistance(cluster);
                if (!maxDistance.isSmaller(tolerance)) {
                    continue outer;
                }
            }
            break;
        }

        if (clusters == null) {
            throw new IllegalStateException("Cluster was not created");
        }

        List<Location> locations = Arrays.stream(clusters)
                .map(dataset -> getCentre(dataset))
                .collect(Collectors.toList());

        Map<Location, Images> locationAndImages = new HashMap<>();
        locations.forEach(location -> locationAndImages.put(location, new Images()));

        for (Image image : images.getImages()) {
            Location imageLocation = image.getImageMetaInfo().getLocation().get();
            Location closestLocation = null;
            for (Location location : locations) {
                if (closestLocation == null) {
                    closestLocation = location;
                    continue;
                }
                if (imageLocation.distanceFrom(location).getAmountInMeter() < imageLocation.distanceFrom(closestLocation).getAmountInMeter()) {
                    closestLocation = location;
                }
            }
            locationAndImages.get(closestLocation).add(image);
        }

        List<Album> albums = new ArrayList<>();
        for (Map.Entry<Location, Images> entry : locationAndImages.entrySet()) {
            Location location = entry.getKey();
            String albumName = geoNameProvider.find(location).getName();
            albums.add(new Album(albumName, entry.getValue().getImages()));
        }

        return new Albums(albums);
    }

    private Distance getMaxDistance(Dataset cluster) {
        Distance max = Distance.ofMeter(0D);
        for (int i = 0; i < cluster.size(); ++i) {
            Instance instanceA = cluster.get(i);
            double latitudeA = instanceA.value(0);
            double longitudeA = instanceA.value(1);
            Location locationA = new Location(latitudeA, longitudeA);
            for (int j = 0; j < cluster.size(); ++j) {
                Instance instanceB = cluster.get(j);
                double latitudeB = instanceB.value(0);
                double longitudeB = instanceB.value(1);
                Location locationB = new Location(latitudeB, longitudeB);
                Distance distance = locationA.distanceFrom(locationB);
                max = Distance.max(max, distance);
            }
        }
        return max;
    }

    private Location getCentre(Dataset cluster) {
        if (cluster.isEmpty()) {
            throw new IllegalArgumentException("Empty cluster");
        }

        double avgLat = cluster.stream().mapToDouble(instance -> instance.value(0)).summaryStatistics().getAverage();
        double avgLon = cluster.stream().mapToDouble(instance -> instance.value(1)).summaryStatistics().getAverage();
        return new Location(avgLat, avgLon);
    }
}
