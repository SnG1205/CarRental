package com.example.bachelorandroid.screens.create_user_page

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bachelorandroid.data.BankRepository
import com.example.bachelorandroid.data.Client
import com.example.bachelorandroid.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateUserPageViewModel @Inject constructor(
    private val repository: BankRepository
): ViewModel() {
    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var firstName by mutableStateOf("")
        private set
    var lastName by mutableStateOf("")
        private set
    var address by mutableStateOf("")
        private set

    fun popBack(){
        sendUiEvent(UiEvent.PopBackStack)
    }

    fun changeFirstName(firstName: String){
        this.firstName = firstName
    }

    fun changeLastName(lastName: String){
        this.lastName = lastName
    }

    fun changeAddress(address: String){
        this.address = address
    }

    fun createUser(){
        try {
            viewModelScope.launch {
                repository.insertClient(Client(
                    firstName = firstName,
                    lastName = lastName,
                    address = address,
                    isEmployee = false
                ))
                sendUiEvent(UiEvent.ShowSnackbar(
                    message = "User was successfully created"
                ))
            }
        }
        catch (e: Exception){
            //Todo something here(check textField for input), or delete if no error is thrown.
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}