package com.krasovitova.currencywallet.wallet

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.krasovitova.currencywallet.R
import com.krasovitova.currencywallet.currency.CurrenciesFragment
import com.krasovitova.currencywallet.currency.CurrencyChipAdapter
import com.krasovitova.currencywallet.transaction.TransactionFragment
import com.krasovitova.currencywallet.transaction.TransactionMenu
import com.krasovitova.currencywallet.wallet.WalletViewModel.Companion.ADD_CURRENCY_TAB_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletFragment : Fragment(R.layout.fragment_wallet) {

    private val viewModel: WalletViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerCurrencies = view.findViewById<RecyclerView>(R.id.recycler_currencies)
        val recyclerTransactions = view.findViewById<RecyclerView>(R.id.recycler_transactions)

        val adapterCurrency = CurrencyChipAdapter { currency ->
            if (currency.id == ADD_CURRENCY_TAB_ID) {
                activity?.supportFragmentManager?.commit {
                    setReorderingAllowed(true)
                    replace(R.id.fragment_container, CurrenciesFragment()).addToBackStack(null)
                }
            }
        }

        val adapterTransactions = WalletAdapter {
            val items = TransactionMenu.titles().toTypedArray()
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.setting))
                .setItems(items) { dialog, which ->

                }
                .setNegativeButton(getString(R.string.cancel)) { dialog, which ->

                }
                .show()
        }

        recyclerCurrencies.adapter = adapterCurrency
        recyclerTransactions.adapter = adapterTransactions

        viewModel.currencies.observe(viewLifecycleOwner) { currencies ->
            adapterCurrency.submitList(currencies)
        }

        viewModel.transactions.observe(viewLifecycleOwner) { transactions ->
            adapterTransactions.submitList(transactions)
        }

        val addTransactionFab = view.findViewById<FloatingActionButton>(R.id.fab_add_transaction)

        addTransactionFab.setOnClickListener {

            activity?.supportFragmentManager?.commit {
                setReorderingAllowed(true)
                replace(R.id.fragment_container, TransactionFragment()).addToBackStack(null)
            }
        }

        view.findViewById<ImageView>(R.id.image_burger).setOnClickListener {
            activity?.findViewById<DrawerLayout>(R.id.drawer_layout)?.open()
        }

        viewModel.initState()
    }
}
