package com.github.tornaia.sorty.metadata;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import com.github.tornaia.sorty.metadata.matcher.GeoLocationMatcher;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;

public class WrappedMetadataTest {

    private String exifImage = "/exif.jpg";
    private String blankImage = "/blank.jpg";

    @Test
    public void wrappedMetadataRead() throws Exception {
        WrappedMetadata wrappedMetadata = getWrappedMetadata(exifImage);
        assertNotNull(wrappedMetadata);
    }

    @Test
    public void gpsRead() throws Exception {
        WrappedMetadata wrappedMetadata = getWrappedMetadata(exifImage);
        Optional<GpsDirectory> gps = wrappedMetadata.getGPS();
        assertTrue(gps.isPresent());
    }

    @Test
    public void gpsNotPresent() throws Exception {
        WrappedMetadata wrappedMetadata = getWrappedMetadata(blankImage);
        Optional<GpsDirectory> gps = wrappedMetadata.getGPS();
        assertFalse(gps.isPresent());
    }

    @Test
    public void geoLocationIsRead() throws Exception {
        WrappedMetadata wrappedMetadata = getWrappedMetadata(exifImage);
        Optional<GpsDirectory> gps = wrappedMetadata.getGPS();
        GpsDirectory gpsDirectory = gps.get();
        GeoLocation geoLocation = gpsDirectory.getGeoLocation();

        assertThat(geoLocation, new GeoLocationMatcher().latitude(47.42111111111111D).longitude(8.558611111111112D));
    }

    @Test
    public void dateIsRead() throws Exception {
        WrappedMetadata wrappedMetadata = getWrappedMetadata(exifImage);
        Optional<GpsDirectory> gps = wrappedMetadata.getGPS();
        GpsDirectory gpsDirectory = gps.get();

        assertEquals(parse("2016-12-24T18:04:07Z"), gpsDirectory.getGpsDate());
    }

    @Test
    public void exifIFD0IsRead() throws Exception {
        WrappedMetadata wrappedMetadata = getWrappedMetadata(exifImage);
        Optional<ExifIFD0Directory> exifIFD0 = wrappedMetadata.getExifIFD0();
        assertTrue(exifIFD0.isPresent());
    }

    @Test
    public void modelIsRead() throws Exception {
        WrappedMetadata wrappedMetadata = getWrappedMetadata(exifImage);
        assertEquals("SM-G935F", wrappedMetadata.getModel().get());
    }

    @Test
    public void exifSubIFD0IsRead() throws Exception {
        WrappedMetadata wrappedMetadata = getWrappedMetadata(exifImage);
        Optional<ExifSubIFDDirectory> exifSubIFD = wrappedMetadata.getExifSubIFD();
        assertTrue(exifSubIFD.isPresent());
    }

    @Test
    public void exifImageWidthIsRead() throws Exception {
        WrappedMetadata wrappedMetadata = getWrappedMetadata(exifImage);
        Optional<Double> exifImageWidth = wrappedMetadata.getWidth();
        assertEquals(4032D, exifImageWidth.get(), 0.0000001D);
    }

    @Test
    public void exifImageHeightIsRead() throws Exception {
        WrappedMetadata wrappedMetadata = getWrappedMetadata(exifImage);
        Optional<Double> exifImageHeight = wrappedMetadata.getHeight();
        assertEquals(3024D, exifImageHeight.get(), 0.0000001D);
    }

    private WrappedMetadata getWrappedMetadata(String classpathFile) throws ImageProcessingException, IOException {
        InputStream resourceAsStream = getClass().getResourceAsStream(classpathFile);
        Metadata metadata = ImageMetadataReader.readMetadata(resourceAsStream);
        return new WrappedMetadata(metadata);
    }

    private static Date parse(String dateTimeAsStr) throws ParseException {
        return new Date(Instant.parse(dateTimeAsStr).toEpochMilli());
    }
}
