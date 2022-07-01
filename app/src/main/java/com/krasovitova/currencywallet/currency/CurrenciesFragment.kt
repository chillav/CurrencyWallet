package com.krasovitova.currencywallet.currency

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.krasovitova.currencywallet.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrenciesFragment : Fragment(R.layout.fragment_currencies) {
    private val viewModel: CurrencyViewModel by viewModels()

    private val adapterCurrency = CurrencyAdapter({})

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerCurrencies = view.findViewById<RecyclerView>(R.id.recycler_currencies)
        recyclerCurrencies.adapter = adapterCurrency

        viewModel.currencies.observe(viewLifecycleOwner) {
            adapterCurrency.submitList(it)
        }
        val buttonBack = view.findViewById<ImageView>(R.id.arrow_back)

        buttonBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}