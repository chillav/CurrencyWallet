package com.krasovitova.currencywallet.transaction

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputEditText
import com.krasovitova.currencywallet.R
import java.util.*

class TransactionFragment : Fragment(R.layout.fragment_transaction) {
    private val viewModel: TransactionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapterCurrencyMenu = ArrayAdapter(
            requireContext(),
            R.layout.item_dropdown_text,
            viewModel.currencies
        )
        val adapterTransactionTypeMenu = ArrayAdapter(
            requireContext(),
            R.layout.item_dropdown_text,
            viewModel.transactionTypes
        )
        view.findViewById<AutoCompleteTextView>(R.id.auto_complete_text_currency)
            .setAdapter(adapterCurrencyMenu)


        view.findViewById<AutoCompleteTextView>(R.id.auto_complete_text_transaction_type)
            .setAdapter(adapterTransactionTypeMenu)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dateTransactionAreaClick: View = view.findViewById(R.id.transaction_date_click_area)
        val transactionDate = view.findViewById<TextInputEditText>(R.id.text_transaction_date)

        dateTransactionAreaClick.setOnClickListener {
            DatePickerDialog(
                requireContext(), { _, year, month, day ->
                    val dateString = "$day.${month.inc()}.$year"
                    transactionDate.setText(dateString)
                }, year, month, day
            ).show()
        }
    }
}
