package com.krasovitova.currencywallet.data.database.currency

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {
    @Query("SELECT * from CURRENCY_TABLE WHERE IS_SELECTED = 1")
    fun getUserCurrencies(): Flow<List<CurrencyEntity>>

    @Query("SELECT * from CURRENCY_TABLE ORDER BY ABBREVIATION ASC")
    suspend fun getCurrencies(): List<CurrencyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencies: List<CurrencyEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currency: CurrencyEntity)

    @Query("DELETE FROM CURRENCY_TABLE")
    suspend fun deleteAll()
}
