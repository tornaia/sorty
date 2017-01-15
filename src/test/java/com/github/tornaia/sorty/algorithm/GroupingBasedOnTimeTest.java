package com.github.tornaia.sorty.algorithm;

import com.github.tornaia.sorty.album.AlbumMatcher;
import com.github.tornaia.sorty.album.Albums;
import com.github.tornaia.sorty.album.AlbumsMatcher;
import com.github.tornaia.sorty.image.Dimension;
import com.github.tornaia.sorty.image.Image;
import com.github.tornaia.sorty.image.ImageMetaInfo;
import com.github.tornaia.sorty.image.Location;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class GroupingBasedOnTimeTest {

    @InjectMocks
    private GroupingBasedOnTime groupingBasedOnTime;

    @Test
    public void oneAlbum() {
        Images images = new Images();
        images.add(createImage("2016-07-02 17:22:30", "640x480", "Siófok", "S7Edge"));
        images.add(createImage("2016-07-02 17:25:02", "640x480", "Siófok", "S7Edge"));
        images.add(createImage("2016-07-02 17:28:50", "640x480", "Siófok", "S7Edge"));
        images.add(createImage("2016-07-02 17:32:11", "640x480", "Siófok", "S7Edge"));
        Albums albums = groupingBasedOnTime.sort(images);

        assertThat(albums, new AlbumsMatcher()
                .albums(hasItems(
                        new AlbumMatcher().name("2016-07-02").size(4)))
                .size(1));
    }

    @Test
    public void twoAlbums() {
        Images images = new Images();
        images.add(createImage("2015-11-23 09:01:00", "640x480", "Keszthely", "S7Edge"));
        images.add(createImage("2015-11-23 09:08:59", "640x480", "Keszthely", "S7Edge"));
        images.add(createImage("2016-07-02 17:28:50", "640x480", "Siófok", "S7Edge"));
        images.add(createImage("2016-07-02 17:32:11", "640x480", "Siófok", "S7Edge"));
        Albums albums = groupingBasedOnTime.sort(images);

        assertThat(albums, new AlbumsMatcher()
                .albums(hasItems(
                        new AlbumMatcher().name("2015-11-23").size(2),
                        new AlbumMatcher().name("2016-07-02").size(2)))
                .size(2));
    }

    private Image createImage(String dateStr, String dimensionStr, String locationStr, String modelStr) {
        try {
            Optional<Date> date = dateStr != null ? Optional.of(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr)) : Optional.empty();
            Optional<Dimension> dimension = dimensionStr != null ? Optional.of(new Dimension(Double.parseDouble(dimensionStr.split("x")[0]), Double.parseDouble(dimensionStr.split("x")[1]))) : Optional.empty();
            Optional<Location> location = getLocation(locationStr);
            Optional<String> model = modelStr != null ? Optional.of(modelStr) : Optional.empty();
            ImageMetaInfo imageMetaInfo = new ImageMetaInfo(date, dimension, location, model);
            return new Image(imageMetaInfo, null);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<Location> getLocation(String locationStr) {
        if ("Siófok".equals(locationStr)) {
            return Optional.of(new Location(46.90413D, 18.058D));
        }
        return Optional.empty();
    }
}
