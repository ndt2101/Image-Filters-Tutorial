package com.tuan2101.imagefilters.viewmodels

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tuan2101.imagefilters.data.ImageFilter
import com.tuan2101.imagefilters.repositories.EditImageRepository
import com.tuan2101.imagefilters.utilities.Coroutines
import java.lang.Exception

/**
 * Created by ndt2101 on 3/2/2022.
 */
class EditImageViewModel(private val editImageRepository: EditImageRepository) : ViewModel() {

    // region :: prepare image preview
    private val _imagePreviewDataState = MutableLiveData<ImagePreviewDataState>()
    val imagePreviewDataState: LiveData<ImagePreviewDataState>
        get() = _imagePreviewDataState

    fun prepareImagePreview(imageUri: Uri) {
        Coroutines.io {
            // try catch cuc bo
            runCatching {
                emitImagePreviewDataState(isLoading = true)
                Log.i("loadingChecking", "loading image")
                editImageRepository.prepareImagePreview(imageUri) // kết quả thực hiện của runCatching
            }.onSuccess {
                // khi khong co exception trong block tren, ket qua cua block tren se duoc truyen xuong block nay
                it?.let {
                    emitImagePreviewDataState(bitmap = it)
                    Log.i("loadingChecking", "done")
                } ?: kotlin.run {
                    emitImagePreviewDataState(error = "Unable to prepare image preview")
                }
            }.onFailure {
                // chay vao block nay neu runCatching gap exception
                emitImagePreviewDataState(error = it.message.toString())
            }
        }
    }

    private fun emitImagePreviewDataState(
        isLoading: Boolean = false,
        bitmap: Bitmap? = null,
        error: String? = null
    ) {
        _imagePreviewDataState.postValue(
            ImagePreviewDataState(
                isLoading,
                bitmap,
                error
            )
        ) // postValue có thể set giá trị khi ở background
    }

    data class ImagePreviewDataState(
        val isLoading: Boolean,
        val bitmap: Bitmap?,
        val error: String?
    )
    // endregion

    // region:: Load Image Filter
    private val _imageFilterDataState = MutableLiveData<ImageFilterDataState>()
    val imageFilterDataState: LiveData<ImageFilterDataState>
        get() = _imageFilterDataState

    private fun emitImageFilterDataState(
        isLoading: Boolean = false,
        imageFilters: List<ImageFilter>? = null,
        error: String? = null
    ) {
        _imageFilterDataState.postValue(ImageFilterDataState(isLoading, imageFilters, error))
    }

    /** resize bitmap
     * param originBitmap: original bitmap
     */
    private fun getPreviewImage(originBitmap: Bitmap): Bitmap {
        return try {
            val appliedPreviewImageWidth = 150
            val appliedPreviewImageHeight = appliedPreviewImageWidth * originBitmap.height / originBitmap.width
            Bitmap.createScaledBitmap(originBitmap, appliedPreviewImageWidth, appliedPreviewImageHeight, false)
        } catch (e: Exception) {
            originBitmap
        }
    }

    // hoat dong giong ham prepareImagePreview
    fun loadImageFilters(image: Bitmap) {
        Coroutines.io {
            runCatching {
                emitImageFilterDataState(isLoading = true)
                // ap dung filter voi anh da duoc resize
                editImageRepository.getImageFilters(getPreviewImage(image))
            }.onSuccess {
                emitImageFilterDataState(imageFilters = it)
            }.onFailure {
                emitImageFilterDataState(error = it.message.toString())
            }
        }
    }

    data class ImageFilterDataState(
        val isLoading: Boolean,
        val imageFilters: List<ImageFilter>?,
        val error: String?
    )
    //endregion
}