package com.krasovitova.currencywallet.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.krasovitova.currencywallet.data.database.currency.CurrencyDao
import com.krasovitova.currencywallet.data.database.currency.CurrencyEntity
import com.krasovitova.currencywallet.data.database.transaction.TransactionDao
import com.krasovitova.currencywallet.data.database.transaction.TransactionEntity

@Database(entities = [CurrencyEntity::class, TransactionEntity::class], version = 1)
abstract class CurrencyWalletDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun transactionDao(): TransactionDao
}
