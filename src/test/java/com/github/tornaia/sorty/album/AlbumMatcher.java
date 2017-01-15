package com.github.tornaia.sorty.album;

import com.github.tornaia.sorty.image.Image;
import com.github.tornaia.sorty.matcher.AbstractTypeSafeDiagnosingMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsAnything;

import static org.hamcrest.CoreMatchers.is;

public class AlbumMatcher extends AbstractTypeSafeDiagnosingMatcher<Album> {

    private Matcher<String> name = new IsAnything<>();

    private Matcher<Iterable<? extends Image>> images = new IsAnything<>();

    private Matcher<Integer> size = new IsAnything<>();

    public AlbumMatcher name(String name) {
        this.name = is(name);
        return this;
    }

    public AlbumMatcher images(Matcher<Iterable<? extends Image>> images) {
        this.images = images;
        return this;
    }

    public AlbumMatcher size(int size) {
        this.size = is(size);
        return this;
    }

    @Override
    protected boolean matchesSafely(Album item, Description mismatchDescription) {
        return matches(name, item.getName(), "name: ", mismatchDescription) &&
                matches(images, item.getImages(), "images: ", mismatchDescription) &&
                matches(size, item.getImages().size(), "size: ", mismatchDescription);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(Albums.class.getSimpleName())
                .appendText(", name: ").appendDescriptionOf(name)
                .appendText(", images: ").appendDescriptionOf(images)
                .appendText(", size: ").appendDescriptionOf(size);
    }
}
