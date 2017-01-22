package com.github.tornaia.sorty.algorithm;

import com.github.tornaia.sorty.album.Album;
import com.github.tornaia.sorty.album.Albums;
import com.github.tornaia.sorty.image.Images;
import com.github.tornaia.sorty.image.Image;
import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class GroupingBasedOnDate implements SortingStrategy {

    @Override
    public Albums sort(Images images) {
        Map<Date, Set<Image>> imagesPerDates = new HashMap<>();
        Consumer<Image> getDates = image -> {
            Optional<Date> optionalDate = image.getImageMetaInfo().getDate();
            if (optionalDate.isPresent()) {
                Date date = optionalDate.get();
                Date truncatedDate = DateUtils.truncate(date, Calendar.DATE);
                boolean newDate = imagesPerDates.get(truncatedDate) == null;
                if (newDate) {
                    imagesPerDates.put(truncatedDate, new HashSet<>());
                }
                Set<Image> imagesOfDate = imagesPerDates.get(truncatedDate);
                imagesOfDate.add(image);
            }
        };

        images.apply(getDates);

        List<Album> albums = imagesPerDates
                .entrySet()
                .stream()
                .map(entry -> new Album(new SimpleDateFormat("yyyy-MM-dd").format(entry.getKey()), entry.getValue()))
                .collect(Collectors.toList());

        return new Albums(albums);
    }
}
