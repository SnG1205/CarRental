package com.example.carrentalapp.screens.user_page

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrentalapp.api.CarRentalService
import com.example.carrentalapp.data.Car
import com.example.carrentalapp.screens.home_page.SharedStringHolder
import com.example.carrentalapp.util.Routes
import com.example.carrentalapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    holder: SharedStringHolder
): ViewModel() {
    val id = savedStateHandle.get<Int>("id")
    val token: StateFlow<String?> = holder.token

    private val _cars = MutableStateFlow<List<Car>>(emptyList())
    val carsFlow: StateFlow<List<Car>> = _cars.asStateFlow()

    var firstName by mutableStateOf("") //Todo try to initialize as null to then set a Client object inside init
        private set

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getAvailableCars()
    }

    fun popBack(){
        sendUiEvent(UiEvent.PopBackStack)
    }

    fun navigateToBooking(){ //TODO change the route to booking page
        sendUiEvent(UiEvent.Navigate(Routes.REGISTER_PAGE))
    }

    private fun getAvailableCars(){
        viewModelScope.launch {
            Log.d("Token Value", token.value!!)
            val response = CarRentalService.retrofitService.getAvailableCars(
                "Bearer "+token.value!!
            )
            _cars.value = response
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}