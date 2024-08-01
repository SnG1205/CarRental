package com.example.bachelorandroid.screens.employee_page

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bachelorandroid.data.Balance
import com.example.bachelorandroid.data.BankRepository
import com.example.bachelorandroid.util.Routes
import com.example.bachelorandroid.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeePageViewModel @Inject constructor(
    private val repository: BankRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val id = savedStateHandle.get<Int>("id")

    var firstName by mutableStateOf("")
        private set
    var clientId by mutableStateOf("")
        private set
    var balance by mutableDoubleStateOf(0.0)
        private set

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            firstName = repository.getClientById(id!!).firstName
            try{
                balance = repository.getBalance().balance
            }
            catch (e: Exception){
                repository.insertBalance(Balance(1000000.0))
                balance = repository.getBalance().balance
            }
        }
    }

    fun popBack(){
        sendUiEvent(UiEvent.PopBackStack)
    }

    fun changeClientId(clientId: String){
        this.clientId = clientId
    }

    fun buyStock(){
        try{
            val parsedClientId = clientId.toInt()
            viewModelScope.launch {
                repository.getClientById(parsedClientId)
            }
            sendUiEvent(UiEvent.Navigate(Routes.BUY_STOCK_PAGE + "?clientId=${clientId}"))
            clientId = ""
        }
        catch (e: Exception){
            sendUiEvent(UiEvent.ShowSnackbar(
                message = "Client with given id was not found"
            ))
        }
    }

    fun getBalance(){
        viewModelScope.launch {
            balance = repository.getBalance().balance
            sendUiEvent(UiEvent.ShowSnackbar(
                message = "Current balance is: ${String.format("%.2f", balance)}"
            ))
        }
    }

    fun createUser(){
        sendUiEvent(UiEvent.Navigate(Routes.CREATE_USER_PAGE))
        clientId = ""
    }

    fun displayClients(){
        sendUiEvent(UiEvent.Navigate(Routes.DISPLAY_CLIENTS_PAGE))
        clientId = ""
    }

    fun showDepot(){
        try{
            val parsedClientId = clientId.toInt()
            viewModelScope.launch {
                repository.getClientById(parsedClientId)
            }
            sendUiEvent(UiEvent.Navigate(Routes.DEPOT_PAGE + "?clientId=${parsedClientId}"))
            clientId = ""
        }
        catch (e: Exception){
            sendUiEvent(UiEvent.ShowSnackbar(
                message = "Client with given id was not found"
            ))
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}