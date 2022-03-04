package com.tuan2101.imagefilters.activities.savedImage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.tuan2101.imagefilters.activities.filteredImage.FilteredImageActivity
import com.tuan2101.imagefilters.adpaters.SavedImageAdapter
import com.tuan2101.imagefilters.callbacks.SavedImageListener
import com.tuan2101.imagefilters.databinding.ActivitySavedImageBinding
import com.tuan2101.imagefilters.utilities.Constants
import com.tuan2101.imagefilters.utilities.disPlayToast
import com.tuan2101.imagefilters.viewmodels.SavedImageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class SavedImageActivity : AppCompatActivity(), SavedImageListener {

    lateinit var binding: ActivitySavedImageBinding
    private val viewModel: SavedImageViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.loadSavedImage()
        onClick()
        setObserve()
    }

    private fun setObserve() {
        viewModel.savedImagesDataState.observe(this) {
            it?.let { savedImagesDataState ->
                binding.progressCircular.visibility =
                    if (savedImagesDataState.isLoading) View.VISIBLE else View.GONE
                savedImagesDataState.savedImages?.let { savedImages ->
                    binding.savedImagesList.adapter = SavedImageAdapter(savedImages, this)
                } ?: run {
                    savedImagesDataState.error?.let { error ->
                        disPlayToast(error)
                    }
                }
            } ?: return@observe
        }
    }

    private fun onClick() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onClickImage(file: File) {
        val fileUri = FileProvider.getUriForFile(
            this,
            "${packageName}.provider",
            file
            )
        Intent(this, FilteredImageActivity::class.java).also {
            it.putExtra(Constants.FILTERED_IMAGE_URI, fileUri)
            startActivity(it)
        }
    }
}