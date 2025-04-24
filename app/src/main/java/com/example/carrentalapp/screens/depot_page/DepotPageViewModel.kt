package com.example.carrentalapp.screens.depot_page

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrentalapp.data.Stock
import com.example.carrentalapp.util.Routes
import com.example.carrentalapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DepotPageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
):ViewModel() {
    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val id = savedStateHandle.get<Int>("clientId")

    var clientName by mutableStateOf("")
        private set
    var stocks = mutableListOf<Stock>()
    var symbols by mutableStateOf("")
        private set
    var totalAmount by mutableDoubleStateOf(0.0)
        private set

    fun popBack(){
        sendUiEvent(UiEvent.PopBackStack)
    }

    fun navigateToSell(itemSymbols : String){
        sendUiEvent(UiEvent.Navigate(Routes.SELL_STOCK_PAGE + "?clientId=${id}" + "?symbols=${itemSymbols}"))
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}