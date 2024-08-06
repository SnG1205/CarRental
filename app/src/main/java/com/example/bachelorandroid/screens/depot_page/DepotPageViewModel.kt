package com.example.bachelorandroid.screens.depot_page

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bachelorandroid.data.BankRepository
import com.example.bachelorandroid.data.Stock
import com.example.bachelorandroid.util.Routes
import com.example.bachelorandroid.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DepotPageViewModel @Inject constructor(
    private val repository: BankRepository,
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

    init {
        viewModelScope.launch {
            clientName = repository.getClientById(id!!).firstName
            stocks = repository.getStocksByClientId(id).toMutableList()
            stocks.forEach { totalAmount += it.amount.toDouble() * it.price }
        }
    }

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