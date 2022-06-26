package com.krasovitova.currencywallet.currency

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krasovitova.currencywallet.api.CurrencyApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val currencyApi: CurrencyApi
) : ViewModel() {
    val currencies = MutableLiveData<List<CurrencyUi>>()

    init {
        getCurrencies()
    }

    private fun getCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = currencyApi.getCurrencies(
                token = "iZevk054PqVNrQZUcNG3ldxyoFGMLM5A"
            ).body()

            val currenciesUi = result?.currencies?.entries?.mapIndexed { index, entry ->
                CurrencyUi(
                    id = index.inc(), // индекс 0 занят табом для добавления валют
                    abbreviation = entry.key,
                    description = entry.value
                )
            }.orEmpty()

            currencies.postValue(currenciesUi)
        }
    }
}