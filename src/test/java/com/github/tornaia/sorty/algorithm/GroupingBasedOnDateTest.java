package com.github.tornaia.sorty.algorithm;

import com.github.tornaia.sorty.album.AlbumMatcher;
import com.github.tornaia.sorty.album.Albums;
import com.github.tornaia.sorty.album.AlbumsMatcher;
import com.github.tornaia.sorty.image.Images;
import com.github.tornaia.sorty.image.*;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

public class GroupingBasedOnDateTest {

    private GroupingBasedOnDate groupingBasedOnDate = new GroupingBasedOnDate();

    @Test
    public void oneAlbum() {
        Images images = new Images();
        images.add(new ImageBuilder().date("2016-07-02 17:22:30").dimension("640x480").location("Siófok").model("S7Edge").create());
        images.add(new ImageBuilder().date("2016-07-02 17:26:02").dimension("640x480").location("Siófok").model("S7Edge").create());
        images.add(new ImageBuilder().date("2016-07-02 17:28:50").dimension("640x480").location("Siófok").model("S7Edge").create());
        images.add(new ImageBuilder().date("2016-07-02 17:32:11").dimension("640x480").location("Siófok").model("S7Edge").create());
        Albums albums = groupingBasedOnDate.sort(images);

        assertThat(albums, new AlbumsMatcher()
                .albums(hasItems(
                        new AlbumMatcher().name("2016-07-02").size(4)))
                .size(1));
    }

    @Test
    public void twoAlbums() {
        Images images = new Images();
        images.add(new ImageBuilder().date("2015-11-23 09:01:00").dimension("640x480").location("Keszthely").model("S7Edge").create());
        images.add(new ImageBuilder().date("2015-11-23 09:08:59").dimension("640x480").location("Keszthely").model("S7Edge").create());
        images.add(new ImageBuilder().date("2016-07-02 17:28:50").dimension("640x480").location("Siófok").model("S7Edge").create());
        images.add(new ImageBuilder().date("2016-07-02 17:32:11").dimension("640x480").location("Siófok").model("S7Edge").create());
        Albums albums = groupingBasedOnDate.sort(images);

        assertThat(albums, new AlbumsMatcher()
                .albums(hasItems(
                        new AlbumMatcher().name("2015-11-23").size(2),
                        new AlbumMatcher().name("2016-07-02").size(2)))
                .size(2));
    }
}
