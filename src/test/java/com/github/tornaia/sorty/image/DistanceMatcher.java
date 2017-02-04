package com.github.tornaia.sorty.image;

import com.github.tornaia.sorty.matcher.AbstractTypeSafeDiagnosingMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.*;

public class DistanceMatcher extends AbstractTypeSafeDiagnosingMatcher<Distance> {

    private double amount;

    private double tolerance = 0.0001D;

    public static DistanceMatcher ofMeter(double amount) {
        DistanceMatcher distanceMatcher = new DistanceMatcher();
        distanceMatcher.amount = amount;
        return distanceMatcher;
    }

    public static DistanceMatcher ofKilometer(double amount) {
        return ofMeter(amount * DistanceUnit.KILOMETER.conversionFactor(DistanceUnit.METER));
    }

    public DistanceMatcher tolerance(double tolerance) {
        this.tolerance = tolerance;
        return this;
    }

    @Override
    protected boolean matchesSafely(Distance item, Description mismatchDescription) {
        return matches(getAmountMatcherWithTolerance(), item.getAmountInMeter(), "amount: ", mismatchDescription);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(Distance.class.getSimpleName())
                .appendText(", amount: ").appendDescriptionOf(getAmountMatcherWithTolerance())
                .appendText(", tolerance of " + tolerance);
    }

    private Matcher<Double> getAmountMatcherWithTolerance() {
        double min = amount * (1.0D - tolerance);
        double max = amount * (1.0D + tolerance);
        return both(greaterThanOrEqualTo(min)).and(lessThanOrEqualTo(max));
    }
}
