package com.github.tornaia.sorty.explorer;

import com.drew.imaging.ImageProcessingException;
import com.github.tornaia.sorty.image.Images;
import com.github.tornaia.sorty.exception.SortingCannotBeCompletedException;
import com.github.tornaia.sorty.image.Image;
import com.github.tornaia.sorty.image.ImageMetaInfo;
import com.github.tornaia.sorty.image.ImageMetaInfoReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class DirectoryExplorer {

    private ImageFileFilter imageFileFilter = new ImageFileFilter();

    public Images getImages(Path directory) {
        Images images = new Images();

        try {
            Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                    File file = path.toFile();
                    boolean isImage = imageFileFilter.accept(file);
                    if (isImage) {
                        Image image;
                        try {
                            image = convertToImage(file);
                        } catch (ImageProcessingException e) {
                            System.out.println("Cannot read metadata from file: " + e);
                            return FileVisitResult.CONTINUE;
                        }
                        images.add(image);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path path, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new SortingCannotBeCompletedException("Cannot sort images", e);
        }

        return images;
    }

    private Image convertToImage(File file) throws IOException, ImageProcessingException {
        ImageMetaInfo imageMetaInfo;
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            imageMetaInfo = new ImageMetaInfoReader().read(fileInputStream);
        }
        return new Image(imageMetaInfo, file.toPath());
    }
}
