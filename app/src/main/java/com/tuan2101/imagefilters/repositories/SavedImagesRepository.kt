package com.tuan2101.imagefilters.repositories

import android.graphics.Bitmap
import java.io.File

/**
 * Created by ndt2101 on 3/4/2022.
 */
interface SavedImagesRepository {
    suspend fun loadSavedImage(): List<Pair<File, Bitmap>>?
}