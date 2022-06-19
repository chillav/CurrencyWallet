package com.krasovitova.currencywallet.transaction

enum class TransactionType(val title: String) {
    INCOME(title = "Приход"),
    EXPENDITURE(title = "Расход");

    companion object {
        fun titles() = listOf(INCOME.title, EXPENDITURE.title)
    }
}