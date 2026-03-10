package com.krasovitova.currencywallet.wallet

import com.krasovitova.currencywallet.transaction.presentation.TransactionType

sealed class WalletDescriptionItems {
    data class Title(
        val date: String,
        val sum: String
    ) : WalletDescriptionItems()

    data class Transaction(
        val id: Int,
        val transactionName: String,
        val type: TransactionType
    ) : WalletDescriptionItems()

    object Divider : WalletDescriptionItems()
}
