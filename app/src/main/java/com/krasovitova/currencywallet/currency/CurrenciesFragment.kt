package com.krasovitova.currencywallet.currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.krasovitova.currencywallet.R
import com.krasovitova.currencywallet.databinding.FragmentCurrenciesBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CurrenciesFragment : Fragment(R.layout.fragment_currencies) {
    private val viewModel: CurrencyViewModel by viewModels()
    private var _binding: FragmentCurrenciesBinding? = null
    private val binding get() = _binding!!

    private val adapterCurrency = CurrencyAdapter {
        viewModel.saveCurrencyCheckedState(currency = it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrenciesBinding.inflate(inflater, container, false)
        return binding.root
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
