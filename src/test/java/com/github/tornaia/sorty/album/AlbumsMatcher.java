package com.github.tornaia.sorty.album;

import com.github.tornaia.sorty.matcher.AbstractTypeSafeDiagnosingMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsAnything;

import static org.hamcrest.CoreMatchers.is;

public class AlbumsMatcher extends AbstractTypeSafeDiagnosingMatcher<Albums> {

    private Matcher<Iterable<Album>> albums = new IsAnything<>();

    private Matcher<Integer> size = new IsAnything<>();

    public AlbumsMatcher albums(Matcher<Iterable<Album>> albums) {
        this.albums = albums;
        return this;
    }

    public AlbumsMatcher size(int size) {
        this.size = is(size);
        return this;
    }

    @Override
    protected boolean matchesSafely(Albums item, Description mismatchDescription) {
        return matches(albums, item.getAlbums(), "albums: ", mismatchDescription) &&
                matches(size, item.getAlbums().size(), "size: ", mismatchDescription);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(Albums.class.getSimpleName())
                .appendText(", albums: ").appendDescriptionOf(albums)
                .appendText(", size: ").appendDescriptionOf(size);
    }
}
