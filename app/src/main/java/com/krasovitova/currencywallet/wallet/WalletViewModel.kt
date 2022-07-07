package com.krasovitova.currencywallet.wallet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krasovitova.currencywallet.currency.CurrencyUi
import com.krasovitova.currencywallet.data.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {
    val transactions = MutableLiveData<List<WalletDescriptionItems>>()
    val currencies = MutableLiveData<List<CurrencyUi>>()

    fun initState() {
        viewModelScope.launch(Dispatchers.IO) {
            transactions.postValue(getTransactionsHistory())
            currencies.postValue(getCurrencies())
        }
    }

    private suspend fun getCurrencies(): List<CurrencyUi> {
        return getUserCurrencies() + getAddingCurrencyTab()
    }

    /**
     * Возвращает список валют, которые выбрал пользователь
     */
    private suspend fun getUserCurrencies(): List<CurrencyUi> {
        return currencyRepository.getUserCurrencies()
    }

    private fun getAddingCurrencyTab() = CurrencyUi(
        id = ADD_CURRENCY_TAB_ID,
        abbreviation = ADD_CURRENCY_ABBREVIATION,
        description = "",
        isSelected = true
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
