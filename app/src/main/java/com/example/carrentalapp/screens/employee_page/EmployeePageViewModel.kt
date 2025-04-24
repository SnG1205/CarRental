package com.example.carrentalapp.screens.employee_page

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrentalapp.data.Balance
import com.example.carrentalapp.util.Routes
import com.example.carrentalapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeePageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val id = savedStateHandle.get<Int>("id")

    var firstName by mutableStateOf("")
        private set
    var clientId by mutableStateOf("")
        private set
    var balance by mutableDoubleStateOf(0.0)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun popBack() {
        sendUiEvent(UiEvent.PopBackStack)
    }

    fun changeClientId(clientId: String) {
        this.clientId = clientId
    }

    fun createUser() {
        sendUiEvent(UiEvent.Navigate(Routes.CREATE_USER_PAGE))
        clientId = ""
    }

    fun displayClients() {
        sendUiEvent(UiEvent.Navigate(Routes.DISPLAY_CLIENTS_PAGE))
        clientId = ""
    }

    fun showDepot() { //Todo ErrorHandling of wrong IDs
        try {
            val parsedClientId = clientId.toInt()
            sendUiEvent(UiEvent.Navigate(Routes.DEPOT_PAGE + "?clientId=${parsedClientId}"))
            clientId = ""
        } catch (e: Exception) {
            sendUiEvent(
                UiEvent.ShowSnackbar(
                    message = "Client with given id was not found"
                )
            )
            clientId = ""
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}