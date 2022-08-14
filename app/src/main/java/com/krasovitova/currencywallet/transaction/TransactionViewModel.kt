package com.krasovitova.currencywallet.transaction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.krasovitova.currencywallet.currency.CurrencyUi
import com.krasovitova.currencywallet.data.CurrencyRepository
import com.krasovitova.currencywallet.data.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val currencyRepository: CurrencyRepository
) : ViewModel() {
    private val currencies = MutableLiveData<List<CurrencyUi>>()
    val cachedTransaction = MutableLiveData<TransactionUi>()
    val transactionTypes = TransactionType.titles()
    val sideEffect = Channel<TransactionScreenSideEffects>()

    val abbreviationsCurrencies by lazy {
        currencies.map { list ->
            list.map { it.abbreviation }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            currencies.postValue(getCurrencies())
        }
    }

    private suspend fun getCurrencies(): List<CurrencyUi> {
        return currencyRepository.getUserCurrencies()
    }

    fun saveTransaction(transactionUi: TransactionUi) {
        viewModelScope.launch(Dispatchers.IO) {
            val errors = getTransactionErrors(transactionUi)

            if (errors.isEmpty()) {
                val transactionForSave = transactionUi.copy(
                    id = cachedTransaction.value?.id
                )
                transactionRepository.saveTransaction(transactionForSave)
                sideEffect.send(TransactionScreenSideEffects.NavigateBack)
            } else {
                sideEffect.send(
                    TransactionScreenSideEffects.ValidationFailed(errors)
                )
            }
        }
    }

    private fun getTransactionErrors(transaction: TransactionUi): List<SaveTransactionError> {
        val errors = mutableListOf<SaveTransactionError>()

        if (transaction.sum.isBlank()) {
            errors.add(SaveTransactionError.EMPTY_SUM)
        }
        if (transaction.date.isBlank()) {
            errors.add(SaveTransactionError.EMPTY_DATE)
        }
        if (transaction.currency.isBlank()) {
            errors.add(SaveTransactionError.EMPTY_CURRENCY)
        }
        if (transaction.type.isBlank()) {
            errors.add(SaveTransactionError.EMPTY_TYPE)
        }
        return errors
    }

    fun fetchTransaction(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = transactionRepository.getTransactionById(id)
            cachedTransaction.postValue(result)
        }
    }
}


