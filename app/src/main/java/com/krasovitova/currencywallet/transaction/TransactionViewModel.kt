package com.krasovitova.currencywallet.transaction

import androidx.lifecycle.ViewModel
import com.krasovitova.currencywallet.currency.Currency

class TransactionViewModel : ViewModel() {
    val currencies = Currency.titles()
    val transactionTypes = TransactionType.titles()
}