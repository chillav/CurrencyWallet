package com.krasovitova.currencywallet.wallet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class WalletViewModel : ViewModel() {
    val currencies = MutableLiveData<List<String>>()
    val transactions = MutableLiveData<List<WalletDescriptionItems>>()
    init {
        currencies.value = getListOfCurrency()
        transactions.value = getTransactionsHistory()
    }

    fun addMockedTransactions() {
        val oldList = transactions.value.orEmpty()
        val newItems = listOf(
            WalletDescriptionItems.Title(
                Random.nextInt(),
                Random.nextInt().toString(),
                Random.nextInt().toString()
            ),
            WalletDescriptionItems.Transaction(
                Random.nextInt(),
                Random.nextInt().toString()
            )
        )
        transactions.value = newItems + oldList
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