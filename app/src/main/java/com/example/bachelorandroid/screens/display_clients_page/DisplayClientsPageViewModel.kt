package com.example.bachelorandroid.screens.display_clients_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bachelorandroid.data.BankRepository
import com.example.bachelorandroid.data.Client
import com.example.bachelorandroid.util.Routes
import com.example.bachelorandroid.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayClientsPageViewModel @Inject constructor(
    private val repository: BankRepository
): ViewModel() {
    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var clients = mutableListOf<Client>()
        private set

    init {
        viewModelScope.launch {
            clients = repository.getAllClients().toMutableList()
        }
    }

    fun popBack(){
        sendUiEvent(UiEvent.PopBackStack)
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}