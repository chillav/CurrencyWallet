package com.krasovitova.currencywallet.currency

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.krasovitova.currencywallet.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CurrenciesFragment : Fragment(R.layout.fragment_currencies) {
    private val viewModel: CurrencyViewModel by viewModels()

    private val adapterCurrency = CurrencyAdapter {
        viewModel.saveCurrencyCheckedState(currency = it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerCurrencies = view.findViewById<RecyclerView>(R.id.recycler_currencies)
        recyclerCurrencies.adapter = adapterCurrency

        viewModel.filteredCurrencies.observe(viewLifecycleOwner) {
            adapterCurrency.submitList(it)
        }

        view.findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        val searchCurrency = view.findViewById<EditText>(R.id.text_search_currency)

        searchCurrency.addTextChangedListener(
            onTextChanged = { text, _, _, _ ->
                viewModel.filterCurrencies(text.toString())
            }
        )
        view.findViewById<Button>(R.id.button_add_currency).setOnClickListener {
            activity?.onBackPressed()
        }
    }
}
