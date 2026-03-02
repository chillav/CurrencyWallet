package com.krasovitova.currencywallet.transaction

sealed class TransactionScreenEvent {
    data class ValidationFailed(
        val errors: List<SaveTransactionError>
    ) : TransactionScreenEvent()

    object NavigateBack : TransactionScreenEvent()
}

