package com.krasovitova.currencywallet.di

import android.content.Context
import androidx.room.Room
import com.krasovitova.currencywallet.Constants.ROOM_DATABASE_NAME
import com.krasovitova.currencywallet.sql.CurrencyDao
import com.krasovitova.currencywallet.sql.CurrencyWalletDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): CurrencyWalletDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            CurrencyWalletDatabase::class.java,
            ROOM_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideCurrencyDao(database: CurrencyWalletDatabase): CurrencyDao {
        return database.currencyDao()
    }
}