package com.github.tornaia.sorty.image;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class Images {

    private Set<Image> images = new HashSet<>();

    public Images() {
    }

    public Images(Set<Image> images) {
        this.images.addAll(images);
    }

    public void add(Image image) {
        images.add(image);
    }

    public void apply(Consumer<Image> action) {
        images.stream().forEach(action);
    }

    public Set<Image> getImages() {
        return Collections.unmodifiableSet(images);
    }
}
