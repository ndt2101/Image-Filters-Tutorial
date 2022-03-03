package com.tuan2101.imagefilters.utilities

import android.app.Application
import com.tuan2101.imagefilters.dependencyinjection.repositoryModule
import com.tuan2101.imagefilters.dependencyinjection.viewModelModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by ndt2101 on 3/3/2022.
 */

/**
 * Use this annotation on test classes or test methods that should not be included in a test suite.
 * If the annotation appears on the class then no tests in that class will be included.
 * If the annotation appears only on a test method then only that method will be excluded.
 */
@Suppress("unused")
class AppConfig : Application() { // Khoi tao Koin
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppConfig)
            modules(listOf(repositoryModule, viewModelModelModule)) // add cac module de thuc hien config cac module
        }
    }
}