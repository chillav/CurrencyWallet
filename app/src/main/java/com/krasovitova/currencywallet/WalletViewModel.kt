package com.krasovitova.currencywallet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WalletViewModel : ViewModel() {
    val currencies = MutableLiveData<List<String>>()

    init {
        currencies.value = getListOfCurrency()
    }

    private fun getListOfCurrency(): List<String> {
        return listOf(
            "dollar", "euro",
            "yen", "franc",
            "rouble", "lari",
        )
    }
}