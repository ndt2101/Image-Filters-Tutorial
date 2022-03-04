package com.tuan2101.imagefilters.repositories

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import java.io.File

/**
 * Created by ndt2101 on 3/4/2022.
 */
class SavedImagesRepositoryImpl(private val context: Context): SavedImagesRepository {

    override suspend fun loadSavedImage(): List<Pair<File, Bitmap>>? {
        val savedImage = ArrayList<Pair<File, Bitmap>>()
        val dir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "Saved Images"
        )
        return dir.listFiles()?.let {
            it.forEach { file ->
                savedImage.add(Pair(file, getPreviewBitmap(file)))
            }
            savedImage
        }
    }

    private fun getPreviewBitmap(file: File): Bitmap {
        val originBitmap = BitmapFactory.decodeFile(file.absolutePath)
        val width = 150
        val height = originBitmap.height * width / originBitmap.width
        return Bitmap.createScaledBitmap(originBitmap, width, height, false)
    }
}