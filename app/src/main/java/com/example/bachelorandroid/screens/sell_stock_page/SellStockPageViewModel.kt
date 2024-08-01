package com.example.bachelorandroid.screens.sell_stock_page

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bachelorandroid.data.BankRepository
import com.example.bachelorandroid.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellStockPageViewModel @Inject constructor(
    private val repository: BankRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val clientId = savedStateHandle.get<Int>("clientId")
    val symbols = savedStateHandle.get<String>("symbols")

    var amount by mutableStateOf("")
        private set

    fun popBack() {
        sendUiEvent(UiEvent.PopBackStack)
    }

    fun changeAmount(amount: String) {
        this.amount = amount
    }

    fun sellStock() {
        viewModelScope.launch {
            val stock = repository.getStocksByClientIdAndSymbols(clientId!!, symbols!!)
            try {
                val intAmount = amount.toInt()
                if (intAmount > stock.amount) {
                    sendUiEvent(
                        UiEvent.ShowSnackbar(
                            message = "You do not have enough shares to sell"
                        )
                    )
                } else if (intAmount == stock.amount) {
                    repository.deleteStocks(clientId, symbols)
                    val currentBalance = repository.getBalance().balance
                    repository.updateBalance(currentBalance + stock.price * amount.toDouble())
                    sendUiEvent(
                        UiEvent.ShowSnackbar(
                            message = "Shares were successfully sold"
                        )
                    )
                } else {
                    repository.updateStockById(
                        price = stock.price,
                        amount = stock.amount - intAmount,
                        clientId = clientId,
                        symbols = symbols
                    )
                    val currentBalance = repository.getBalance().balance
                    repository.updateBalance(currentBalance + stock.price * amount.toDouble())
                    sendUiEvent(
                        UiEvent.ShowSnackbar(
                            message = "Shares were successfully sold"
                        )
                    )
                }
            } catch (e: Exception) {
                sendUiEvent(
                    UiEvent.ShowSnackbar(
                        message = "Amount must be a number"
                    )
                )
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}