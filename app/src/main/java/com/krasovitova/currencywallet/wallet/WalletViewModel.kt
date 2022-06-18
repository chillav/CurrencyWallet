package com.krasovitova.currencywallet.wallet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WalletViewModel : ViewModel() {
    val currencies = MutableLiveData<List<String>>()
    val transaction = MutableLiveData<List<WalletDescriptionItems>>()

    init {
        currencies.value = getListOfCurrency()
        transaction.value = getTransactionsHistory()
    }

    private fun getListOfCurrency(): List<String> {
        return listOf(
            "dollar", "euro",
            "yen", "franc",
            "rouble", "lari",
        )
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