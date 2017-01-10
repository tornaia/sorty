package com.github.tornaia.sorty.explorer;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class DirectoryExplorer {

    private ImageFileFilter imageFileFilter = new ImageFileFilter();

    public List<Path> getImageFilesRecursively(Path directory) throws IOException {
        List<Path> imageFiles = new ArrayList<>();

        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                File file = path.toFile();
                boolean isImage = imageFileFilter.accept(file);
                if (isImage) {
                    imageFiles.add(path);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path path, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });

        return imageFiles;
    }
}
