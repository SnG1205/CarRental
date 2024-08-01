package com.example.bachelorandroid.screens.home_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bachelorandroid.util.UiEvent

@Composable
fun HomePageScreen(
    onNavigate:(UiEvent.Navigate) -> Unit,
    viewModel: HomePageViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                elevation = 3.dp,
                backgroundColor = Color.Red,
            ) {
                Text(text = "Banking App", style = MaterialTheme.typography.h5, color = Color.White)
            }
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize()
        ) {
            Text(text = "Please enter Your credentials to login into Your account.")
            TextField(
                value = viewModel.firstName,
                placeholder = {
                    Text(text = "First name")
                },
                onValueChange = {
                    viewModel.changeFirstName(it)
                })
            TextField(
                value = viewModel.lastName,
                placeholder = {
                    Text(text = "Last name")
                },
                onValueChange = {
                    viewModel.changeLastName(it)
                })
            Button(onClick = { viewModel.navigateToNextScreen() }) {
                Text(text = "Press")
            }
            if (viewModel.visible){
                Text(text = viewModel.firstName)
            }
        }
    }
}