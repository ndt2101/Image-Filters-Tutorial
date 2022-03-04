package com.tuan2101.imagefilters.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tuan2101.imagefilters.repositories.SavedImagesRepository
import com.tuan2101.imagefilters.utilities.Coroutines
import java.io.File

/**
 * Created by ndt2101 on 3/4/2022.
 */
class SavedImageViewModel(private val savedImagesRepository: SavedImagesRepository) : ViewModel() {

    class SavedImagesDataState(
        val isLoading: Boolean,
        val savedImages: List<Pair<File, Bitmap>>?,
        val error: String?
    )

    private val _savedImagesDataState = MutableLiveData<SavedImagesDataState>()
    val savedImagesDataState: LiveData<SavedImagesDataState>
        get() = _savedImagesDataState

    fun loadSavedImage() {
        Coroutines.io {
            runCatching {
                emitSavedImagesDataState(isLoading = true)
                savedImagesRepository.loadSavedImage()
            }.onSuccess { savedImages ->
                emitSavedImagesDataState(savedImages = savedImages)
            }.onFailure { error ->
                emitSavedImagesDataState(error = error.message.toString())
            }
        }
    }

    private fun emitSavedImagesDataState(
        isLoading: Boolean = false,
        savedImages: List<Pair<File, Bitmap>>? = null,
        error: String? = null
    ) {
        _savedImagesDataState.postValue(SavedImagesDataState(isLoading, savedImages, error))
    }
}