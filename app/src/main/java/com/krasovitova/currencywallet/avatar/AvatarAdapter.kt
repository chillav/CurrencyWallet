package com.krasovitova.currencywallet.avatar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.krasovitova.currencywallet.R

class AvatarAdapter(
    val onItemClick: (ImageUi) -> Unit
) : ListAdapter<ImageUi, ImageViewHolder>(ImageDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)

        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = currentList[position]
        holder.image.load(image.url) {
            transformations(RoundedCornersTransformation())
        }

        holder.itemView.setOnClickListener {
            onItemClick(image)
        }
    }

    override fun getItemCount() = currentList.size
}

class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val image: ImageView = itemView.findViewById(R.id.image_view)
}