package com.github.tornaia.sorty.metadata.reader;

import com.drew.imaging.ImageProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class WrapperMetadataReaderTest {

    private String sampleFile = "/exif.test.image.jpg";

    @InjectMocks
    private MetadataReader metadataReader;

    @Test
    public void imageIsRead() throws ImageProcessingException, IOException {
        InputStream resourceAsStream = getClass().getResourceAsStream(sampleFile);
        assertNotNull(metadataReader.read(resourceAsStream));
    }
}
