package com.krasovitova.currencywallet.sql

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CurrencyEntity::class], version = 1)
abstract class CurrencyWalletDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}
