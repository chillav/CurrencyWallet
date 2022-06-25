package com.krasovitova.currencywallet.currency

import androidx.recyclerview.widget.DiffUtil

class CurrencyDiffUtil : DiffUtil.ItemCallback<CurrencyUi>() {
    override fun areItemsTheSame(oldItem: CurrencyUi, newItem: CurrencyUi): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CurrencyUi, newItem: CurrencyUi): Boolean {
        return oldItem == newItem
    }
}
