package com.krasovitova.currencywallet.transaction.presentation

data class TransactionState(
    val id: Int,
    val sum: String,
    val type: String,
    val date: String,
    val currency: String,
)
