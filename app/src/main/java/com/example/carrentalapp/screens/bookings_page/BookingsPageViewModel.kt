package com.example.carrentalapp.screens.bookings_page

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrentalapp.api.BookingService
import com.example.carrentalapp.data.Booking
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
class BookingsPageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    holder: SharedStringHolder
) : ViewModel() {
    val id = savedStateHandle.get<Int>("id")
    val userId: StateFlow<String?> = holder.userId

    private val _bookings = MutableStateFlow<List<Booking>>(emptyList())
    val bookingsFlow: StateFlow<List<Booking>> = _bookings.asStateFlow()

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getActiveBookings()
    }

    fun navigateToUserPage(){
        sendUiEvent(UiEvent.Navigate(Routes.USER_PAGE))
    }

    fun popBack(){
        sendUiEvent(UiEvent.PopBackStack)
    }

    fun returnBooking(booking: Booking){
        viewModelScope.launch {
            BookingService.retrofitService.returnBooking(
                booking.id!!
            )
        }
        //navigateToUserPage()
        sendUiEvent(UiEvent.ShowSnackbar(
            message = "Car was returned successfully"
        ))
    }

    private fun getActiveBookings(){
        viewModelScope.launch {
            val response = BookingService.retrofitService.getActiveBookingsForUser(
                userId.value!!
            )
            if (response != null) {
                _bookings.value = response
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}