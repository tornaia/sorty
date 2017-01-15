package com.github.tornaia.sorty.album;

import com.github.tornaia.sorty.image.Image;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Album {

    private final String name;

    private final Set<Image> images;

    public Album(String name, Set<Image> images) {
        this.name = name;
        this.images = Collections.unmodifiableSet(images);
    }

    public String getName() {
        return name;
    }

    public Set<Image> getImages() {
        return images;
    }
}
