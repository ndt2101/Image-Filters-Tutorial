package com.tuan2101.imagefilters.activities.editimage

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.tuan2101.imagefilters.adpaters.ImageFilterAdapter
import com.tuan2101.imagefilters.callbacks.ImageFilterListener
import com.tuan2101.imagefilters.data.ImageFilter
import com.tuan2101.imagefilters.databinding.ActivityEditingNewImageBinding
import com.tuan2101.imagefilters.utilities.Constants
import com.tuan2101.imagefilters.utilities.disPlayToast
import com.tuan2101.imagefilters.viewmodels.EditImageViewModel
import jp.co.cyberagent.android.gpuimage.GPUImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditingNewImageActivity : AppCompatActivity(), ImageFilterListener {

    lateinit var binding: ActivityEditingNewImageBinding
    private val viewModel: EditImageViewModel by viewModel()
    lateinit var gpuImage: GPUImage

    private lateinit var originalBitmap: Bitmap
    private val filteredBitmap = MutableLiveData<Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditingNewImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListener()
        setObserve()
        prepareImagePreview()
    }

    private fun setObserve() {
        viewModel.imagePreviewDataState.observe(this) {
            it?.let {
                binding.previewProgressCircular.visibility = if (it.isLoading) View.VISIBLE else View.GONE
                it.bitmap?.let { bitmap ->
                    originalBitmap = bitmap
                    filteredBitmap.value = originalBitmap
                    gpuImage.setImage(originalBitmap)

                    viewModel.loadImageFilters(bitmap) // load filters
                    binding.preview.visibility = View.VISIBLE
                } ?: kotlin.run {
                    it.error?.let { message ->
                        disPlayToast(message)
                    }
                }
            } ?: return@observe
        }

        viewModel.imageFilterDataState.observe(this) {
            it?.let {
                binding.filterProgressCircular.visibility = if (it.isLoading) View.VISIBLE else View.GONE
                it.imageFilters?.let{ imageFilterList ->
                    binding.filterList.adapter = ImageFilterAdapter(imageFilterList, this)
                } ?: kotlin.run {
                    it.error?.let { message ->
                        disPlayToast(message)                    }
                }
            }?: return@observe
        }

        filteredBitmap.observe(this) {
            binding.preview.setImageBitmap(it)
        }
    }

    private fun prepareImagePreview() {
        val imageUri = intent.getParcelableExtra<Uri>(Constants.IMAGE_URI)
        imageUri?.let {
            gpuImage = GPUImage(this)
            viewModel.prepareImagePreview(it)
        }
    }

    private fun setListener() {
        binding.backButton.setOnClickListener {
            this.onBackPressed()
        }

        // region:: For difference between origin and filtered image
        binding.preview.setOnLongClickListener {
            binding.preview.setImageBitmap(originalBitmap)
            false
        }

        binding.preview.setOnClickListener {
            binding.preview.setImageBitmap(filteredBitmap.value)
        }
        //endregion
    }

    override fun onFilterSelected(imageFilter: ImageFilter) {
        gpuImage.setFilter(imageFilter.filter)
        filteredBitmap.value = gpuImage.bitmapWithFilterApplied
//        with(gpuImage) {
//            with(imageFilter) {
//                setFilter(filter)
//                filteredBitmap.value = bitmapWithFilterApplied
//            }
//        }
    }
}