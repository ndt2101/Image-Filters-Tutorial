package com.tuan2101.imagefilters.callbacks

import com.tuan2101.imagefilters.data.ImageFilter

/**
 * Created by ndt2101 on 3/3/2022.
 */
interface ImageFilterListener {
    fun onFilterSelected(imageFilter: ImageFilter)
}