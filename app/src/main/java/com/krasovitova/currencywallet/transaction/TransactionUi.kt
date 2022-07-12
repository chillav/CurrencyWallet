package com.krasovitova.currencywallet.transaction

data class TransactionUi(
    val id: Int? = null,
    val sum: String,
    val currency: String,
    val date: String,
    val type: String
)