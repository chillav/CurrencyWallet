package com.krasovitova.currencywallet.transaction

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(

) : ViewModel() {
    val currencies = emptyList<String>()
    val transactionTypes = TransactionType.titles()
}