package com.github.tornaia.sorty.metadata.reader;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.github.tornaia.sorty.metadata.WrappedMetadata;

import java.io.IOException;
import java.io.InputStream;

public class MetadataReader {

    public WrappedMetadata read(InputStream inputStream) throws ImageProcessingException, IOException {
        Metadata metadata = ImageMetadataReader.readMetadata(inputStream);
        return new WrappedMetadata(metadata);
    }
}
