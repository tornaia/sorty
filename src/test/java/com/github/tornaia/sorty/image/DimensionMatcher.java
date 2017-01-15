package com.github.tornaia.sorty.image;

import com.github.tornaia.sorty.test.AbstractTypeSafeDiagnosingMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsAnything;

import static org.hamcrest.CoreMatchers.is;

public class DimensionMatcher extends AbstractTypeSafeDiagnosingMatcher<Dimension> {

    private Matcher<Double> width = new IsAnything<>();

    private Matcher<Double> height = new IsAnything<>();

    public DimensionMatcher width(Double width) {
        this.width = is(width);
        return this;
    }

    public DimensionMatcher height(Double height) {
        this.height = is(height);
        return this;
    }

    @Override
    protected boolean matchesSafely(Dimension item, Description mismatchDescription) {
        return matches(width, item.getWidth(), "width: ", mismatchDescription) &&
                matches(height, item.getHeight(), "height: ", mismatchDescription);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(Dimension.class.getSimpleName())
                .appendText(", width: ").appendDescriptionOf(width)
                .appendText(", height: ").appendDescriptionOf(height);
    }
}
