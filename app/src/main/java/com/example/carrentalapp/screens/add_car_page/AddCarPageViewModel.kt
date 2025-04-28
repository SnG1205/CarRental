package com.example.carrentalapp.screens.add_car_page

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrentalapp.api.CarService
import com.example.carrentalapp.data.Car
import com.example.carrentalapp.screens.home_page.SharedStringHolder
import com.example.carrentalapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCarPageViewModel @Inject constructor(
    holder: SharedStringHolder
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var brand by mutableStateOf("")
        private set
    var model by mutableStateOf("")
        private set
    var licensePlate by mutableStateOf("")
        private set
    var manufactureYear by mutableStateOf("")
        private set
    var pricePerDayUsd by mutableStateOf("")
        private set

    fun popBack() {
        sendUiEvent(UiEvent.PopBackStack)
    }

    fun changeBrand(brand: String) {
        this.brand = brand
    }

    fun changeModel(model: String) {
        this.model = model
    }

    fun changeLicensePlate(licensePlate: String) {
        this.licensePlate = licensePlate
    }

    fun changeManufactureYear(manufactureYear: String) {
        this.manufactureYear = manufactureYear
    }

    fun changePricePerDayUsd(pricePerYearUsd: String) {
        this.pricePerDayUsd = pricePerYearUsd
    }

    fun addCar() {
        if (!checkIfFieldsAreEmpty()) {
            try {
                val carManufactureYear = manufactureYear.toInt()
                val carPricePerUsd = pricePerDayUsd.toDouble()
                viewModelScope.launch {
                    CarService.retrofitService.createCar(
                        Car(
                            null,
                            brand,
                            model,
                            carManufactureYear,
                            licensePlate,
                            carPricePerUsd,
                            null
                        )
                    )
                    sendUiEvent(
                        UiEvent.ShowSnackbar(
                            message = "Car was added successfully"
                        )
                    )
                    clearAllFields()
                }
            } catch (e: Exception) {
                sendUiEvent(
                    UiEvent.ShowSnackbar(
                        message = "Was not able to add a car"
                    )
                )
            }
        }
    }

    private fun checkIfFieldsAreEmpty(): Boolean {
        if (brand == "" || model == "" || licensePlate == "" || manufactureYear == "" || pricePerDayUsd == "") {
            sendUiEvent(
                UiEvent.ShowSnackbar(
                    message = "Fields can not be empty"
                )
            )
            return true
        }
        return false
    }

    private fun clearAllFields(){
        brand = ""
        model = ""
        licensePlate = ""
        manufactureYear = ""
        pricePerDayUsd = ""
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}