package com.tuan2101.imagefilters.dependencyinjection

import com.tuan2101.imagefilters.repositories.EditImageRepository
import com.tuan2101.imagefilters.repositories.EditImageRepositoryImpl
import com.tuan2101.imagefilters.repositories.SavedImagesRepository
import com.tuan2101.imagefilters.repositories.SavedImagesRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Created by ndt2101 on 3/3/2022.
 */

// cung cấp một instance EditImageRepositoryImpl mới
val repositoryModule = module {
    factory<EditImageRepository> { EditImageRepositoryImpl(androidContext()) }
    factory<SavedImagesRepository> { SavedImagesRepositoryImpl(androidContext()) }
}