package com.krasovitova.currencywallet.transaction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.krasovitova.currencywallet.currency.CurrencyUi
import com.krasovitova.currencywallet.data.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val currencyRepository: CurrencyRepository
) : ViewModel() {
    private val currencies = MutableLiveData<List<CurrencyUi>>()
    val transactionTypes = TransactionType.titles()

    val abbreviationsCurrencies by lazy {
        currencies.map { list ->
            list.map { it.abbreviation }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            currencies.postValue(getCurrencies())
        }
    }

    private suspend fun getCurrencies(): List<CurrencyUi> {
        return currencyRepository.getUserCurrencies()
    }

    fun saveTransaction(transactionUi: TransactionUi) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionRepository.saveTransaction(transactionUi)
        }
    }
}
