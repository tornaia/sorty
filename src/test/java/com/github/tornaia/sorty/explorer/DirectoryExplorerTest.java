package com.github.tornaia.sorty.explorer;

import com.github.tornaia.sorty.algorithm.Images;
import com.github.tornaia.sorty.image.Image;
import com.github.tornaia.sorty.image.ImageMatcher;
import com.github.tornaia.sorty.image.ImageMetaInfoMatcher;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DirectoryExplorerTest {

    @InjectMocks
    private DirectoryExplorer directoryExplorer;

    private Path directory;

    @Before
    public void initDirectory() throws Exception {
        directory = Files.createTempDirectory("junit");
    }

    @After
    public void deleteDirectory() throws Exception {
        FileUtils.forceDelete(directory.toFile());
    }

    @Test
    public void nonExistingDirectory() throws Exception {
        Images images = directoryExplorer.getImages(new File("non-existing-directory").toPath());
        Consumer<Image> counter = mock(Consumer.class);

        images.apply(counter);

        verifyZeroInteractions(counter);
    }

    @Test
    public void emptyDirectory() throws Exception {
        Images images = directoryExplorer.getImages(directory);
        Consumer<Image> counter = mock(Consumer.class);

        images.apply(counter);

        verifyZeroInteractions(counter);
    }

    @Test
    public void withOneNonImageFile() throws Exception {
        Files.createFile(directory.resolve("sample.txt"));
        Images images = directoryExplorer.getImages(directory);
        Consumer<Image> counter = mock(Consumer.class);

        images.apply(counter);

        verifyZeroInteractions(counter);
    }

    @Test
    public void withOneImageFile() throws Exception {
        InputStream imageInputStream = getClass().getResourceAsStream("/exif.jpg");
        Files.copy(imageInputStream, directory.resolve("exif.jpg"));
        Images images = directoryExplorer.getImages(directory);

        Consumer mockedConsumer = Mockito.mock(Consumer.class);
        images.apply(mockedConsumer);

        ArgumentCaptor<Image> argument = ArgumentCaptor.forClass(Image.class);

        verify(mockedConsumer).accept(argument.capture());
        assertThat(argument.getValue(), new ImageMatcher()
                .imageMetaInfo(new ImageMetaInfoMatcher()
                        .date("2016-12-24 19:04:07")
                        .dimension("4032x3024")
                        .location("47.42111111111111,8.558611111111112")
                        .model("SM-G935F")));

        verifyNoMoreInteractions(mockedConsumer);
    }
}
