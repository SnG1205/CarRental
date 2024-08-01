package com.example.bachelorandroid.screens.buy_stock_page

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bachelorandroid.api.StockApi
import com.example.bachelorandroid.api.StockService
import com.example.bachelorandroid.data.BankRepository
import com.example.bachelorandroid.data.Stock
import com.example.bachelorandroid.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class BuyStockPageViewModel @Inject constructor(
    private val repository: BankRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val id = savedStateHandle.get<Int>("clientId")

    var stockSymbols by mutableStateOf("")
        private set
    var amount by mutableStateOf("")
        private set
    var text by mutableStateOf("")
        private set
    var apiResponse by mutableStateOf<StockApi?>(null)
        private set

    fun popBack() {
        sendUiEvent(UiEvent.PopBackStack)
    }

    fun changeStockSymbols(stockSymbols: String) {
        this.stockSymbols = stockSymbols
    }

    fun changeAmount(amount: String) {
        this.amount = amount
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun findStock() {
        viewModelScope.launch {
            try {
                val date = LocalDate.now().minusDays(2)
                apiResponse = StockService.retrofitService.fetchStockPrices(
                    stockSymbols,
                    date.toString()
                ) //Todo change data to now
                text =
                    "Price per share of the ${apiResponse!!.ticker} stock is: ${apiResponse!!.results[0].o}"
            } catch (e: Exception) {
                sendUiEvent(
                    UiEvent.ShowSnackbar(
                        message = "No stocks were found with given symbols"
                    )
                )
                apiResponse = null
                text = ""
            }
            finally {
                stockSymbols = ""
            }
        }
    }

    fun buyShares() {
        //Todo check that api response is not null
        viewModelScope.launch {
            val balance = repository.getBalance().balance
            try {
                val price = apiResponse!!.results[0].o
                if (balance < amount.toDouble() * price) {
                    sendUiEvent(
                        UiEvent.ShowSnackbar(
                            message = "Not enough balance to buy this amount of shares"
                        )
                    )
                } else {
                    val desiredStock =
                        repository.getStocksByClientIdAndSymbols(id!!, apiResponse!!.ticker)
                    if (desiredStock == null) {
                        repository.insertStock(
                            Stock(
                                clientId = id,
                                symbols = apiResponse!!.ticker,
                                amount = amount.toInt(),
                                price = price
                            )
                        )
                        sendUiEvent(
                            UiEvent.ShowSnackbar(
                                message = "Shares were successfully bought"
                            )
                        )
                    } else {
                        repository.updateStockById(
                            amount = amount.toInt() + desiredStock.amount,
                            clientId = id,
                            symbols = apiResponse!!.ticker,
                            price = price
                        )
                        sendUiEvent(
                            UiEvent.ShowSnackbar(
                                message = "Shares were successfully bought"
                            )
                        )
                    }
                    repository.updateBalance(balance - (amount.toDouble() * price))
                    apiResponse = null
                }
            } catch (e: Exception) {
                sendUiEvent(
                    UiEvent.ShowSnackbar(
                        message = "You must search for stocks first"
                    )
                )
            }

            /*try {
                val some = repository.getStocksByClientIdAndSymbols(id!!, apiResponse!!.ticker)
                if (some == null) {
                    repository.insertStock(
                        Stock(
                            clientId = id,
                            symbols = apiResponse!!.ticker,
                            amount = amount.toInt(),
                            price = apiResponse!!.results[0].o
                        )
                    )
                }
            } catch (e: Exception) {
                /*repository.insertStock(
                    Stock(
                        1,
                        id!!,
                        stockSymbols,
                        apiResponse!!.results[0].o,
                        amount.toInt()
                    )
                )*/
                sendUiEvent(
                    UiEvent.ShowSnackbar(
                        message = "Not in db"
                    )
                )
            }*/
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}