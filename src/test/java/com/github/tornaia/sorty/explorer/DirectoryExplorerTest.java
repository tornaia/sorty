package com.github.tornaia.sorty.explorer;

import com.github.tornaia.sorty.explorer.matcher.PathMatcher;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class DirectoryExplorerTest {

    @InjectMocks
    private DirectoryExplorer directoryExplorer;

    private Path directory;
    private String directoryAsAbsolutePath;

    @Before
    public void initDirectory() throws Exception {
        directory = Files.createTempDirectory("junit");
        directoryAsAbsolutePath = directory.toAbsolutePath().toString();
    }

    @After
    public void deleteDirectory() throws Exception {
        FileUtils.forceDelete(directory.toFile());
    }

    @Test
    public void nonExistingDirectory() throws Exception {
        List<Path> images = directoryExplorer.getImageFilesRecursively(new File("non-existing-directory").toPath());
        assertTrue(images.isEmpty());
    }

    @Test
    public void emptyDirectory() throws Exception {
        List<Path> images = directoryExplorer.getImageFilesRecursively(directory);
        assertTrue(images.isEmpty());
    }

    @Test
    public void withOneImageFile() throws Exception {
        Files.createFile(directory.resolve("sample.jpg"));
        List<Path> images = directoryExplorer.getImageFilesRecursively(directory);
        assertThat(images, hasItems(new PathMatcher(directoryAsAbsolutePath, "sample.jpg")));
    }

    @Test
    public void withOneNonImageFile() throws Exception {
        Files.createFile(directory.resolve("sample.txt"));
        List<Path> images = directoryExplorer.getImageFilesRecursively(directory);
        assertTrue(images.isEmpty());
    }
}
