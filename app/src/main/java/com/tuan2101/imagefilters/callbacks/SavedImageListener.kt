package com.tuan2101.imagefilters.callbacks

import java.io.File

/**
 * Created by ndt2101 on 3/5/2022.
 */
interface SavedImageListener {
    fun onClickImage(file: File)
}