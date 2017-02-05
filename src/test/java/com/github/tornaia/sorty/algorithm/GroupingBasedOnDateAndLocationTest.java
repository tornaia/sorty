package com.github.tornaia.sorty.algorithm;

import com.github.tornaia.sorty.album.AlbumMatcher;
import com.github.tornaia.sorty.album.Albums;
import com.github.tornaia.sorty.album.AlbumsMatcher;
import com.github.tornaia.sorty.image.ImageBuilder;
import com.github.tornaia.sorty.image.Images;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

public class GroupingBasedOnDateAndLocationTest {

    private GroupingBasedOnDateAndLocation groupingBasedOnDateAndLocation = new GroupingBasedOnDateAndLocation();

    @Test
    public void sameDaySameLocation() {
        Images images = new Images();
        images.add(new ImageBuilder().date("2017-02-05 09:01:00").dimension("640x480").location("Siófok").model("S7Edge").create());
        images.add(new ImageBuilder().date("2017-02-05 19:33:21").dimension("800x600").location("Siófok").model("Iphone6s").create());
        Albums albums = groupingBasedOnDateAndLocation.sort(images);

        assertThat(albums, new AlbumsMatcher()
                .albums(hasItems(
                        new AlbumMatcher().name("2017-02-05 Siófok").size(2)))
                .size(1));
    }

    @Test
    public void sameDayDifferentLocations() {
        Images images = new Images();
        images.add(new ImageBuilder().date("2017-02-05 09:01:00").dimension("640x480").location("Keszthely").model("S7Edge").create());
        images.add(new ImageBuilder().date("2017-02-05 09:08:59").dimension("640x480").location("Keszthely").model("S7Edge").create());
        images.add(new ImageBuilder().date("2017-02-05 17:28:50").dimension("640x480").location("Siófok").model("S7Edge").create());
        images.add(new ImageBuilder().date("2017-02-05 17:32:11").dimension("640x480").location("Siófok").model("S7Edge").create());
        Albums albums = groupingBasedOnDateAndLocation.sort(images);

        assertThat(albums, new AlbumsMatcher()
                .albums(hasItems(
                        new AlbumMatcher().name("2017-02-05 Keszthely").size(2),
                        new AlbumMatcher().name("2017-02-05 Siófok").size(2)))
                .size(2));
    }

    @Test
    public void differentDaysDifferentLocations() {
        Images images = new Images();
        images.add(new ImageBuilder().date("2017-02-05 09:01:00").dimension("640x480").location("Keszthely").model("S7Edge").create());
        images.add(new ImageBuilder().date("2017-02-06 09:08:59").dimension("640x480").location("Keszthely").model("S7Edge").create());
        images.add(new ImageBuilder().date("2017-02-05 17:28:50").dimension("640x480").location("Siófok").model("S7Edge").create());
        images.add(new ImageBuilder().date("2017-02-06 17:32:11").dimension("640x480").location("Siófok").model("S7Edge").create());
        Albums albums = groupingBasedOnDateAndLocation.sort(images);

        assertThat(albums, new AlbumsMatcher()
                .albums(hasItems(
                        new AlbumMatcher().name("2017-02-05 Keszthely").size(1),
                        new AlbumMatcher().name("2017-02-06 Keszthely").size(1),
                        new AlbumMatcher().name("2017-02-05 Siófok").size(1),
                        new AlbumMatcher().name("2017-02-06 Siófok").size(1)))
                .size(4));
    }
}
