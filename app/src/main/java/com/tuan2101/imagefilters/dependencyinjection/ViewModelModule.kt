package com.tuan2101.imagefilters.dependencyinjection

import com.tuan2101.imagefilters.viewmodels.EditImageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by ndt2101 on 3/3/2022.
 */

// Cung cấp instance EditImageViewModel
val viewModelModelModule = module {
    viewModel { EditImageViewModel(editImageRepository = get()) } // get duoc do da tao instance editImageRepository trong RepositoryModule
}