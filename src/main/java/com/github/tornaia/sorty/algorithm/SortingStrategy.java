package com.github.tornaia.sorty.algorithm;

import com.github.tornaia.sorty.album.Albums;
import com.github.tornaia.sorty.image.Images;

public interface SortingStrategy {

    Albums sort(Images images);
}
