package com.krasovitova.currencywallet.wallet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.krasovitova.currencywallet.currency.Currency

class WalletViewModel : ViewModel() {
    val currencies = Currency.titles()
    val transactions = MutableLiveData<List<WalletDescriptionItems>>()

    init {
        transactions.value = getTransactionsHistory()
    }

    private fun getTransactionsHistory(): List<WalletDescriptionItems> {
        return listOf(
            WalletDescriptionItems.Title(0, "12.06.2022", "1212$"),
            WalletDescriptionItems.Transaction(1, "1212$"),
            WalletDescriptionItems.Title(3, "13.06.2022", "1000$"),
            WalletDescriptionItems.Transaction(4, "500$"),
            WalletDescriptionItems.Transaction(5, "500$")
        )
    }
}