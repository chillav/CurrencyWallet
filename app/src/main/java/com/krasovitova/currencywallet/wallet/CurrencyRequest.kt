package com.krasovitova.currencywallet.wallet

import com.google.gson.annotations.SerializedName

data class CurrencyRequest(
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("symbols")
    val currencies: Map<String, CurrencyModel>?
)

data class CurrencyModel(
    @SerializedName("description")
    val description: String?
)