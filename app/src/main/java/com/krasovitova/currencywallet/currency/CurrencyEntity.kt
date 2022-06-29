package com.krasovitova.currencywallet.currency

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "TABLE_CURRENCY", primaryKeys = ["ID"])
data class CurrencyEntity(
    @ColumnInfo(name = "ID")
    val id: Int,
    @ColumnInfo(name = "ABBREVIATION")
    val abbreviation: String,
    @ColumnInfo(name = "DESCRIPTION")
    val description: String
)