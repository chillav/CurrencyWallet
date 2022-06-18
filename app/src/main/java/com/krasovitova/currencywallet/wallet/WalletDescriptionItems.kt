package com.krasovitova.currencywallet.wallet

sealed class WalletDescriptionItems {
    data class Title(
        val id: Int,
        val date: String,
        val sum: String
    ) : WalletDescriptionItems()

    data class Transaction(
        val id: Int,
        val transactionName: String
    ) : WalletDescriptionItems()
}
