package com.krasovitova.currencywallet.currency

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.krasovitova.currencywallet.base.BaseFragment
import com.krasovitova.currencywallet.databinding.FragmentCurrenciesBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CurrenciesFragment : BaseFragment<FragmentCurrenciesBinding>(
    FragmentCurrenciesBinding::inflate
) {
    private val viewModel: CurrencyViewModel by viewModels()

    private val adapterCurrency = CurrencyAdapter {
        viewModel.saveCurrencyCheckedState(currency = it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerCurrencies.adapter = adapterCurrency

        viewModel.filteredCurrencies.observe(viewLifecycleOwner) {
            adapterCurrency.submitList(it)
        }

        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        binding.textSearchCurrency.addTextChangedListener(
            onTextChanged = { text, _, _, _ ->
                viewModel.filterCurrencies(text.toString())
            }
        )
        binding.buttonAddCurrency.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}
