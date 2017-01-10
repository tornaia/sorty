package com.github.tornaia.sorty.explorer;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageFileFilter implements FileFilter {

    private static final Pattern IMAGE_PATTERN = Pattern.compile("([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)");

    @Override
    public boolean accept(File file) {
        String fileName = file.getName();
        Matcher matcher = IMAGE_PATTERN.matcher(fileName);
        return matcher.matches();
    }
}
