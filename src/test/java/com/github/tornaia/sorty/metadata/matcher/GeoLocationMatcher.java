package com.github.tornaia.sorty.metadata.matcher;

import com.drew.lang.GeoLocation;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsAnything;

import static org.hamcrest.CoreMatchers.is;

public class GeoLocationMatcher extends AbstractTypeSafeDiagnosingMatcher<GeoLocation> {

    private Matcher<Double> latitude = new IsAnything<>();

    private Matcher<Double> longitude = new IsAnything<>();

    public GeoLocationMatcher latitude(Double latitude) {
        this.latitude = is(latitude);
        return this;
    }

    public GeoLocationMatcher longitude(Double longitude) {
        this.longitude = is(longitude);
        return this;
    }

    @Override
    protected boolean matchesSafely(GeoLocation item, Description mismatchDescription) {
        return matches(latitude, item.getLatitude(), "latitude: ", mismatchDescription) &&
                matches(longitude, item.getLongitude(), "longitude: ", mismatchDescription);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(GeoLocation.class.getSimpleName())
                .appendText(", latitude: ").appendDescriptionOf(latitude)
                .appendText(", longitude: ").appendDescriptionOf(longitude);
    }
}
