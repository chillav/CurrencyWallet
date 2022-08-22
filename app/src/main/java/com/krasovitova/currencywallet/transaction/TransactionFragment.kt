package com.krasovitova.currencywallet.transaction

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.krasovitova.currencywallet.Constants.DATE_FORMAT
import com.krasovitova.currencywallet.Constants.TRANSACTION_ID_ARG
import com.krasovitova.currencywallet.R
import com.krasovitova.currencywallet.base.BaseFragment
import com.krasovitova.currencywallet.databinding.FragmentTransactionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class TransactionFragment : BaseFragment<FragmentTransactionBinding>(
    FragmentTransactionBinding::inflate
) {
    private val viewModel: TransactionViewModel by viewModels()
    private val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

    private val transactionIdArg by lazy {
        arguments?.getInt(TRANSACTION_ID_ARG)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textInputSum.apply {
            saveInto(viewModel.sumState)
        }

        binding.autoCompleteTextCurrency.apply {
            saveInto(viewModel.currencyState)
        }

        lifecycleScope.launch {
            viewModel.currencies.collect {
                val adapterCurrencyMenu = ArrayAdapter(
                    requireContext(),
                    R.layout.item_dropdown_text,
                    it
                )
                binding.autoCompleteTextCurrency.setAdapter(adapterCurrencyMenu)
            }
        }

        val adapterTransactionTypeMenu = ArrayAdapter(
            requireContext(),
            R.layout.item_dropdown_text,
            viewModel.transactionTypes
        )

        binding.autoCompleteTextTransactionType.apply {
            setAdapter(adapterTransactionTypeMenu)
            saveInto(viewModel.typeState)
        }

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dateTransactionAreaView = binding.transactionDateClickArea

        binding.textTransactionDate.apply {
            saveInto(viewModel.dateState)
        }

        dateTransactionAreaView.setOnClickListener {
            DatePickerDialog(
                requireContext(), { _, year, month, day ->
                    calendar.set(year, month, day)
                    val dateString = dateFormat.format(calendar.time)
                    binding.textTransactionDate.setText(dateString)
                }, year, month, day
            ).show()
        }

        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        binding.buttonAddTransaction

        binding.buttonAddTransaction.setOnClickListener {
            viewModel.saveTransaction()
        }

        handleSideEffects()

        transactionIdArg?.let {
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.fetchTransaction(it)

                withContext(Dispatchers.Main) {
                    binding.autoCompleteTextCurrency.setText(viewModel.currencyState.value, false)
                    binding.autoCompleteTextTransactionType.setText(
                        viewModel.typeState.value,
                        false
                    )
                    binding.textTransactionDate.setText(viewModel.dateState.value)
                    binding.textInputSum.setText(viewModel.sumState.value)
                }
            }
        }
    }

    private fun TextView.saveInto(livedata: MutableLiveData<String>) {
        addTextChangedListener {
            livedata.postValue(it.toString())
        }
    }

    private fun handleSideEffects() = lifecycleScope.launchWhenResumed {
        viewModel.sideEffect.consumeEach { effect ->
            when (effect) {
                is TransactionScreenSideEffects.ValidationFailed -> {
                    handleFailedValidation(effect)
                }
                is TransactionScreenSideEffects.NavigateBack -> {
                    activity?.onBackPressed()
                }
            }
        }
    }

    private fun handleFailedValidation(effect: TransactionScreenSideEffects.ValidationFailed) {
        effect.errors.forEach { error ->
            when (error) {
                SaveTransactionError.EMPTY_SUM -> {
                    binding.textCount.error = getString(R.string.empty_sum_error)
                }
                SaveTransactionError.EMPTY_CURRENCY -> {
                    binding.textInputCurrency.error = getString(R.string.empty_currency_error)
                }
                SaveTransactionError.EMPTY_DATE -> {
                    binding.inputLayoutTransactionDate.error = getString(R.string.empty_date_error)
                }
                SaveTransactionError.EMPTY_TYPE -> {
                    binding.textInputTransactionType.error =
                        getString(R.string.empty_type_transactions_error)
                }
            }
        }
    }
}
