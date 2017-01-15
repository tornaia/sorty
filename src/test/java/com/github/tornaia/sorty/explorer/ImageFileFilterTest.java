package com.github.tornaia.sorty.explorer;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ImageFileFilterTest {

    private ImageFileFilter imageFileFilter = new ImageFileFilter();

    @Test
    public void noExtension() {
        assertFalse(imageFileFilter.accept(new File("file")));
    }

    @Test
    public void jpgWithOneDot() {
        assertTrue(imageFileFilter.accept(new File("20150710_153022.jpg")));
    }

    @Test
    public void jpgWithMultipleDots() {
        assertTrue(imageFileFilter.accept(new File("2016-07-03 16.41.24 248.jpg")));
    }

    @Test
    public void jpegWithOneDot() {
        assertTrue(imageFileFilter.accept(new File("1.jpeg")));
    }

    @Test
    public void jpegWithUpperCase() {
        assertTrue(imageFileFilter.accept(new File("1.JPEG")));
    }

    @Test
    public void txtIsNotImage() {
        assertFalse(imageFileFilter.accept(new File("1.txt")));
    }
}
