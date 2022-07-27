package com.krasovitova.currencywallet.transaction

enum class TransactionMenu(val title: String) {
    EDIT(title = "Редактировать"),
    DELETE(title = "Удалить");

    companion object {
        fun titles() = listOf(EDIT.title, DELETE.title)

        fun getMenuItemByTitle(title: String): TransactionMenu {
            return when (title) {
                EDIT.title -> EDIT
                DELETE.title -> DELETE
                else -> throw IllegalArgumentException("Menu item with $title not found")
            }
        }
    }
}