package com.krasovitova.currencywallet.utils

import java.math.BigDecimal

fun Iterable<BigDecimal>.sum(): BigDecimal {
    var sum: BigDecimal = BigDecimal.ZERO
    for (element in this) {
        sum += element
    }
    return sum
}