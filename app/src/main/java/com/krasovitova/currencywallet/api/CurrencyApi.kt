package com.krasovitova.currencywallet.api

import com.krasovitova.currencywallet.wallet.CurrencyRequest
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyApi {
    @GET("symbols")
    suspend fun getCurrencies(): Response<CurrencyRequest>
}
