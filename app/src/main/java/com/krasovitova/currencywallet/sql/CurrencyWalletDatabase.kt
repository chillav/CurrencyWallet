package com.krasovitova.currencywallet.sql

import androidx.room.Database
import androidx.room.RoomDatabase
import com.krasovitova.currencywallet.transaction.TransactionDao
import com.krasovitova.currencywallet.transaction.TransactionEntity

@Database(entities = [CurrencyEntity::class, TransactionEntity::class], version = 1)
abstract class CurrencyWalletDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun transactionDao(): TransactionDao
}
