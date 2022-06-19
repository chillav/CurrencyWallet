package com.krasovitova.currencywallet.currency

enum class Currency(val title: String) {
    DOLLAR(title = "Dollar $"),
    LARI(title = "Lari ₾"),
    RUB(title = "Rub ₽"),
    EURO(title = "Euro €");

    companion object {
        fun titles() = listOf(DOLLAR.title, LARI.title, RUB.title, EURO.title)
    }
}