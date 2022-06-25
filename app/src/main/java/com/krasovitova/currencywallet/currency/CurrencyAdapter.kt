package com.krasovitova.currencywallet.currency

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.krasovitova.currencywallet.R

class CurrencyAdapter(
    val onItemClick: (CurrencyUi) -> Unit
) : ListAdapter<CurrencyUi, CurrenciesViewHolder>(CurrencyDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrenciesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_currency, parent, false)

        return CurrenciesViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrenciesViewHolder, position: Int) {
        val currency = currentList[position]
        val title = "${currency.abbreviation} - ${currency.description}"

        holder.title.text = title
        holder.itemView.setOnClickListener {
            onItemClick(currency)
        }
    }

    override fun getItemCount() = currentList.size
}

class CurrenciesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title: TextView = itemView.findViewById(R.id.text_currency)
}