package com.github.tornaia.sorty.algorithm;

import com.github.tornaia.sorty.album.Album;
import com.github.tornaia.sorty.album.Albums;
import com.github.tornaia.sorty.geoname.GeoNameProvider;
import com.github.tornaia.sorty.image.Images;
import com.github.tornaia.sorty.image.Location;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class GroupingBasedOnDateAndLocation implements SortingStrategy {

    private GroupingBasedOnDate groupingBasedOnDate = new GroupingBasedOnDate();

    private GroupingBasedOnLocation groupingBasedOnLocation = new GroupingBasedOnLocation();

    private GeoNameProvider geoNameProvider = new GeoNameProvider();

    @Override
    public Albums sort(Images images) {
        Albums albumsGroupedByDate = groupingBasedOnDate.sort(images);

        List<Album> rawAlbums = new ArrayList<>();
        for (Album rawAlbum : albumsGroupedByDate.getAlbums()) {
            Albums perLocationRawAlbums = groupingBasedOnLocation.sort(new Images(new HashSet(rawAlbum.getImages())));
            for (Album perLocationRawAlbum : perLocationRawAlbums.getAlbums()) {
                String date = getDate(perLocationRawAlbum);
                String location = getLocation(perLocationRawAlbum);
                rawAlbums.add(new Album(date + " " + location, perLocationRawAlbum.getImages()));
            }
        }

        return new Albums(rawAlbums);
    }

    // FIXME move this logic to Album and introduce DateStatistics class or something like that
    private String getDate(Album album) {
        Date date = album.getImages().iterator().next().getImageMetaInfo().getDate().get();
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    private String getLocation(Album album) {
        Location location = album.getImages().iterator().next().getImageMetaInfo().getLocation().get();
        return geoNameProvider.find(location).getName();
    }
}
