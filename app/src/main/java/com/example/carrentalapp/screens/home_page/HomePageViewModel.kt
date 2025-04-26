package com.example.carrentalapp.screens.home_page

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrentalapp.api.CarRentalService
import com.example.carrentalapp.data.LoginRequest
import com.example.carrentalapp.util.Routes
import com.example.carrentalapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val holder: SharedStringHolder
) : ViewModel() {
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var visible by mutableStateOf(true)
        private set
    var token by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun changeEmail(username: String) {
        this.email = username
    }

    fun changePassword(password: String) {
        this.password = password
    }

    fun changeVisibility() {
        visible = !visible
    }

    fun navigateToLogin() {//Todo add validation of credentials via extra method
        var isAdmin: Boolean? = true

        if (isAdmin == null) {
            sendUiEvent(
                UiEvent.ShowSnackbar(
                    message = "User with given credentials wasn`t found"
                )
            )
        } /*else if (isAdmin) {
            viewModelScope.launch { //Todo just a reminder that here is some crazy shit with FlowStates
                val response = CarRentalService.retrofitService.login(
                    LoginRequest(email, password)
                ).token
                holder.setToken(response)
                //Log.d("SharedHolder", response)
                /*token = CarRentalService.retrofitService.login(
                    LoginRequest(email, password)
                ).token*/
            }
            sendUiEvent(UiEvent.Navigate(Routes.ADMIN_PAGE))
            clearAllFields()

        }*/ else {
            viewModelScope.launch {
                val response = CarRentalService.retrofitService.login(
                    LoginRequest(email, password)
                ).token
                holder.setToken(response)
                Log.d("SharedHolder", response)
                sendUiEvent(UiEvent.Navigate(Routes.USER_PAGE))
                clearAllFields()
            }

        }
    }

    fun navigateToRegister() {
        sendUiEvent(UiEvent.Navigate(Routes.REGISTER_PAGE))
        clearAllFields()
    }

    private fun clearAllFields(){
        email = ""
        password = ""
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}

@ActivityScoped
class SharedStringHolder {
    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token.asStateFlow()

    fun setToken(newToken: String) {
        _token.value = newToken
    }
}