package com.github.tornaia.sorty.image;

import com.github.tornaia.sorty.matcher.AbstractTypeSafeDiagnosingMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsAnything;

import java.nio.file.Path;

public class ImageMatcher extends AbstractTypeSafeDiagnosingMatcher<Image> {

    private Matcher<ImageMetaInfo> imageMetaInfo = new IsAnything<>();
    private Matcher<Path> source = new IsAnything<>();

    public ImageMatcher imageMetaInfo(Matcher<ImageMetaInfo> imageMetaInfo) {
        this.imageMetaInfo = imageMetaInfo;
        return this;
    }

    public ImageMatcher source(Matcher<Path> source) {
        this.source = source;
        return this;
    }

    @Override
    protected boolean matchesSafely(Image item, Description mismatchDescription) {
        return matches(imageMetaInfo, item.getImageMetaInfo(), "imageMetaInfo: ", mismatchDescription) &&
                matches(source, item.getSource(), "source: ", mismatchDescription);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(Image.class.getSimpleName())
                .appendText(", imageMetaInfo: ").appendDescriptionOf(imageMetaInfo)
                .appendText(", source: ").appendDescriptionOf(source);
    }
}
