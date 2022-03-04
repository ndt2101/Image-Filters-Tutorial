package com.tuan2101.imagefilters.adpaters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuan2101.imagefilters.callbacks.SavedImageListener
import com.tuan2101.imagefilters.databinding.SavedImageItemBinding
import java.io.File

/**
 * Created by ndt2101 on 3/5/2022.
 */
class SavedImageAdapter(
    val savedImages: List<Pair<File, Bitmap>>,
    val onClick: SavedImageListener
) : RecyclerView.Adapter<SavedImageAdapter.SavedImageViewHolder>() {

    override fun getItemCount() = savedImages.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedImageViewHolder {
        return SavedImageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SavedImageViewHolder, position: Int) {
        holder.bind(savedImages[position], onClick)
    }

    class SavedImageViewHolder(val binding: SavedImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): SavedImageViewHolder {
                return SavedImageViewHolder(
                    SavedImageItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        fun bind(savedImage: Pair<File, Bitmap>, onClick: SavedImageListener) {
            savedImage.apply {
                binding.image.setImageBitmap(second)
                binding.root.setOnClickListener {
                    onClick.onClickImage(first)
                }
            }
        }
    }
}