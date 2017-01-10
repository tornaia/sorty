package com.github.tornaia.sorty.explorer.matcher;

import com.github.tornaia.sorty.metadata.matcher.AbstractTypeSafeDiagnosingMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.io.File;
import java.nio.file.Path;

import static org.hamcrest.CoreMatchers.is;

public class PathMatcher extends AbstractTypeSafeDiagnosingMatcher<Path> {

    private final Matcher<Path> path;

    public PathMatcher(String rootDirectory, String relativePath) {
        this.path = is(new File(rootDirectory).toPath().resolve(relativePath));
    }

    @Override
    protected boolean matchesSafely(Path item, Description mismatchDescription) {
        return matches(path, item, "path: ", mismatchDescription);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(Path.class.getSimpleName())
                .appendText(", path: ").appendDescriptionOf(path);
    }
}
