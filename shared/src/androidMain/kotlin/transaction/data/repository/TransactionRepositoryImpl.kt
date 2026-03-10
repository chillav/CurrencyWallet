package com.krasovitova.currencywallet.transaction.data.repository

import android.util.Log
import com.krasovitova.currencywallet.transaction.data.database.TransactionDao
import com.krasovitova.currencywallet.transaction.data.database.TransactionEntity
import com.krasovitova.currencywallet.transaction.domain.TransactionRepository
import com.krasovitova.currencywallet.transaction.presentation.TransactionUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TransactionRepositoryImpl(
    private val transactionDao: TransactionDao
) : TransactionRepository {

    private val dateFormat = SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault())

    override suspend fun saveTransaction(transactionUi: TransactionUi) {
        val entity = TransactionEntity(
            id = transactionUi.id,
            sum = transactionUi.sum,
            currency = transactionUi.currency,
            date = parseDate(date = transactionUi.date),
            type = transactionUi.type
        )
        transactionDao.insert(entity)
    }

    private fun parseDate(date: String): Long {
        return try {
            val parsedDate = dateFormat.parse(date)?.time
            if (parsedDate == null) {
                Log.e(TAG, "Date parsing returns null, date set to zero")
                0L
            } else {
                parsedDate
            }
        } catch (exception: ParseException) {
            Log.e(TAG, "Date parsing failed, date set to zero", exception)
            0L
        }
    }

    override fun getTransactions(): Flow<List<TransactionUi>> {
        return transactionDao.getTransactions().map { list -> list.map { it.mapToUi() } }
    }

    private fun TransactionEntity.mapToUi(): TransactionUi {
        return TransactionUi(
            id = id,
            sum = sum,
            currency = currency,
            date = dateFormat.format(Date(date)),
            type = type,
        )
    }

    override suspend fun getTransactionById(id: Int): TransactionUi {
        return transactionDao.getTransactionById(id).mapToUi()
    }

    override suspend fun deleteTransactionById(id: Int) {
        return transactionDao.deleteTransactionById(id)
    }
}

private const val DATE_FORMAT_PATTERN = "dd.MM.yyyy"
private const val TAG = "TransactionRepositoryImpl"
