package com.krasovitova.currencywallet.sql

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CURRENCY_TABLE")
data class CurrencyEntity(
    @PrimaryKey
    @ColumnInfo(name = "ID")
    val id: Int,
    @ColumnInfo(name = "ABBREVIATION")
    val abbreviation: String,
    @ColumnInfo(name = "DESCRIPTION")
    val description: String
)