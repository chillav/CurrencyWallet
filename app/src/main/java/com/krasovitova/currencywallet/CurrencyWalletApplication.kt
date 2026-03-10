package com.krasovitova.currencywallet

import android.app.Application
import com.krasovitova.currencywallet.data.database.CurrencyWalletDatabase
import com.krasovitova.currencywallet.transaction.di.transactionModule
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import javax.inject.Inject

@HiltAndroidApp
class CurrencyWalletApplication : Application() {

    @Inject lateinit var database: CurrencyWalletDatabase

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CurrencyWalletApplication)
            modules(
                transactionModule,
                module { single { database.transactionDao() } }
            )
        }
    }
}
