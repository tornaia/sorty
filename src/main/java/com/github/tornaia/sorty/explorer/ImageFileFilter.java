package com.github.tornaia.sorty.explorer;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ImageFileFilter implements FileFilter {

    private static final Set<String> IMAGE_EXTENSIONS = new HashSet<String>(Arrays.asList(new String[]{"jpg", "jpeg"}));

    @Override
    public boolean accept(File file) {
        String fileName = file.getName();
        int lastIndexOfDot = fileName.lastIndexOf('.');
        boolean hasExtension = lastIndexOfDot != -1;
        if (!hasExtension) {
            return false;
        }
        String extension = fileName.substring(lastIndexOfDot + 1).toLowerCase();
        return IMAGE_EXTENSIONS.contains(extension);
    }
}
