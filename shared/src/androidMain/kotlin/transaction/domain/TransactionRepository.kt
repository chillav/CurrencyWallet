package com.krasovitova.currencywallet.transaction.domain

import com.krasovitova.currencywallet.transaction.presentation.TransactionUi
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun saveTransaction(transactionUi: TransactionUi)
    fun getTransactions(): Flow<List<TransactionUi>>
    suspend fun getTransactionById(id: Int): TransactionUi
    suspend fun deleteTransactionById(id: Int)
}
