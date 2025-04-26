package com.example.carrentalapp.screens.create_user_page

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrentalapp.api.CarRentalService
import com.example.carrentalapp.data.User
import com.example.carrentalapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateUserPageViewModel @Inject constructor(
): ViewModel() {
    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var username by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    fun popBack(){
        sendUiEvent(UiEvent.PopBackStack)
    }

    fun changeFirstName(firstName: String){
        this.username = firstName
    }

    fun changeEmail(email: String){
        this.email = email
    }

    fun changePassword(password: String){
        this.password = password
    }

    fun createUser(){
        if(!checkIfFieldsAreEmpty()){
            viewModelScope.launch {
                try {
                    CarRentalService.retrofitService.createUser(
                        User(username = username, email = email, password = password, id = null)
                    )
                    sendUiEvent(UiEvent.ShowSnackbar(
                        message = "User was successfully created"
                    ))
                    username = ""
                    email = ""
                    password = ""
                }
                catch (e: Exception){
                    sendUiEvent(UiEvent.ShowSnackbar(
                        message = "Was not able to create a user"
                    ))
                }
            }
        }
    }

    private fun checkIfFieldsAreEmpty() : Boolean{
        if (username == "" || email == "" || password == "" ){
            sendUiEvent(UiEvent.ShowSnackbar(
                message = "Fields can not be empty"
            ))
            return true
        }
        return false
    }


    /*fun createUser(){
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
    }*/

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}