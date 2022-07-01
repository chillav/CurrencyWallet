package com.krasovitova.currencywallet.sql

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyDao {
    @Query("SELECT * from CURRENCY_TABLE ORDER BY ABBREVIATION ASC")
    suspend fun getCurrencies(): List<CurrencyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currencies: List<CurrencyEntity>)

    @Query("DELETE FROM CURRENCY_TABLE")
    suspend fun deleteAll()
}
