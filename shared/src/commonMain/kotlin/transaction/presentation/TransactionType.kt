package com.krasovitova.currencywallet.transaction.presentation

enum class TransactionType(val title: String) {
    INCOME(title = "ПРИХОД"),
    EXPEND(title = "РАСХОД");

    companion object {
        fun titles() = listOf(INCOME.title, EXPEND.title)

        fun getTypeByTitle(text: String): TransactionType {
            return when (text) {
                INCOME.title -> INCOME
                EXPEND.title -> EXPEND
                else -> throw IllegalArgumentException("Type $text not found")
            }
        }
    }
}
