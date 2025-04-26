package com.example.carrentalapp.di

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.carrentalapp.screens.home_page.SharedStringHolder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedStringHolder(): SharedStringHolder{
        return SharedStringHolder()
    }
    /*
    @Provides
    @Singleton
    fun provideBankDatabase(app: Application): BankDatabase{
        return Room.databaseBuilder(
            app,
            BankDatabase::class.java,
            "bank_db"
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideBankRepository(db: BankDatabase): BankRepository{
        return BankRepository(db.dao)
    }

    object MIGRATION: Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("DROP TABLE stocks")
            db.execSQL("CREATE TABLE stocks (id INTEGER PRIMARY KEY, clientId INTEGER NOT NULL, symbols TEXT NOT NULL, price REAL NOT NULL, amount INTEGER NOT NULL);")
        }
    }*/
}