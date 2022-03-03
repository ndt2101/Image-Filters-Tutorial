package com.tuan2101.imagefilters.repositories

import android.graphics.Bitmap
import android.net.Uri
import com.tuan2101.imagefilters.data.ImageFilter

/**
 * Created by ndt2101 on 3/2/2022.
 */
interface EditImageRepository {
    suspend fun prepareImagePreview(imageUri: Uri): Bitmap?
    suspend fun getImageFilters(image: Bitmap): List<ImageFilter>
}