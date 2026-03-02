package com.krasovitova.currencywallet.transaction

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.krasovitova.currencywallet.Constants.DATE_FORMAT
import com.krasovitova.currencywallet.Constants.TRANSACTION_ID_ARG
import com.krasovitova.currencywallet.R
import com.krasovitova.currencywallet.base.BaseFragment
import com.krasovitova.currencywallet.data.CurrencyRepository
import com.krasovitova.currencywallet.databinding.FragmentTransactionBinding
import com.krasovitova.currencywallet.di.TransactionViewModelFactory
import com.krasovitova.currencywallet.transaction.presentation.SaveTransactionError
import com.krasovitova.currencywallet.transaction.presentation.TransactionScreenEvent
import com.krasovitova.currencywallet.transaction.presentation.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class TransactionFragment : BaseFragment<FragmentTransactionBinding>(
    FragmentTransactionBinding::inflate
) {

    @Inject lateinit var viewModelFactory: TransactionViewModelFactory
    @Inject lateinit var currencyRepository: CurrencyRepository

    private val viewModel: TransactionViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val transactionId = arguments?.getInt(TRANSACTION_ID_ARG, 0) ?: 0

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        setupTypeDropdown()
        setupCurrencyDropdown()
        setupDatePicker()

        binding.textInputSum.doAfterTextChanged {
            viewModel.updateSum(it?.toString().orEmpty())
        }

        binding.buttonAddTransaction.setOnClickListener {
            viewModel.saveTransaction()
        }

        lifecycleScope.launch {
            viewModel.event.collect { event ->
                when (event) {
                    TransactionScreenEvent.NavigateBack ->
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    is TransactionScreenEvent.ValidationFailed ->
                        showValidationErrors(event.errors)
                }
            }
        }

        if (transactionId != 0) {
            lifecycleScope.launch {
                viewModel.fetchTransaction(transactionId)
                val state = viewModel.uiState.value
                binding.textInputSum.setText(state.sum)
                binding.autoCompleteTextCurrency.setText(state.currency, false)
                binding.textTransactionDate.setText(state.date)
                binding.autoCompleteTextTransactionType.setText(state.type, false)
            }
        }
    }

    private fun setupTypeDropdown() {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            viewModel.transactionTypes
        )
        binding.autoCompleteTextTransactionType.setAdapter(adapter)
        binding.autoCompleteTextTransactionType.setOnItemClickListener { _, _, position, _ ->
            viewModel.updateType(viewModel.transactionTypes[position])
        }
    }

    private fun setupCurrencyDropdown() {
        lifecycleScope.launch {
            currencyRepository.getUserCurrencies().collect { currencies ->
                val abbreviations = currencies.map { it.abbreviation }
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    abbreviations
                )
                binding.autoCompleteTextCurrency.setAdapter(adapter)
            }
        }
        binding.autoCompleteTextCurrency.setOnItemClickListener { parent, _, position, _ ->
            viewModel.updateCurrency(parent.getItemAtPosition(position).toString())
        }
    }

    private fun setupDatePicker() {
        val openPicker = {
            val picker = MaterialDatePicker.Builder.datePicker().build()
            picker.addOnPositiveButtonClickListener { millis ->
                val formatted = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
                    .format(Date(millis))
                viewModel.updateDate(formatted)
                binding.textTransactionDate.setText(formatted)
            }
            picker.show(parentFragmentManager, null)
        }
        binding.transactionDateClickArea.setOnClickListener { openPicker() }
        binding.inputLayoutTransactionDate.setEndIconOnClickListener { openPicker() }
    }

    private fun showValidationErrors(errors: List<SaveTransactionError>) {
        binding.textCount.error = null
        binding.textInputCurrency.error = null
        binding.inputLayoutTransactionDate.error = null
        binding.textInputTransactionType.error = null

        errors.forEach { error ->
            when (error) {
                SaveTransactionError.EMPTY_SUM ->
                    binding.textCount.error = getString(R.string.empty_sum_error)
                SaveTransactionError.EMPTY_CURRENCY ->
                    binding.textInputCurrency.error = getString(R.string.empty_currency_error)
                SaveTransactionError.EMPTY_DATE ->
                    binding.inputLayoutTransactionDate.error = getString(R.string.empty_date_error)
                SaveTransactionError.EMPTY_TYPE ->
                    binding.textInputTransactionType.error = getString(R.string.empty_type_transactions_error)
            }
        }
    }
}
