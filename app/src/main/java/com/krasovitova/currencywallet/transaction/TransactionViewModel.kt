package com.krasovitova.currencywallet.transaction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krasovitova.currencywallet.data.CurrencyRepository
import com.krasovitova.currencywallet.data.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val currencyRepository: CurrencyRepository
) : ViewModel() {
    private val idState = MutableLiveData<Int>()
    private val _event = MutableSharedFlow<TransactionScreenEvent>()

    val event = _event.asSharedFlow()
    val currencies: Flow<List<String>> = fetchCurrencies()
    val transactionTypes = TransactionType.titles()
    val sumState = MutableLiveData<String>()
    val typeState = MutableLiveData<String>()
    val dateState = MutableLiveData<String>()
    val currencyState = MutableLiveData<String>()

    fun saveTransaction() {
        viewModelScope.launch(Dispatchers.IO) {
            val errors = getTransactionErrors()

            if (errors.isEmpty()) {
                val transactionForSave = TransactionUi(
                    id = idState.value,
                    sum = sumState.value.orEmpty(),
                    currency = currencyState.value.orEmpty(),
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

        if (sumState.value.isNullOrBlank()) {
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
}


