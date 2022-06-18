package com.krasovitova.currencywallet.wallet

import androidx.recyclerview.widget.DiffUtil

class WalletDescriptionDiffUtil : DiffUtil.ItemCallback<WalletDescriptionItems>() {
    override fun areItemsTheSame(
        oldItem: WalletDescriptionItems,
        newItem: WalletDescriptionItems
    ): Boolean {
        return when {
            oldItem is WalletDescriptionItems.Title && newItem is WalletDescriptionItems.Title -> {
                oldItem.id == newItem.id
            }
            oldItem is WalletDescriptionItems.Transaction && newItem is WalletDescriptionItems.Transaction -> {
                oldItem.id == newItem.id
            }
            else -> false
        }
    }
    override fun areContentsTheSame(
        oldItem: WalletDescriptionItems,
        newItem: WalletDescriptionItems
    ): Boolean = oldItem == newItem

}