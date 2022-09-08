package com.krasovitova.currencywallet.avatar

import androidx.recyclerview.widget.DiffUtil

class ImageDiffUtil : DiffUtil.ItemCallback<ImageUi>() {
    override fun areItemsTheSame(oldItem: ImageUi, newItem: ImageUi): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ImageUi, newItem: ImageUi): Boolean {
        return oldItem == newItem
    }
}