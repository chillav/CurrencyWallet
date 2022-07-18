package com.krasovitova.currencywallet.transaction

sealed class TransactionScreenSideEffects {
    data class ValidationFailed(
        val errors: List<SaveTransactionError>
    ) : TransactionScreenSideEffects()

    object NavigateBack : TransactionScreenSideEffects()
}

