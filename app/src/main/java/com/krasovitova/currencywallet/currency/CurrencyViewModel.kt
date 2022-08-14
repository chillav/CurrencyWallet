package com.krasovitova.currencywallet.currency

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krasovitova.currencywallet.data.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {
    private val currencies = MutableLiveData<List<CurrencyUi>>()
    val filteredCurrencies = MutableLiveData<List<CurrencyUi>>()

    init {
        getCurrencies()
    }

    private fun getCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = currencyRepository.getCurrencies()
            currencies.postValue(result)
            filteredCurrencies.postValue(result)
        }
    }

    fun saveCurrencyCheckedState(currency: CurrencyUi) {
        val newCurrencies = currencies.value?.map {
            if (it.id == currency.id) currency else it
        }.orEmpty()

        val newFilteredCurrencies = filteredCurrencies.value?.map {
            if (it.id == currency.id) currency else it
        }.orEmpty()

        currencies.value = newCurrencies
        filteredCurrencies.value = newFilteredCurrencies

        viewModelScope.launch(Dispatchers.IO) {
            currencyRepository.saveCurrencyCheckedState(currency)
        }
    }

    fun filterCurrencies(text: String) {
        filteredCurrencies.value = if (text.isBlank()) {
            currencies.value
        } else {
            currencies.value?.filter {
                it.description.containsCurrency(text)
            }.orEmpty()
        }
    }

    private fun String.containsCurrency(text: String): Boolean {
        return this.lowercase(
            Locale.getDefault()
        ).contains(
            text.lowercase(Locale.getDefault())
        )
    }
}
