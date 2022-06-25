package com.krasovitova.currencywallet.transaction

import androidx.lifecycle.ViewModel

class TransactionViewModel : ViewModel() {
    val currencies = emptyList<String>()
    val transactionTypes = TransactionType.titles()
}