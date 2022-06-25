package com.krasovitova.currencywallet.currency

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krasovitova.currencywallet.wallet.CurrencyRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

class CurrencyViewModel : ViewModel() {
    val currencies = MutableLiveData<List<CurrencyUi>>()

    init {
        getCurrencies()
    }

    private fun getCurrencies() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.apilayer.com/exchangerates_data/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val currencyApi = retrofit.create(CurrencyApi::class.java)

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

    interface CurrencyApi {

        @GET("symbols")
        suspend fun getCurrencies(
            @Header("apikey") token: String
        ): Response<CurrencyRequest>
    }
}