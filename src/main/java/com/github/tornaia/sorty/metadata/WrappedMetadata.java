package com.github.tornaia.sorty.metadata;

import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;

import java.util.Collection;
import java.util.Optional;

public class WrappedMetadata {

    private final Metadata metadata;

    public WrappedMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public Optional<GpsDirectory> getGPS() {
        Collection<GpsDirectory> gpsDirectories = metadata.getDirectoriesOfType(GpsDirectory.class);
        if (gpsDirectories.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(gpsDirectories.iterator().next());
    }

    public Optional<ExifIFD0Directory> getExifIFD0() {
        Collection<ExifIFD0Directory> exifIFD0Directories = metadata.getDirectoriesOfType(ExifIFD0Directory.class);
        if (exifIFD0Directories.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(exifIFD0Directories.iterator().next());
    }

    public Optional<ExifSubIFDDirectory> getExifSubIFD() {
        Collection<ExifSubIFDDirectory> exifSubIFDDirectoryDirectories = metadata.getDirectoriesOfType(ExifSubIFDDirectory.class);
        if (exifSubIFDDirectoryDirectories.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(exifSubIFDDirectoryDirectories.iterator().next());
    }

    public Optional<Double> getWidth() {
        Optional<ExifSubIFDDirectory> exifSubIFD = getExifSubIFD();
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

    public Optional<Double> getHeight() {
        Optional<ExifSubIFDDirectory> exifSubIFD = getExifSubIFD();
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

    public Optional<String> getModel() {
        Optional<ExifIFD0Directory> optionalExifIFD0 = getExifIFD0();
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
}
