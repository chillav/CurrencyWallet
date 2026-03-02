package com.krasovitova.currencywallet.transaction.di

import com.krasovitova.currencywallet.transaction.data.repository.TransactionRepositoryImpl
import com.krasovitova.currencywallet.transaction.domain.TransactionRepository
import com.krasovitova.currencywallet.transaction.presentation.TransactionViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val transactionModule = module {
    single<TransactionRepository> { TransactionRepositoryImpl(get()) }
    viewModel { TransactionViewModel(get()) }
}
