package com.krasovitova.currencywallet.transaction

import com.krasovitova.currencywallet.Constants.DATE_FORMAT
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao
) {
    private val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

    suspend fun saveTransaction(transactionUi: TransactionUi) {
        val entity = TransactionEntity(
            sum = transactionUi.sum,
            currency = transactionUi.currency,
            date = parseDate(date = transactionUi.date),
            type = transactionUi.type
        )
        transactionDao.insert(entity)
    }

    private fun parseDate(date: String): Long {
        return try {
            dateFormat.parse(date)?.time ?: 0L// TODO add log
        } catch (exception: ParseException) {
            0L
        }
    }

    suspend fun getTransactions(): List<TransactionUi> {
        return transactionDao.getTransactions().mapToUi()

    }

    fun List<TransactionEntity>.mapToUi(): List<TransactionUi> {
        return this.map {
            val date = Date().apply { time = it.date }
            TransactionUi(
                id = it.id,
                sum = it.sum,
                currency = it.currency,
                date = dateFormat.format(date),
                type = it.type
            )
        }
    }
}


