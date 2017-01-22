package com.github.tornaia.sorty.image;

import java.nio.file.Path;

public class Image {

    private final ImageMetaInfo imageMetaInfo;
    private final Path source;

    public Image(ImageMetaInfo imageMetaInfo, Path source) {
        this.imageMetaInfo = imageMetaInfo;
        this.source = source;
    }

    public ImageMetaInfo getImageMetaInfo() {
        return imageMetaInfo;
    }

    public Path getSource() {
        return source;
    }
}
