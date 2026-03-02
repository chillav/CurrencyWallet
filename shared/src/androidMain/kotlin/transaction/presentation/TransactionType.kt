package com.krasovitova.currencywallet.transaction

enum class TransactionType(val title: String) {
    INCOME(title = "Приход"),
    EXPEND(title = "Расход");

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