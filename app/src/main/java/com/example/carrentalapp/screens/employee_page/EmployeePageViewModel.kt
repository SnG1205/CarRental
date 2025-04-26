package com.example.carrentalapp.screens.employee_page

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrentalapp.screens.home_page.SharedStringHolder
import com.example.carrentalapp.util.Routes
import com.example.carrentalapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeePageViewModel @Inject constructor(
     savedStateHandle: SavedStateHandle,
     holder: SharedStringHolder
) : ViewModel() {
    val id = savedStateHandle.get<Int>("id")

    val token: StateFlow<String?> = holder.token

    var firstName by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun popBack() {
        sendUiEvent(UiEvent.PopBackStack)
    }

    fun createUser() {
        sendUiEvent(UiEvent.Navigate(Routes.REGISTER_PAGE))
    }

    fun addCar(){
        sendUiEvent(UiEvent.Navigate(Routes.ADD_CAR_PAGE))
    }

    fun displayUsers() {
        sendUiEvent(UiEvent.Navigate(Routes.DISPLAY_USERS_PAGE))
    }

    fun displayCars() {
       sendUiEvent(UiEvent.Navigate(Routes.DISPLAY_ALL_CARS_PAGE))
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}