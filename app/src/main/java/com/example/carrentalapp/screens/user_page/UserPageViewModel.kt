package com.example.carrentalapp.screens.user_page

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrentalapp.api.CarService
import com.example.carrentalapp.api.UserService
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
    private val savedStateHandle: SavedStateHandle,
    private val holder: SharedStringHolder
): ViewModel() {
    val id = savedStateHandle.get<Int>("id")
    val userId: StateFlow<String?> = holder.userId

    private val _cars = MutableStateFlow<List<Car>>(emptyList())
    val carsFlow: StateFlow<List<Car>> = _cars.asStateFlow()

    var expanded by mutableStateOf(false)
        private set

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getAvailableCars()
    }

    fun popBack(){
        sendUiEvent(UiEvent.PopBackStack)
    }

    fun navigateToBooking(car: Car){ //TODO change the route to booking page
        holder.setCarToBook(car)
        sendUiEvent(UiEvent.Navigate(Routes.BOOK_CAR_PAGE))
    }

    fun toggleDropdownState(){
        expanded = !expanded
    }

    fun setExpandedToFalse(){
        expanded = false
    }

    fun navigateToActiveBookings(){
        sendUiEvent(UiEvent.Navigate(Routes.DISPLAY_USER_ACTIVE_BOOKINGS_PAGE))
        expanded = false
    }

    fun navigateToBookingsHistory(){
        sendUiEvent(UiEvent.Navigate(Routes.DISPLAY_USER_BOOKINGS_HISTORY_PAGE))
        expanded = false
    }

    private fun getAvailableCars(){
        viewModelScope.launch {
            val response = CarService.retrofitService.getAvailableCars()
            _cars.value = response
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}