package com.krasovitova.currencywallet.wallet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.krasovitova.currencywallet.R
import com.krasovitova.currencywallet.wallet.currency.CurrencyAdapter


class WalletFragment : Fragment(R.layout.fragment_wallet) {

    private val viewModel: WalletViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerCurrencies = view.findViewById<RecyclerView>(R.id.recycler_currencies)
        val recyclerTransactions = view.findViewById<RecyclerView>(R.id.recycler_transactions)
        val adapterCurrency = CurrencyAdapter({})
        val adapterTransactions = WalletDescriptionAdapter()

        recyclerCurrencies.adapter = adapterCurrency
        recyclerTransactions.adapter = adapterTransactions

        viewModel.currencies.observe(viewLifecycleOwner) { currencies ->
            adapterCurrency.currencies = currencies
        }

        viewModel.transaction.observe(viewLifecycleOwner) { transactions ->
            adapterTransactions.submitList(transactions)
        }

        val addTransactionFab = view.findViewById<FloatingActionButton>(R.id.fab_add_transaction)

        addTransactionFab.setOnClickListener { fab ->
            Snackbar.make(fab, "Snackbar", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }
    }
}