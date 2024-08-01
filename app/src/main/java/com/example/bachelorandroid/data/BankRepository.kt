package com.example.bachelorandroid.data

import kotlinx.coroutines.flow.Flow

class BankRepository(
    private val dao: BankDAO
) {
    suspend fun insertClient(client: Client){
        dao.insertClient(client)
    }

    suspend fun deleteClient(client: Client){
        dao.deleteClient(client)
    }

    suspend fun insertStock(stock: Stock){
        dao.insertStock(stock)
    }

    suspend fun updateStock(stock: Stock){
        dao.updateStock(stock)
    }

    suspend fun deleteStock(stock: Stock){
        dao.deleteStock(stock)
    }

    suspend fun insertBalance(balance: Balance){
        dao.insertBalance(balance)
    }

    /*suspend fun updateBalance(balance: Balance){
        dao.updateBalance(balance)
    }*/

    suspend fun getAllClients(): List<Client>{
        return dao.getAllClients()
    }

    suspend fun getClientByFullName(firstName: String, lastName: String): Client { //Todo mb return as Flow<Client>
        return dao.getClientByFullName(firstName, lastName)
    }

    suspend fun getClientById(id: Int): Client{
        return dao.getClientById(id)
    }

    suspend fun getAllStocks(): List<Stock>{
        return dao.getAllStocks()
    }

    suspend fun getStocksByClientId(clientId: Int): List<Stock>{
        return dao.getStocksByClientId(clientId)
    }

    suspend fun getStocksByClientIdAndSymbols(clientId: Int, symbols: String): Stock{
        return dao.getStocksByClientIdAndSymbols(clientId, symbols)
    }

    suspend fun getBalance(): Balance{
        return dao.getBalance()
    }

    suspend fun updateStockById(price: Double, amount: Int, clientId: Int, symbols: String){
        dao.updateStockById(price, amount, clientId, symbols)
    }

    suspend fun updateBalance(balance: Double){
        dao.updateBalance(balance)
    }

    suspend fun deleteStocks(clientId: Int, symbols: String){
        dao.deleteStocks(clientId, symbols)
    }
}