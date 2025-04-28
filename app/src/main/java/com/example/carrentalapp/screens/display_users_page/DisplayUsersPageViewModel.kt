package com.example.carrentalapp.screens.display_users_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carrentalapp.api.UserService
import com.example.carrentalapp.data.User
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
class DisplayUsersPageViewModel @Inject constructor(
): ViewModel() {
    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val usersFlow: StateFlow<List<User>> = _users.asStateFlow()

    var users = mutableListOf<User>() //Todo mb change to normal List
        private set

    fun popBack(){
        sendUiEvent(UiEvent.PopBackStack)
    }

    /*init {
        viewModelScope.launch {
            val response = CarRentalService.retrofitService.getAllUsers()
            _users.value = response
            //users = CarRentalService.retrofitService.getAllUsers().toMutableList()
        }
    }*/

    fun fetch(){
        viewModelScope.launch {
            val response = UserService.retrofitService.getAllUsers()
            _users.value = response
            //users = CarRentalService.retrofitService.getAllUsers().toMutableList()
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}