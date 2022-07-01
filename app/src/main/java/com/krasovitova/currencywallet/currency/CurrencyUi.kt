package com.krasovitova.currencywallet.currency

import com.krasovitova.currencywallet.sql.CurrencyEntity

data class CurrencyUi(
    val id: Int,
    val abbreviation: String,
    val description: String
)

fun List<CurrencyUi>.mapToEntity(): List<CurrencyEntity> {
    return this.map {
        CurrencyEntity(
            id = it.id,
            abbreviation = it.abbreviation,
            description = it.description
        )
    }
}