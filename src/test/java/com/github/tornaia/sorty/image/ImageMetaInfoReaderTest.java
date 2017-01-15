package com.github.tornaia.sorty.image;

import com.github.tornaia.sorty.image.ImageMetaInfoReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.InputStream;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ImageMetaInfoReaderTest {

    @InjectMocks
    private ImageMetaInfoReader imageMetaInfoReader;

    @Test
    public void exifImageIsRead() throws Exception {
        InputStream resourceAsStream = getClass().getResourceAsStream("/exif.jpg");
        assertNotNull(imageMetaInfoReader.read(resourceAsStream));
    }

    @Test
    public void blankImageIsRead() throws Exception {
        InputStream resourceAsStream = getClass().getResourceAsStream("/blank.jpg");
        assertNotNull(imageMetaInfoReader.read(resourceAsStream));
    }
}
