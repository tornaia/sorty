package com.github.tornaia.sorty.image;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class ImageBuilder {

    private Optional<Date> date = Optional.empty();
    private Optional<Dimension> dimension = Optional.empty();
    private Optional<Location> location = Optional.empty();
    private Optional<String> model = Optional.empty();

    public ImageBuilder date(String dateStr) {
        try {
            this.date = Optional.of(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public ImageBuilder dimension(String dimensionStr) {
        this.dimension = Optional.of(new Dimension(Double.parseDouble(dimensionStr.split("x")[0]), Double.parseDouble(dimensionStr.split("x")[1])));
        return this;
    }

    public ImageBuilder location(String locationStr) {
        this.location = getLocation(locationStr);
        return this;
    }

    public ImageBuilder model(String model) {
        this.model = Optional.of(model);
        return this;
    }

    public Image create() {
        ImageMetaInfo imageMetaInfo = new ImageMetaInfo(date, dimension, location, model);
        return new Image(imageMetaInfo, null);
    }

    private Optional<Location> getLocation(String locationStr) {
        if ("Siófok".equals(locationStr)) {
            return Optional.of(new Location(46.90413D, 18.058D));
        }
        return Optional.empty();
    }
}
