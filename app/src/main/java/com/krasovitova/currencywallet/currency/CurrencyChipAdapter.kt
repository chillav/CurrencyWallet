package com.krasovitova.currencywallet.currency

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.krasovitova.currencywallet.R

class CurrencyChipAdapter(
    val onItemClick: (CurrencyUi) -> Unit
) : ListAdapter<CurrencyUi, CurrencyViewHolder>(CurrencyDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chip_currency, parent, false)

        return CurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = currentList[position]
        holder.currencyChip.text = currency.abbreviation

        holder.itemView.setOnClickListener {
            onItemClick(currency)
        }
    }

    override fun getItemCount() = currentList.size
}

class CurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val currencyChip: TextView = itemView.findViewById(R.id.text_currency)
}