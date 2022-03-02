package com.tuan2101.imagefilters

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.tuan2101.imagefilters.databinding.ActivityEditingNewImageBinding

class EditingNewImageActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditingNewImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditingNewImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListener()
        displayImage()
    }

    private fun displayImage() {
        val imageUri = intent.getParcelableExtra<Uri>(Constants.IMAGE_URI)
        imageUri?.let {
            val inputStream = contentResolver.openInputStream(it)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            binding.preview.apply {
                setImageBitmap(bitmap)
                visibility = View.VISIBLE
            }
        }
    }

    private fun setListener() {
        binding.backButton.setOnClickListener {
            this.onBackPressed()
        }
    }
}