package com.krasovitova.currencywallet.wallet.currency

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.krasovitova.currencywallet.R

class CurrencyAdapter(
    val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<CurrencyViewHolder>() {

    var currencies: List<String> = emptyList()
        set(value) {
            val diffUtil = CurrencyDiffCallback(currencies, value)
            val diffResults = DiffUtil.calculateDiff(diffUtil)
            field = value
            diffResults.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_currency, parent, false)

        view.setOnClickListener {
            val text = view.findViewById<TextView>(R.id.text_currency).text.toString()
            onItemClick(text)
        }

        return CurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = currencies[position]
        holder.currency.text = currency
    }

    override fun getItemCount() = currencies.size
}

class CurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val currency: TextView = itemView.findViewById(R.id.text_currency)
}