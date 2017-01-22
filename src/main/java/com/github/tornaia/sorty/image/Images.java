package com.github.tornaia.sorty.image;

import com.github.tornaia.sorty.image.Image;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class Images {

    private Set<Image> images = new HashSet<>();

    public void add(Image image) {
        images.add(image);
    }

    public void apply(Consumer<Image> action) {
        images.stream().forEach(action);
    }
}
