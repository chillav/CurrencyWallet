package com.krasovitova.currencywallet.wallet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.krasovitova.currencywallet.R

class WalletDescriptionAdapter :
    ListAdapter<WalletDescriptionItems, WalletDescriptionViewHolder>(WalletDescriptionDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletDescriptionViewHolder {
        return when (viewType) {
            R.layout.item_history_date -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_history_date, parent, false)

                WalletDescriptionViewHolder.Title(view)
            }
            R.layout.item_history_transaction -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_history_transaction, parent, false)

                WalletDescriptionViewHolder.Transaction(view)
            }
            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun onBindViewHolder(holder: WalletDescriptionViewHolder, position: Int) {
        when (holder) {
            is WalletDescriptionViewHolder.Transaction -> {
                val item = currentList[position] as WalletDescriptionItems.Transaction
                holder.transaction.text = item.transactionName
            }
            is WalletDescriptionViewHolder.Title -> {
                val item = currentList[position] as WalletDescriptionItems.Title
                holder.date.text = item.date
                holder.sum.text = item.sum
            }
        }
    }

    override fun getItemCount(): Int = currentList.size

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is WalletDescriptionItems.Title -> R.layout.item_history_date
            is WalletDescriptionItems.Transaction -> R.layout.item_history_transaction
        }
    }
}

