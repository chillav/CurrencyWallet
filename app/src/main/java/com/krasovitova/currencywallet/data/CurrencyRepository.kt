package com.krasovitova.currencywallet.data

import com.krasovitova.currencywallet.api.CurrencyApi
import com.krasovitova.currencywallet.currency.CurrencyUi
import com.krasovitova.currencywallet.currency.mapToEntity
import com.krasovitova.currencywallet.sql.CurrencyDao
import com.krasovitova.currencywallet.sql.mapToUi
import timber.log.Timber
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val currencyDao: CurrencyDao,
    private val currencyApi: CurrencyApi
) {

    suspend fun getCurrencies(): List<CurrencyUi> {

        val cache = currencyDao.getCurrencies()

        if (cache.isNotEmpty()) {
            Timber.i("Cashed currencies returned.")
            return cache.mapToUi()
        }

        Timber.i("The currency cache is empty, we make a request via api.")

        val result = currencyApi.getCurrencies().body()

        val currencies = result?.currencies?.entries?.mapIndexed { index, entry ->
            CurrencyUi(
                id = index.inc(), // индекс 0 занят табом для добавления валют
                abbreviation = entry.key,
                description = entry.value.description.orEmpty()
            )
        }.orEmpty()

        currencyDao.insert(currencies.mapToEntity())

        return currencies
    }
}
