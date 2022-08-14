package com.krasovitova.currencywallet.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.krasovitova.currencywallet.Constants.TRANSACTION_ID_ARG
import com.krasovitova.currencywallet.R
import com.krasovitova.currencywallet.currency.CurrenciesFragment
import com.krasovitova.currencywallet.currency.CurrencyChipAdapter
import com.krasovitova.currencywallet.databinding.FragmentWalletBinding
import com.krasovitova.currencywallet.transaction.TransactionFragment
import com.krasovitova.currencywallet.transaction.TransactionMenu
import com.krasovitova.currencywallet.wallet.WalletViewModel.Companion.ADD_CURRENCY_TAB_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletFragment : Fragment() {

    private var _binding: FragmentWalletBinding? = null
    private val viewModel: WalletViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWalletBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                .setItems(items) { _, index ->
                    val selectedItem = TransactionMenu.getMenuItemByTitle(items[index])

                    when (selectedItem) {
                        TransactionMenu.EDIT -> {
                            openTransactionFragment(
                                args = bundleOf(TRANSACTION_ID_ARG to it.id)
                            )
                        }
                        TransactionMenu.DELETE -> {
                            // TODO handle delete click
                        }
                    }
                }
                .setNegativeButton(getString(R.string.cancel)) { dialog, which ->

                }
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
            openTransactionFragment()
        }

        binding.imageBurger.setOnClickListener {
            activity?.findViewById<DrawerLayout>(R.id.drawer_layout)?.open()
        }

        viewModel.initWallet()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openTransactionFragment(args: Bundle? = null) {
        activity?.supportFragmentManager?.commit {
            setReorderingAllowed(true)
            val fragment = TransactionFragment().apply {
                arguments = args
            }
            replace(R.id.fragment_container, fragment).addToBackStack(null)
        }
    }
}
