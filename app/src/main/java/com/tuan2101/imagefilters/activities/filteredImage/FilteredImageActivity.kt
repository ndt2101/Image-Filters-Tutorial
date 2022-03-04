package com.tuan2101.imagefilters.activities.filteredImage

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tuan2101.imagefilters.databinding.ActivityFilteredImageBinding
import com.tuan2101.imagefilters.utilities.Constants

class FilteredImageActivity : AppCompatActivity() {

    lateinit var binding: ActivityFilteredImageBinding
    var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilteredImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        disPlaceImage()
        onClick()
    }

    private fun disPlaceImage() {
        intent.getParcelableExtra<Uri>(Constants.FILTERED_IMAGE_URI)?.let {
            imageUri = it
            binding.image.setImageURI(imageUri)
        }
    }

    private fun onClick() {
        binding.shareButton.setOnClickListener {
            Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_STREAM, imageUri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                type = "image/*"
                startActivity(this)
            }
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }
}