package com.example.bachelorandroid.screens.home_page

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
class HomePageViewModel @Inject constructor(
    private val repository: BankRepository
) : ViewModel() {
    var firstName by mutableStateOf("")
        private set
    var lastName by mutableStateOf("")
        private set
    var visible by mutableStateOf(true)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            if (repository.getAllClients() == null) {
                repository.insertClient(
                    Client(
                    firstName = "Serhii",
                        lastName = "Holiev",
                        address = "Home",
                        isEmployee = true
                    )
                )
            }
        }
    }

    fun changeFirstName(firstName: String) {
        this.firstName = firstName
    }

    fun changeLastName(lastName: String) {
        this.lastName = lastName
    }

    fun changeVisibility() {
        visible = !visible
    }

    fun navigateToNextScreen() {
        viewModelScope.launch {
            try {
                val client = repository.getClientByFullName(firstName, lastName)
                if (client.isEmployee) {
                    sendUiEvent(UiEvent.Navigate(Routes.EMPLOYEE_PAGE + "?id=${client.id}"))
                    firstName = ""
                    lastName = ""
                } else {
                    sendUiEvent(UiEvent.Navigate(Routes.USER_PAGE + "?id=${client.id}"))
                    firstName = ""
                    lastName = ""
                }
            } catch (e: Exception) {
                sendUiEvent(UiEvent.ShowSnackbar(message = "User was not found"))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}