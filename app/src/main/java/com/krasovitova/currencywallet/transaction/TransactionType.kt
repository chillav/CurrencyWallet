package com.krasovitova.currencywallet.transaction

enum class TransactionType(val title: String) {
    INCOME(title = "Приход"),
    EXPENDITURE(title = "Расход");

    companion object {
        fun titles() = listOf(INCOME.title, EXPENDITURE.title)

        fun getTypeByTitle(text: String): TransactionType {
            return when (text) {
                INCOME.title -> INCOME
                EXPENDITURE.title -> EXPENDITURE
                else -> throw IllegalArgumentException("Type $text not found")
            }
        }
    }
}