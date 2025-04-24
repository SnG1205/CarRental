package com.example.carrentalapp.screens.user_page

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrentalapp.util.Routes
import com.example.carrentalapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val id = savedStateHandle.get<Int>("id")

    var firstName by mutableStateOf("") //Todo try to initialize as null to then set a Client object inside init
        private set

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun popBack(){
        sendUiEvent(UiEvent.PopBackStack)
    }

    fun navigateToBuyShares(){
        sendUiEvent(UiEvent.Navigate(Routes.BUY_STOCK_PAGE + "?clientId=${id}"))
    }

    fun navigateToShowDepot(){
        sendUiEvent(UiEvent.Navigate(Routes.DEPOT_PAGE + "?clientId=${id}"))
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}