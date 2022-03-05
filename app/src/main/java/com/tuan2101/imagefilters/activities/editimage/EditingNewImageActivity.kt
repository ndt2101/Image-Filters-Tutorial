package com.tuan2101.imagefilters.activities.editimage

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.tuan2101.imagefilters.activities.filteredImage.FilteredImageActivity
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
                    gpuImage.setImage(originalBitmap) // set origin bitmap cho gpuImage

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

        viewModel.saveFilteredImageDataState.observe(this) {
            it?.let {
                binding.saveProgressCircular.visibility = if (it.isLoading) View.VISIBLE else View.GONE
                it.uri?.let { uri->
                    Intent(this, FilteredImageActivity::class.java).apply {
                        putExtra(Constants.FILTERED_IMAGE_URI, uri)
                        startActivity(this)
                    }
                } ?: run {
                    it.error?.let {  message ->
                        disPlayToast(message)
                    }
                }
            } ?: return@observe
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

        binding.saveButton.setOnClickListener {
            filteredBitmap.value?.let { bitmap ->
                viewModel.saveFilteredImage(bitmap)
            }
        }

    }

    // set  filter cho gpuImage sau do tao bitmap voi filter va original bitmap duoc add ow tren
    override fun onFilterSelected(imageFilter: ImageFilter) {
        gpuImage.setFilter(imageFilter.filter)
        filteredBitmap.value = gpuImage.bitmapWithFilterApplied // tao bitmap voi filter duoc chon
//        with(gpuImage) {
//            with(imageFilter) {
//                setFilter(filter)
//                filteredBitmap.value = bitmapWithFilterApplied
//            }
//        }
    }
}