package com.krasovitova.currencywallet.transaction

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.krasovitova.currencywallet.Constants.DATE_FORMAT
import com.krasovitova.currencywallet.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.consumeEach
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class TransactionFragment : Fragment(R.layout.fragment_transaction) {
    private val viewModel: TransactionViewModel by viewModels()

    private val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sumView = view.findViewById<TextInputEditText>(R.id.text_input_sum)
        val currencyView = view.findViewById<AutoCompleteTextView>(
            R.id.auto_complete_text_currency
        )

        viewModel.abbreviationsCurrencies.observe(viewLifecycleOwner) {
            val adapterCurrencyMenu = ArrayAdapter(
                requireContext(),
                R.layout.item_dropdown_text,
                it
            )
            currencyView.setAdapter(adapterCurrencyMenu)
        }

        val adapterTransactionTypeMenu = ArrayAdapter(
            requireContext(),
            R.layout.item_dropdown_text,
            viewModel.transactionTypes
        )

        val transactionTypeView = view.findViewById<AutoCompleteTextView>(
            R.id.auto_complete_text_transaction_type
        ).apply {
            setAdapter(adapterTransactionTypeMenu)
        }

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dateTransactionAreaView =
            view.findViewById<View>(R.id.transaction_date_click_area)

        val transactionDateView = view.findViewById<TextInputEditText>(R.id.text_transaction_date)

        dateTransactionAreaView.setOnClickListener {
            DatePickerDialog(
                requireContext(), { _, year, month, day ->
                    calendar.set(year, month, day)
                    val dateString = dateFormat.format(calendar.time)
                    transactionDateView.setText(dateString)
                }, year, month, day
            ).show()
        }

        view.findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        val buttonTransaction = view.findViewById<Button>(R.id.button_add_transaction)

        buttonTransaction.setOnClickListener {
            val currency = currencyView.text.toString()
            val type = transactionTypeView.text.toString()
            val date = transactionDateView.text.toString()
            val sum = sumView.text.toString()

            viewModel.saveTransaction(
                transactionUi = TransactionUi(
                    sum = sum,
                    currency = currency,
                    date = date,
                    type = type
                )
            )
        }

        handleSideEffects()
    }

    private fun handleSideEffects() {
        lifecycleScope.launchWhenResumed {
            viewModel.sideEffect.consumeEach { effect ->
                when (effect) {
                    is TransactionScreenSideEffects.ValidationFailed -> {
                        effect.errors.forEach { error ->
                            when (error) {
                                // TODO ?????????????? ???? ?????????????? ?? ?????????????? ????????????
                                SaveTransactionError.EMPTY_SUM -> {
                                    view?.findViewById<TextInputLayout>(R.id.text_count)?.error =
                                        "?????????????? ??????????"
                                }
                                SaveTransactionError.EMPTY_CURRENCY -> {
                                    view?.findViewById<TextInputLayout>(R.id.text_input_currency)?.error =
                                        "???????????????? ????????????"
                                }
                                SaveTransactionError.EMPTY_DATE -> {
                                    view?.findViewById<TextInputLayout>(R.id.input_layout_transaction_date)?.error =
                                        "???????????????? ????????"
                                }
                                SaveTransactionError.EMPTY_TYPE -> {
                                    view?.findViewById<TextInputLayout>(R.id.text_input_transaction_type)?.error =
                                        "???????????????? ?????? ????????????????????"
                                }
                            }
                        }
                    }
                    TransactionScreenSideEffects.NavigateBack -> {
                        activity?.onBackPressed()
                    }
                }
            }
        }
    }
}
