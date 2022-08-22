package com.krasovitova.currencywallet.wallet

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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {
    val transactions: Flow<List<WalletDescriptionItems>> = fetchTransactions()
    val currencies: Flow<List<CurrencyUi>> = fetchCurrencies()

    fun deleteTransactionById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionRepository.deleteTransactionById(id)
        }
    }

    private fun fetchCurrencies(): Flow<List<CurrencyUi>> {
        return currencyRepository.getUserCurrencies().map {
            it + getAddingCurrencyTab()
        }
    }

    private fun getAddingCurrencyTab() = CurrencyUi(
        id = ADD_CURRENCY_TAB_ID,
        abbreviation = ADD_CURRENCY_ABBREVIATION,
        description = "",
        isSelected = true
    )

    private fun fetchTransactions(): Flow<List<WalletDescriptionItems>> {
        return transactionRepository.getTransactions().map {
            it.groupBy {
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
                    if (transactionUi.id == null) {
                        Timber.e(
                            """Transaction id is null. 
                            |This transaction wasn't added in wallet transaction list: 
                            |$transactionUi""".trimMargin()
                        )
                        return@forEachIndexed
                    }

                    list.add(
                        WalletDescriptionItems.Transaction(
                            id = transactionUi.id,
                            transactionName = "${transactionUi.sum} ${transactionUi.currency}",
                            type = TransactionType.getTypeByTitle(transactionUi.type)
                        )
                    )
                }

                list.add(WalletDescriptionItems.Divider)

                list
            }.flatten()
        }
    }

    private fun List<TransactionUi>.sum(): BigDecimal {
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
