package com.krasovitova.currencywallet.wallet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krasovitova.currencywallet.currency.CurrencyUi
import com.krasovitova.currencywallet.data.CurrencyRepository
import com.krasovitova.currencywallet.data.TransactionRepository
import com.krasovitova.currencywallet.transaction.TransactionType
import com.krasovitova.currencywallet.transaction.TransactionUi
import com.krasovitova.currencywallet.utils.sum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {
    val transactions = MutableLiveData<List<WalletDescriptionItems>>()
    val currencies = MutableLiveData<List<CurrencyUi>>()

    fun initState() {
        viewModelScope.launch(Dispatchers.IO) {
            transactions.postValue(getTransactionsHistory())
            currencies.postValue(getCurrencies())
        }
    }

    private suspend fun getCurrencies(): List<CurrencyUi> {
        return getUserCurrencies() + getAddingCurrencyTab()
    }

    /**
     * Возвращает список валют, которые выбрал пользователь
     */
    private suspend fun getUserCurrencies(): List<CurrencyUi> {
        return currencyRepository.getUserCurrencies()
    }

    private fun getAddingCurrencyTab() = CurrencyUi(
        id = ADD_CURRENCY_TAB_ID,
        abbreviation = ADD_CURRENCY_ABBREVIATION,
        description = "",
        isSelected = true
    )

    private suspend fun getTransactionsHistory(): List<WalletDescriptionItems> {
        return transactionRepository.getTransactions().groupBy {
            it.date
        }.map { (date, transactions) ->
            val list = mutableListOf<WalletDescriptionItems>()
            val (incomes, expends) = transactions.partition {
                it.type == TransactionType.INCOME.title
            }
            val sum = incomes.sum() - expends.sum()

            list.add(
                WalletDescriptionItems.Title(
                    date = date,
                    sum = sum.toString()
                )
            )

            transactions.forEachIndexed { index, transactionUi ->
                list.add(
                    WalletDescriptionItems.Transaction(
                        id = index,
                        transactionName = "${transactionUi.sum} ${transactionUi.currency}",
                        type = TransactionType.getTypeByTitle(transactionUi.type)
                    )
                )
            }

            list.add(
                WalletDescriptionItems.Divider
            )

            list
        }.flatten()

    }

    fun List<TransactionUi>.sum(): BigDecimal {
        return this.mapNotNull {
            if (it.sum.isNotBlank()) {
                BigDecimal(it.sum)
            } else null
        }.sum()
    }

    companion object {
        private const val ADD_CURRENCY_ABBREVIATION = "+"
        const val ADD_CURRENCY_TAB_ID = 0
    }
}
