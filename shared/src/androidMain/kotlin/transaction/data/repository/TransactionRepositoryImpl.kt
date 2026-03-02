package com.krasovitova.currencywallet.transaction.data.repository

import com.krasovitova.currencywallet.transaction.presentation.TransactionUi
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao
) {
    private val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

    suspend fun saveTransaction(transactionUi: TransactionUi) {
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
                Timber.e("Date parsing returns null, date set to zero")
                0L
            } else {
                parsedDate
            }

        } catch (exception: ParseException) {
            Timber.e(exception, "Date parsing failed, date set to zero")
            0L
        }
    }

    fun getTransactions(): Flow<List<TransactionUi>> {
        return transactionDao.getTransactions().map { it.mapToUi() }
    }

    private fun List<TransactionEntity>.mapToUi(): List<TransactionUi> {
        return this.map { it.mapToUi() }
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

    suspend fun getTransactionById(id: Int): TransactionUi {
        return transactionDao.getTransactionById(id).mapToUi()
    }

    suspend fun deleteTransactionById(id: Int) {
        return transactionDao.deleteTransactionById(id)
    }
}