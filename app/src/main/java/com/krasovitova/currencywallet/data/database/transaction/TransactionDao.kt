package com.krasovitova.currencywallet.data.database.transaction

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TransactionDao {
    @Query("SELECT * from TRANSACTION_TABLE ORDER BY DATE DESC")
    suspend fun getTransactions(): List<TransactionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionEntity)

    @Query("SELECT * FROM TRANSACTION_TABLE WHERE ID = :id")
    suspend fun getTransactionById(id: Int): TransactionEntity

    @Query("DELETE FROM TRANSACTION_TABLE WHERE id = :id")
    suspend fun deleteTransactionById(id: Int)
}