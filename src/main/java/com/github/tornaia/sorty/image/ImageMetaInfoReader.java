package com.github.tornaia.sorty.image;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class ImageMetaInfoReader {

    public ImageMetaInfo read(InputStream inputStream) throws ImageProcessingException, IOException {
        Metadata metadata = ImageMetadataReader.readMetadata(inputStream);

        Optional<Date> date = getDate(metadata);
        Optional<Dimension> dimension = getDimension(metadata);
        Optional<Location> location = getLocation(metadata);
        Optional<String> model = getModel(metadata);

        return new ImageMetaInfo(date, dimension, location, model);
    }

    private Optional<Date> getDate(Metadata metadata) {
        Optional<Date> optionalGpsDate = getGpsDate(metadata);
        if (optionalGpsDate.isPresent()) {
            return optionalGpsDate;
        }

        Optional<Date> optionalExifSubIFDDate = getExifSubIFDDate(metadata);
        if (optionalExifSubIFDDate.isPresent()) {
            return optionalExifSubIFDDate;
        }

        return Optional.empty();
    }

    private Optional<Dimension> getDimension(Metadata metadata) {
        Optional<Double> width = getWidth(metadata);
        if (!width.isPresent()) {
            return Optional.empty();
        }

        Optional<Double> height = getHeight(metadata);
        if (!height.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(new Dimension(width.get(), height.get()));
    }

    private Optional<Location> getLocation(Metadata metadata) {
        Collection<GpsDirectory> gpsDirectories = metadata.getDirectoriesOfType(GpsDirectory.class);
        if (gpsDirectories.isEmpty()) {
            return Optional.empty();
        }

        Optional<GeoLocation> optionalGeoLocation = gpsDirectories.stream()
                .map(gpsDirectory -> gpsDirectory.getGeoLocation())
                .filter(Objects::nonNull)
                .findFirst();

        if (!optionalGeoLocation.isPresent()) {
            return Optional.empty();
        }

        GeoLocation geoLocation = optionalGeoLocation.get();
        return Optional.of(new Location(geoLocation.getLatitude(), geoLocation.getLongitude()));
    }

    private Optional<String> getModel(Metadata metadata) {
        Optional<ExifIFD0Directory> optionalExifIFD0 = getExifIFD0(metadata);
        if (!optionalExifIFD0.isPresent()) {
            return Optional.empty();
        }

        ExifIFD0Directory exifIFD0Directory = optionalExifIFD0.get();
        String model = exifIFD0Directory.getString(ExifIFD0Directory.TAG_MODEL);
        if (model == null) {
            return Optional.empty();
        }

        return Optional.of(model);
    }

    private Optional<Date> getGpsDate(Metadata metadata) {
        Optional<Date> optionalDate = metadata.getDirectoriesOfType(GpsDirectory.class)
                .stream()
                .map(gpsDirectory -> gpsDirectory.getGpsDate())
                .filter(Objects::nonNull)
                .findFirst();

        if (!optionalDate.isPresent()) {
            return Optional.empty();
        }

        Date date = optionalDate.get();
        return Optional.of(date);
    }

    private Optional<Date> getExifSubIFDDate(Metadata metadata) {
        Optional<Date> optionalDate = metadata.getDirectoriesOfType(ExifSubIFDDirectory.class)
                .stream()
                .map(gpsDirectory -> gpsDirectory.getDateOriginal())
                .filter(Objects::nonNull)
                .findFirst();

        if (!optionalDate.isPresent()) {
            return Optional.empty();
        }

        Date date = optionalDate.get();
        return Optional.of(date);
    }

    private Optional<Double> getWidth(Metadata metadata) {
        Optional<ExifSubIFDDirectory> exifSubIFD = getExifSubIFD(metadata);
        if (!exifSubIFD.isPresent()) {
            return Optional.empty();
        }

        ExifSubIFDDirectory exifSubIFDDirectory = exifSubIFD.get();
        Double exifImageWidth = exifSubIFDDirectory.getDoubleObject(ExifSubIFDDirectory.TAG_EXIF_IMAGE_WIDTH);
        if (exifImageWidth == null) {
            return Optional.empty();
        }

        return Optional.of(exifImageWidth);
    }

    private Optional<Double> getHeight(Metadata metadata) {
        Optional<ExifSubIFDDirectory> exifSubIFD = getExifSubIFD(metadata);
        if (!exifSubIFD.isPresent()) {
            return Optional.empty();
        }

        ExifSubIFDDirectory exifSubIFDDirectory = exifSubIFD.get();
        Double exifImageHeight = exifSubIFDDirectory.getDoubleObject(ExifSubIFDDirectory.TAG_EXIF_IMAGE_HEIGHT);
        if (exifImageHeight == null) {
            return Optional.empty();
        }

        return Optional.of(exifImageHeight);
    }

    private Optional<ExifIFD0Directory> getExifIFD0(Metadata metadata) {
        Collection<ExifIFD0Directory> exifIFD0Directories = metadata.getDirectoriesOfType(ExifIFD0Directory.class);
        if (exifIFD0Directories.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(exifIFD0Directories.iterator().next());
    }

    private Optional<ExifSubIFDDirectory> getExifSubIFD(Metadata metadata) {
        Collection<ExifSubIFDDirectory> exifSubIFDDirectoryDirectories = metadata.getDirectoriesOfType(ExifSubIFDDirectory.class);
        if (exifSubIFDDirectoryDirectories.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(exifSubIFDDirectoryDirectories.iterator().next());
    }
}
