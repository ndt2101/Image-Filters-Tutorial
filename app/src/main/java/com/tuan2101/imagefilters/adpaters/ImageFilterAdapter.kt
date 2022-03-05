package com.tuan2101.imagefilters.adpaters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.tuan2101.imagefilters.R
import com.tuan2101.imagefilters.callbacks.ImageFilterListener
import com.tuan2101.imagefilters.data.ImageFilter
import com.tuan2101.imagefilters.databinding.ItemFilterBinding

/**
 * Created by ndt2101 on 3/3/2022.
 */
class ImageFilterAdapter(
    val imageFilters: List<ImageFilter>,
    private val imageFilterListener: ImageFilterListener
) : RecyclerView.Adapter<ImageFilterAdapter.FilterViewHolder>() {

    private var previousPosition = MutableLiveData(0)
    private var currentPosition = MutableLiveData(0)

    inner class FilterViewHolder(private val binding: ItemFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            imageFilter: ImageFilter,
            imageFilterListener: ImageFilterListener,
            position: Int
        ) {
            binding.textFilterName.text = imageFilter.name
            binding.imageFilterPreview.setImageBitmap(imageFilter.filterPreview)
            binding.root.setOnClickListener {
                if (currentPosition.value != position) {
                    previousPosition.value = currentPosition.value
                    currentPosition.value = position
                    imageFilterListener.onFilterSelected(imageFilter)
                    this@ImageFilterAdapter.notifyItemChanged(previousPosition.value!!, Unit)
                    this@ImageFilterAdapter.notifyItemChanged(currentPosition.value!!, Unit)
                }
            }
            binding.textFilterName.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    if (position == currentPosition.value) R.color.primary_dark else R.color.primary_text
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        return FilterViewHolder(
            ItemFilterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(
            imageFilters[position],
            imageFilterListener,
            position
        )
    }

    override fun getItemCount(): Int {
        return imageFilters.size
    }
}