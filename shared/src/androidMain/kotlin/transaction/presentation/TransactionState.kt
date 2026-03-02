package com.krasovitova.currencywallet.transaction

internal data class TransactionState(
    val id: Int,
    val sum: String,
    val type: String,
    val date: String,
    val currency: String,
)