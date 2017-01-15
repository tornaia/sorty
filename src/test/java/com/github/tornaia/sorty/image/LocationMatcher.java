package com.github.tornaia.sorty.image;

import com.github.tornaia.sorty.matcher.AbstractTypeSafeDiagnosingMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsAnything;

import static org.hamcrest.CoreMatchers.is;

public class LocationMatcher extends AbstractTypeSafeDiagnosingMatcher<Location> {

    private Matcher<Double> latitude = new IsAnything<>();

    private Matcher<Double> longitude = new IsAnything<>();

    public LocationMatcher latitude(Double latitude) {
        this.latitude = is(latitude);
        return this;
    }

    public LocationMatcher longitude(Double longitude) {
        this.longitude = is(longitude);
        return this;
    }

    @Override
    protected boolean matchesSafely(Location item, Description mismatchDescription) {
        return matches(latitude, item.getLatitude(), "latitude: ", mismatchDescription) &&
                matches(longitude, item.getLongitude(), "longitude: ", mismatchDescription);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(Location.class.getSimpleName())
                .appendText(", latitude: ").appendDescriptionOf(latitude)
                .appendText(", longitude: ").appendDescriptionOf(longitude);
    }
}
