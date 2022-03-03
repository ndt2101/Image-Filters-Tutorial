package com.tuan2101.imagefilters.utilities

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by ndt2101 on 3/2/2022.
 */
object Coroutines {

    /** run function work in background
     * param work: (higher order function) is suspend function without input and output
     */
    fun io(work: suspend (() -> Unit)) {
        CoroutineScope(Dispatchers.IO).launch { work() }
    }
}