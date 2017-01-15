package com.github.tornaia.sorty.image;

import com.drew.imaging.ImageProcessingException;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;

public class ImageMetaInfoTest {

    private String exifImage = "/exif.jpg";
    private String blankImage = "/blank.jpg";

    @Test
    public void wrappedMetadataRead() throws Exception {
        ImageMetaInfo imageMetaInfo = getWrappedMetadata(exifImage);

        assertNotNull(imageMetaInfo);
    }

    @Test
    public void locationIsRead() throws Exception {
        ImageMetaInfo imageMetaInfo = getWrappedMetadata(exifImage);
        Optional<Location> optionalLocation = imageMetaInfo.getLocation();
        Location location = optionalLocation.get();

        assertThat(location, new LocationMatcher().latitude(47.42111111111111D).longitude(8.558611111111112D));
    }

    @Test
    public void locationIsNotRead() throws Exception {
        ImageMetaInfo imageMetaInfo = getWrappedMetadata(blankImage);
        Optional<Location> optionalLocation = imageMetaInfo.getLocation();

        assertFalse(optionalLocation.isPresent());
    }

    @Test
    public void dateIsRead() throws Exception {
        ImageMetaInfo imageMetaInfo = getWrappedMetadata(exifImage);
        Optional<Date> optionalDate = imageMetaInfo.getDate();

        assertEquals(parse("2016-12-24T18:04:07Z"), optionalDate.get());
    }

    @Test
    public void dateIsNotRead() throws Exception {
        ImageMetaInfo imageMetaInfo = getWrappedMetadata(blankImage);
        Optional<Date> optionalDate = imageMetaInfo.getDate();

        assertFalse(optionalDate.isPresent());
    }

    @Test
    public void modelIsRead() throws Exception {
        ImageMetaInfo imageMetaInfo = getWrappedMetadata(exifImage);
        Optional<String> optionalModel = imageMetaInfo.getModel();

        assertEquals("SM-G935F", optionalModel.get());
    }

    @Test
    public void modelIsNotRead() throws Exception {
        ImageMetaInfo imageMetaInfo = getWrappedMetadata(blankImage);
        Optional<String> optionalModel = imageMetaInfo.getModel();

        assertFalse(optionalModel.isPresent());
    }

    @Test
    public void dimensionIsRead() throws Exception {
        ImageMetaInfo imageMetaInfo = getWrappedMetadata(exifImage);
        Optional<Dimension> optionalDimension = imageMetaInfo.getDimension();

        assertThat(optionalDimension.get(), new DimensionMatcher().width(4032D).height(3024D));
    }

    @Test
    public void dimensionIsNotRead() throws Exception {
        ImageMetaInfo imageMetaInfo = getWrappedMetadata(blankImage);
        Optional<Dimension> optionalDimension = imageMetaInfo.getDimension();

        assertFalse(optionalDimension.isPresent());
    }

    private ImageMetaInfo getWrappedMetadata(String classpathFile) throws ImageProcessingException, IOException {
        InputStream resourceAsStream = getClass().getResourceAsStream(classpathFile);
        return new ImageMetaInfoReader().read(resourceAsStream);
    }

    private static Date parse(String dateTimeAsStr) throws ParseException {
        return new Date(Instant.parse(dateTimeAsStr).toEpochMilli());
    }
}
