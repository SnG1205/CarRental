package com.example.bachelorandroid.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Client::class, Stock::class, Balance::class],
    version = 6,
    autoMigrations = [
        AutoMigration (from = 5, to = 6)
    ],
    exportSchema = true
)
abstract class BankDatabase: RoomDatabase(){
    abstract val dao: BankDAO
}