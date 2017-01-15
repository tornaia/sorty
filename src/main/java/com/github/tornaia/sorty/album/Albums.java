package com.github.tornaia.sorty.album;

import java.util.Collections;
import java.util.List;

public class Albums {

    private final List<Album> albums;

    public Albums(List<Album> albums) {
        this.albums = albums;
    }

    public List<Album> getAlbums() {
        return Collections.unmodifiableList(albums);
    }
}
