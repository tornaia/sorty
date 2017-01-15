package com.github.tornaia.sorty.image;

import java.util.Date;
import java.util.Optional;

public class ImageMetaInfo {

    private Optional<Date> date;
    private Optional<Dimension> dimension;
    private Optional<Location> location;
    private Optional<String> model;

    public ImageMetaInfo(Optional<Date> date, Optional<Dimension> dimension, Optional<Location> location, Optional<String> model) {
        this.date = date;
        this.dimension = dimension;
        this.location = location;
        this.model = model;
    }

    public Optional<Date> getDate() {
        return date;
    }

    public Optional<Dimension> getDimension() {
        return dimension;
    }

    public Optional<Location> getLocation() {
        return location;
    }

    public Optional<String> getModel() {
        return model;
    }
}
