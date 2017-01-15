package com.github.tornaia.sorty.image;

import java.nio.file.Path;

public class Image {

    private final ImageMetaInfo metadata;
    private final Path source;

    public Image(ImageMetaInfo metadata, Path source) {
        this.metadata = metadata;
        this.source = source;
    }

    public ImageMetaInfo getMetadata() {
        return metadata;
    }

    public Path getSource() {
        return source;
    }
}
