package com.example.carrentalapp.screens.buy_stock_page

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrentalapp.api.StockApi
import com.example.carrentalapp.api.StockService
import com.example.carrentalapp.data.Stock
import com.example.carrentalapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class BuyStockPageViewModel @Inject constructor(
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
                val date = LocalDate.now().minusDays(5) //Note from 06.08: for some reason stopped working if date is earlier than 3 days before actual. Either new terms of use or some problem in API.
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


    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}