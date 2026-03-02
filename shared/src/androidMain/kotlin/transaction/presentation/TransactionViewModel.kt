package com.krasovitova.currencywallet.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krasovitova.currencywallet.data.CurrencyRepository
import com.krasovitova.currencywallet.data.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val transactionRepository: TransactionRepository,
    private val currencyRepository: CurrencyRepository
) : ViewModel() {
    private val state = MutableStateFlow(emptyTransactionState())
    private val _event = MutableSharedFlow<TransactionScreenEvent>()

    val event = _event.asSharedFlow()
    val currencies: Flow<List<String>> = fetchCurrencies()
    val transactionTypes = TransactionType.titles()

    fun saveTransaction() {
        viewModelScope.launch(Dispatchers.IO) {
            val errors = getTransactionErrors()

            if (errors.isEmpty()) {
                val transactionForSave = TransactionUi(
                    id = id.value,
                    sum = sum.value.orEmpty(),
                    currency = currency.value.orEmpty(),
                    date = dateState.value.orEmpty(),
                    type = typeState.value.orEmpty()
                )
                transactionRepository.saveTransaction(transactionForSave)
                _event.emit(TransactionScreenEvent.NavigateBack)
            } else {
                _event.emit(
                    TransactionScreenEvent.ValidationFailed(errors)
                )
            }
        }
    }

    private fun fetchCurrencies(): Flow<List<String>> {
        return currencyRepository.getUserCurrencies().map {
            it.map { it.abbreviation }
        }
    }

    private fun getTransactionErrors(): List<SaveTransactionError> {
        val errors = mutableListOf<SaveTransactionError>()

        if (state.value.isNullOrBlank()) {
            errors.add(SaveTransactionError.EMPTY_SUM)
        }
        if (dateState.value.isNullOrBlank()) {
            errors.add(SaveTransactionError.EMPTY_DATE)
        }
        if (currencyState.value.isNullOrBlank()) {
            errors.add(SaveTransactionError.EMPTY_CURRENCY)
        }
        if (typeState.value.isNullOrBlank()) {
            errors.add(SaveTransactionError.EMPTY_TYPE)
        }
        return errors
    }

    suspend fun fetchTransaction(id: Int) {
        val result = transactionRepository.getTransactionById(id)
        with(result) {
            this.id?.let {
                idState.postValue(it)
            }
            sumState.postValue(sum)
            typeState.postValue(type)
            dateState.postValue(date)
            currencyState.postValue(currency)
        }
    }

    private fun emptyTransactionState() = TransactionState(
        id = 0,
        sum = EMPTY_STRING,
        type = EMPTY_STRING,
        date = EMPTY_STRING,
        currency = EMPTY_STRING
    )
}

private const val EMPTY_STRING = "" //TODO


