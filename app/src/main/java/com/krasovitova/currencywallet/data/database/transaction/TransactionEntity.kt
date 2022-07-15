package com.krasovitova.currencywallet.data.database.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TRANSACTION_TABLE")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    val id: Int? = null,
    @ColumnInfo(name = "SUM")
    val sum: String,
    @ColumnInfo(name = "ABBREVIATION")
    val currency: String,
    @ColumnInfo(name = "DATE")
    val date: Long,
    @ColumnInfo(name = "TYPE")
    val type: String
)