package com.example.carrentalapp.screens.book_car_page

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrentalapp.api.CarRentalService
import com.example.carrentalapp.data.Booking
import com.example.carrentalapp.data.BookingRequest
import com.example.carrentalapp.data.Car
import com.example.carrentalapp.screens.home_page.SharedStringHolder
import com.example.carrentalapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class BookCarPageViewModel @Inject constructor(
    holder: SharedStringHolder
): ViewModel() {
    val token: StateFlow<String?> = holder.token
    val userId: StateFlow<Long?> = holder.userId
    val carToBook: StateFlow<Car?> = holder.carToBook
    @RequiresApi(Build.VERSION_CODES.O)
    val date = LocalDate.now()

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun popBack(){
        sendUiEvent(UiEvent.PopBackStack)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun bookCar(){
        viewModelScope.launch {
            val date1 = LocalDate.now()
            Log.d("Date", date1.toString())
            CarRentalService.retrofitService.createBooking(
                "Bearer "+token.value!!,
                BookingRequest(
                    userId.value!!,
                    carToBook.value?.id!!,
                    LocalDate.now().toString(),
                    LocalDate.now().plusDays(2).toString() //Todo change this to date picker
                    )
            )
            sendUiEvent(UiEvent.ShowSnackbar(
                message = "Car was successfully rented"
            ))
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}