package com.tuan2101.imagefilters.utilities

import android.content.Context
import android.widget.Toast

/**
 * Created by ndt2101 on 3/3/2022.
 */

fun Context.disPlayToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}