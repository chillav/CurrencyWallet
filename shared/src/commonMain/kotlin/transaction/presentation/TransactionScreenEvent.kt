package com.krasovitova.currencywallet.transaction.presentation

sealed class TransactionScreenEvent {
    data class ValidationFailed(
        val errors: List<SaveTransactionError>
    ) : TransactionScreenEvent()

    object NavigateBack : TransactionScreenEvent()
}
