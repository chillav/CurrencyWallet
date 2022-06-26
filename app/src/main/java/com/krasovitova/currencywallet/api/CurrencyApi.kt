package com.krasovitova.currencywallet.api

import com.krasovitova.currencywallet.wallet.CurrencyRequest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface CurrencyApi {
    @GET("symbols")
    suspend fun getCurrencies(
        @Header("apikey") token: String
    ): Response<CurrencyRequest>
}
