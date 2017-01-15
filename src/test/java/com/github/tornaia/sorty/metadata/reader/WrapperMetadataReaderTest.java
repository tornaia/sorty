package com.github.tornaia.sorty.metadata.reader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.InputStream;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class WrapperMetadataReaderTest {

    @InjectMocks
    private MetadataReader metadataReader;

    @Test
    public void exifImageIsRead() throws Exception {
        InputStream resourceAsStream = getClass().getResourceAsStream("/exif.jpg");
        assertNotNull(metadataReader.read(resourceAsStream));
    }

    @Test
    public void blankImageIsRead() throws Exception {
        InputStream resourceAsStream = getClass().getResourceAsStream("/blank.jpg");
        assertNotNull(metadataReader.read(resourceAsStream));
    }
}
