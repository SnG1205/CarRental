package com.example.bachelorandroid.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BankDAO {
    @Insert
    suspend fun insertClient(client: Client)

    @Delete
    suspend fun deleteClient(client: Client)

    @Insert
    suspend fun insertStock(stock: Stock)

    @Update
    suspend fun updateStock(stock: Stock) //Todo check update annotation usage

    @Delete
    suspend fun deleteStock(stock: Stock)

    @Insert
    suspend fun insertBalance(balance: Balance)

    /*@Update
    suspend fun updateBalance(balance: Balance)*/

    @Query("SELECT * FROM clients")
    suspend fun getAllClients(): List<Client> //TODO Maybe return Flow and get rid of suspend

    @Query("SELECT * FROM clients WHERE firstName = :firstName AND lastName = :lastName") //Todo maybe change to camel case (Search in room doc ??)
    suspend fun getClientByFullName(firstName: String, lastName: String): Client //Todo mb return as Flow<Client>

    @Query("SELECT * FROM clients WHERE id = :id")
    suspend fun getClientById(id: Int): Client

    @Query("SELECT * FROM stocks")
    suspend fun getAllStocks(): List<Stock> //TODO Maybe return Flow and get rid of suspend

    @Query("SELECT * FROM stocks WHERE clientId = :clientId")
    suspend fun  getStocksByClientId(clientId: Int): List<Stock> //TODO Maybe return Flow and get rid of suspend

    @Query("SELECT * FROM stocks WHERE clientId = :clientId AND symbols = :symbols")
    suspend fun getStocksByClientIdAndSymbols(clientId: Int, symbols: String): Stock //Todo mb return as Flow<Stock>

    @Query("SELECT * FROM balance")
    suspend fun getBalance(): Balance //Todo mb returns as Flow<Balance>

    @Query("UPDATE stocks SET price = :price, amount = :amount WHERE clientId = :clientId AND symbols = :symbols")
    suspend fun updateStockById(price: Double, amount: Int, clientId: Int, symbols: String)

    @Query("UPDATE balance SET balance = :balance")
    suspend fun updateBalance(balance: Double)

    @Query("DELETE FROM stocks WHERE clientId  = :clientId AND symbols = :symbols")
    suspend fun deleteStocks(clientId: Int, symbols: String)
}