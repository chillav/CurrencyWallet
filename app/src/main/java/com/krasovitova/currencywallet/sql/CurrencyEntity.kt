package com.krasovitova.currencywallet.sql

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.krasovitova.currencywallet.currency.CurrencyUi

@Entity(tableName = "CURRENCY_TABLE")
data class CurrencyEntity(
    @PrimaryKey
    @ColumnInfo(name = "ID")
    val id: Int,
    @ColumnInfo(name = "ABBREVIATION")
    val abbreviation: String,
    @ColumnInfo(name = "DESCRIPTION")
    val description: String,
    @ColumnInfo(name = "IS_SELECTED")
    val isSelected: Boolean
)

fun List<CurrencyEntity>.mapToUi(): List<CurrencyUi> {
    return this.map {
        CurrencyUi(
            id = it.id,
            abbreviation = it.abbreviation,
            description = it.description,
            isSelected = it.isSelected
        )
    }
}

