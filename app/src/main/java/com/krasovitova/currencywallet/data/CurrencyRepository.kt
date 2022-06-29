package com.krasovitova.currencywallet.data

import com.krasovitova.currencywallet.Constants
import com.krasovitova.currencywallet.api.CurrencyApi
import com.krasovitova.currencywallet.currency.CurrencyUi
import com.krasovitova.currencywallet.wallet.CurrencyRequest
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val currencyApi: CurrencyApi
) {
    suspend fun getCurrencies(): List<CurrencyUi> {
        val result = currencyApi.getCurrencies(
            token = Constants.API_KEY
        ).body()

        return result?.currencies?.entries?.mapIndexed { index, entry ->
            CurrencyUi(
                id = index.inc(), // индекс 0 занят табом для добавления валют
                abbreviation = entry.key,
                description = entry.value
            )
        }.orEmpty()
    }

    fun cacheCurrencies(request: CurrencyRequest) {

    }
}