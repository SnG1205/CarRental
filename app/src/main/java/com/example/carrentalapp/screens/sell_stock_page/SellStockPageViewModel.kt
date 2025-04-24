package com.example.carrentalapp.screens.sell_stock_page

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrentalapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellStockPageViewModel @Inject constructor(
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

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}