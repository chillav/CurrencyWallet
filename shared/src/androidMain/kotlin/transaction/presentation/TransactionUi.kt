package com.krasovitova.currencywallet.transaction

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionUi(
    val id: Int? = null,
    val sum: String,
    val currency: String,
    val date: String,
    val type: String
) : Parcelable