package com.krasovitova.currencywallet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView


class WalletFragment : Fragment(R.layout.fragment_wallet) {

    private val viewModel: WalletViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapterCurrency = CurrencyAdapter({})

        viewModel.currencies.observe(viewLifecycleOwner) { currencies ->
            adapterCurrency.currencies = currencies
        }

        val recyclerCurrencies = view.findViewById<RecyclerView>(R.id.recycler_currencies)

        recyclerCurrencies.adapter = adapterCurrency
    }
}