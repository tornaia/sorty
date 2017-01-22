package com.github.tornaia.sorty.matcher;

import org.hamcrest.Description;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsAnything;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;

public class OptionalMatcher<T> extends AbstractTypeSafeDiagnosingMatcher<Optional<T>> {

    private Matcher<Optional<T>> value = new IsAnything<>();

    private OptionalMatcher() {
    }

    public static <T> OptionalMatcher<T> anything() {
        return new OptionalMatcher<>();
    }

    public static <T> OptionalMatcher<T> empty() {
        OptionalMatcher<T> optionalMatcher = new OptionalMatcher<>();
        optionalMatcher.value = new FeatureMatcher<Optional<T>, Boolean>(is(false), "value", "value") {
            @Override
            protected Boolean featureValueOf(Optional<T> actual) {
                return actual.isPresent();
            }
        };
        return optionalMatcher;
    }

    public static <T> OptionalMatcher<T> value(Matcher<T> valueMatcher) {
        OptionalMatcher<T> optionalMatcher = new OptionalMatcher<>();
        optionalMatcher.value = new FeatureMatcher<Optional<T>, T>(valueMatcher, "value", "value") {
            @Override
            protected T featureValueOf(Optional<T> actual) {
                return actual.get();
            }
        };
        return optionalMatcher;
    }

    public static <T> OptionalMatcher<T> value(T value) {
        OptionalMatcher<T> optionalMatcher = new OptionalMatcher<>();
        optionalMatcher.value = new FeatureMatcher<Optional<T>, T>(is(value), "value", "value") {
            @Override
            protected T featureValueOf(Optional<T> actual) {
                return actual.get();
            }
        };
        return optionalMatcher;
    }

    @Override
    protected boolean matchesSafely(Optional<T> item, Description mismatchDescription) {
        return matches(value, item, "optional: ", mismatchDescription);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(Optional.class.getSimpleName())
                .appendText(", value: ").appendDescriptionOf(value);
    }
}
