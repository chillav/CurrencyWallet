package com.krasovitova.currencywallet.transaction.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krasovitova.currencywallet.transaction.domain.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val transactionRepository: TransactionRepository
) : ViewModel() {
    private val _state = MutableStateFlow(emptyTransactionState())
    private val _event = MutableSharedFlow<TransactionScreenEvent>()

    val state = _state.asStateFlow()
    val event = _event.asSharedFlow()
    val currencies = listOf("RUB", "EUR", "USD") //TODO вынести в enum
    val transactionTypes = TransactionType.titles()

    fun saveTransaction() {
        viewModelScope.launch(Dispatchers.IO) {
            val errors = getTransactionErrors()

            if (errors.isEmpty()) {
                val transactionForSave = TransactionUi(
                    id = state.value.id.takeIf { it != 0 },
                    sum = state.value.sum,
                    currency = state.value.currency,
                    date = state.value.date,
                    type = state.value.type
                )
                transactionRepository.saveTransaction(transactionForSave)
                _event.emit(TransactionScreenEvent.NavigateBack)
            } else {
                _event.emit(TransactionScreenEvent.ValidationFailed(errors))
            }
        }
    }

    fun updateSum(sum: String) { _state.update { it.copy(sum = sum) } }
    fun updateCurrency(currency: String) { _state.update { it.copy(currency = currency) } }
    fun updateDate(date: String) { _state.update { it.copy(date = date) } }
    fun updateType(type: String) { _state.update { it.copy(type = type) } }

    private fun getTransactionErrors(): List<SaveTransactionError> {
        val errors = mutableListOf<SaveTransactionError>()
        if (_state.value.sum.isBlank()) errors.add(SaveTransactionError.EMPTY_SUM)
        if (_state.value.date.isBlank()) errors.add(SaveTransactionError.EMPTY_DATE)
        if (_state.value.currency.isBlank()) errors.add(SaveTransactionError.EMPTY_CURRENCY)
        if (_state.value.type.isBlank()) errors.add(SaveTransactionError.EMPTY_TYPE)
        return errors
    }

    suspend fun fetchTransaction(id: Int) {
        val result = transactionRepository.getTransactionById(id)
        _state.update {
            it.copy(
                id = result.id ?: 0,
                sum = result.sum,
                type = result.type,
                date = result.date,
                currency = result.currency
            )
        }
    }

    private fun emptyTransactionState() = TransactionState(
        id = 0,
        sum = "",
        type = "",
        date = "",
        currency = ""
    )
}
