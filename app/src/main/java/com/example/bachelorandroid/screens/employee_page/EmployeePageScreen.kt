package com.example.bachelorandroid.screens.employee_page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bachelorandroid.util.UiEvent

@Composable
fun EmployeePageScreen(
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: EmployeePageViewModel = hiltViewModel()
){
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                is UiEvent.Navigate -> onNavigate(event)
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                elevation = 3.dp,
                backgroundColor = Color.Cyan, //Todo change color
            ) {
                Row() {
                    Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow Back",
                        tint = Color.Yellow,
                        modifier = Modifier.clickable {
                            viewModel.popBack()
                        })

                    Spacer(modifier = Modifier.width(20.dp))

                    Text(text = "Welcome, ${viewModel.firstName}", style = MaterialTheme.typography.h5, color = Color.Yellow)
                }
            }
        }
    ){
        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize(),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            TextField(
                value = viewModel.clientId,
                placeholder = {
                    Text(text = "Enter id of the client")
                },
                onValueChange = {
                    viewModel.changeClientId(it)
                }
            )
            Button(onClick = { viewModel.buyStock() }) {
                Text(text = "Buy Stock")
            }
            Button(onClick = { viewModel.getBalance() }) {
                Text(text = "Get Balance")
            }
            Button(onClick = { viewModel.createUser() }) {
                Text(text = "Create User")
            }
            Button(onClick = { viewModel.displayClients() }) {
                Text(text = "Display Clients")
            }
            Button(onClick = { viewModel.showDepot() }) {
                Text(text = "Show Depot")
            }
        }
    }
}