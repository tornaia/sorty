package com.github.tornaia.sorty.image;

import com.github.tornaia.sorty.matcher.AbstractTypeSafeDiagnosingMatcher;
import com.github.tornaia.sorty.matcher.OptionalMatcher;
import org.hamcrest.Description;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageMetaInfoMatcher extends AbstractTypeSafeDiagnosingMatcher<ImageMetaInfo> {

    private OptionalMatcher<Date> date = OptionalMatcher.anything();
    private OptionalMatcher<Dimension> dimension = OptionalMatcher.anything();
    private OptionalMatcher<Location> location = OptionalMatcher.anything();
    private OptionalMatcher<String> model = OptionalMatcher.anything();

    public ImageMetaInfoMatcher date(String dateStr) {
        try {
            date = OptionalMatcher.value(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public ImageMetaInfoMatcher unknownDate() {
        this.date = OptionalMatcher.empty();
        return this;
    }

    public ImageMetaInfoMatcher dimension(String dimensionStr) {
        double width = Double.parseDouble(dimensionStr.split("x")[0]);
        double height = Double.parseDouble(dimensionStr.split("x")[1]);
        dimension = OptionalMatcher.value(new DimensionMatcher().width(width).height(height));
        return this;
    }

    public ImageMetaInfoMatcher unknownDimension() {
        this.dimension = OptionalMatcher.empty();
        return this;
    }

    public ImageMetaInfoMatcher location(String locationStr) {
        double latitude = Double.parseDouble(locationStr.split(",")[0]);
        double longitude = Double.parseDouble(locationStr.split(",")[1]);
        location = OptionalMatcher.value(new LocationMatcher().latitude(latitude).longitude(longitude));
        return this;
    }

    public ImageMetaInfoMatcher unknownLocation() {
        this.location = OptionalMatcher.empty();
        return this;
    }

    public ImageMetaInfoMatcher model(String model) {
        this.model = OptionalMatcher.value(model);
        return this;
    }

    public ImageMetaInfoMatcher unknownModel() {
        this.model = OptionalMatcher.empty();
        return this;
    }

    @Override
    protected boolean matchesSafely(ImageMetaInfo item, Description mismatchDescription) {
        return matches(date, item.getDate(), "date: ", mismatchDescription) &&
                matches(dimension, item.getDimension(), "dimension: ", mismatchDescription) &&
                matches(location, item.getLocation(), "location: ", mismatchDescription) &&
                matches(model, item.getModel(), "model: ", mismatchDescription);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(ImageMetaInfo.class.getSimpleName())
                .appendText(", date: ").appendDescriptionOf(date)
                .appendText(", dimension: ").appendDescriptionOf(dimension)
                .appendText(", location: ").appendDescriptionOf(location)
                .appendText(", model: ").appendDescriptionOf(model);
    }
}
