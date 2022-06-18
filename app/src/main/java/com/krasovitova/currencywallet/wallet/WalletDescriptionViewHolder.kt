package com.krasovitova.currencywallet.wallet

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.krasovitova.currencywallet.R

sealed class WalletDescriptionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class Title(view: View) : WalletDescriptionViewHolder(view) {
        val date: TextView = view.findViewById(R.id.text_date)
        val sum: TextView = view.findViewById(R.id.text_sum_day)
    }

    class Transaction(view: View) : WalletDescriptionViewHolder(view) {
        val transaction: TextView = view.findViewById(R.id.text_transaction)
    }
}
