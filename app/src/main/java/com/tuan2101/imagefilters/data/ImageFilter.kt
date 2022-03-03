package com.tuan2101.imagefilters.data

import android.graphics.Bitmap
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter

/**
 * Created by ndt2101 on 3/3/2022.
 */
data class ImageFilter(
    val name: String,
    val filter: GPUImageFilter,
    val filterPreview: Bitmap
)
