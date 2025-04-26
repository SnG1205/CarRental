package com.example.carrentalapp.screens.bookings_history_page

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrentalapp.api.CarRentalService
import com.example.carrentalapp.data.Booking
import com.example.carrentalapp.screens.home_page.SharedStringHolder
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
class BookingsHistoryPageViewModel @Inject constructor(
    holder: SharedStringHolder
): ViewModel() {
    val token: StateFlow<String?> = holder.token
    val userId: StateFlow<Long?> = holder.userId

    private val _bookingsHistory = MutableStateFlow<List<Booking>>(emptyList())
    val bookingsHistoryFlow: StateFlow<List<Booking>> = _bookingsHistory.asStateFlow()

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getBookingHistory()
    }

    fun popBack(){
        sendUiEvent(UiEvent.PopBackStack)
    }

    private fun getBookingHistory(){
        viewModelScope.launch {
            Log.d("Token Value", token.value!!)
            val response = CarRentalService.retrofitService.getBookingsHistoryForUser(
                "Bearer "+token.value!!,
                userId.value!!
            )
            if (response != null) {
                _bookingsHistory.value = response
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}