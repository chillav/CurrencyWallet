package com.krasovitova.currencywallet.data

import com.krasovitova.currencywallet.api.CurrencyApi
import com.krasovitova.currencywallet.currency.CurrencyUi
import com.krasovitova.currencywallet.sql.CurrencyDao
import com.krasovitova.currencywallet.wallet.CurrencyRequest
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val currencyDao: CurrencyDao,
    private val currencyApi: CurrencyApi
) {
    suspend fun getCurrencies(): List<CurrencyUi> {
        val result = currencyApi.getCurrencies().body()

        return result?.currencies?.entries?.mapIndexed { index, entry ->
            CurrencyUi(
                id = index.inc(), // индекс 0 занят табом для добавления валют
                abbreviation = entry.key,
                description = entry.value.description.orEmpty()
            )
        }.orEmpty()
    }

    fun cacheCurrencies(request: CurrencyRequest) {

    }
}