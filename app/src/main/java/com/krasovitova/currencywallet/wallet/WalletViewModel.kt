package com.krasovitova.currencywallet.wallet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.krasovitova.currencywallet.currency.CurrencyUi


class WalletViewModel : ViewModel() {
    val transactions = MutableLiveData<List<WalletDescriptionItems>>()
    val currencies = MutableLiveData<List<CurrencyUi>>()

    init {
        transactions.value = getTransactionsHistory()
        currencies.value = getCurrencies()
    }

    private fun getCurrencies(): List<CurrencyUi> {
        return getUserCurrencies() + getAddingCurrencyTab()
    }

    /**
     * Возвращает список валют, которые выбрал пользователь
     */
    private fun getUserCurrencies(): List<CurrencyUi> {
        return emptyList()
    }

    private fun getAddingCurrencyTab() = CurrencyUi(
        id = ADD_CURRENCY_TAB_ID,
        abbreviation = ADD_CURRENCY_ABBREVIATION,
        description = ""
    )


    private fun getTransactionsHistory(): List<WalletDescriptionItems> {
        return listOf(
            WalletDescriptionItems.Title(0, "12.06.2022", "1212$"),
            WalletDescriptionItems.Transaction(1, "1212$"),
            WalletDescriptionItems.Title(3, "13.06.2022", "1000$"),
            WalletDescriptionItems.Transaction(4, "500$"),
            WalletDescriptionItems.Transaction(5, "500$")
        )
    }

    companion object {
        private const val ADD_CURRENCY_ABBREVIATION = "+"
        const val ADD_CURRENCY_TAB_ID = 0
    }
}
