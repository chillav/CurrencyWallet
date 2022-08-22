package com.krasovitova.currencywallet.wallet

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.krasovitova.currencywallet.Constants.TRANSACTION_ID_ARG
import com.krasovitova.currencywallet.R
import com.krasovitova.currencywallet.base.BaseFragment
import com.krasovitova.currencywallet.currency.CurrenciesFragment
import com.krasovitova.currencywallet.currency.CurrencyChipAdapter
import com.krasovitova.currencywallet.databinding.FragmentWalletBinding
import com.krasovitova.currencywallet.transaction.TransactionFragment
import com.krasovitova.currencywallet.transaction.TransactionMenu
import com.krasovitova.currencywallet.wallet.WalletViewModel.Companion.ADD_CURRENCY_TAB_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding>(
    FragmentWalletBinding::inflate
) {
    private val viewModel: WalletViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapterCurrency = CurrencyChipAdapter { currency ->
            if (currency.id == ADD_CURRENCY_TAB_ID) {
                openFragment(fragment = CurrenciesFragment())
            }
        }

        val adapterTransactions = WalletAdapter {
            val items = TransactionMenu.titles().toTypedArray()

            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.setting))
                .setItems(items) { _, index ->
                    val selectedItem = TransactionMenu.getMenuItemByTitle(items[index])

                    when (selectedItem) {
                        TransactionMenu.EDIT -> {
                            openFragment(
                                fragment = TransactionFragment(),
                                args = bundleOf(TRANSACTION_ID_ARG to it.id)
                            )
                        }
                        TransactionMenu.DELETE -> viewModel.deleteTransactionById(it.id)
                    }
                }
                .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
                .show()
        }

        binding.recyclerCurrencies.adapter = adapterCurrency
        binding.recyclerTransactions.adapter = adapterTransactions

        viewModel.currencies.observe(viewLifecycleOwner) { currencies ->
            adapterCurrency.submitList(currencies)
        }

        viewModel.transactions.observe(viewLifecycleOwner) { transactions ->
            adapterTransactions.submitList(transactions)
        }

        binding.fabAddTransaction.setOnClickListener {
            openFragment(fragment = TransactionFragment())
        }

        binding.imageBurger.setOnClickListener {
            activity?.findViewById<DrawerLayout>(R.id.drawer_layout)?.open()
        }

        viewModel.initWallet()
    }
}
