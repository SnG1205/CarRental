package com.example.bachelorandroid.screens.home_page

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.material.*
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bachelorandroid.util.UiEvent

@Composable
fun HomePageScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: HomePageViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
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
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize()
        ) {
            Text(
                modifier = Modifier
                    .padding(0.dp, 20.dp, 0.dp, 10.dp),
                text = "Please enter Your credentials to login into Your account.",
                fontWeight = FontWeight.Bold
            )
            TextField(
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 20.dp)
                    .border(0.8.dp, Color.Black, RoundedCornerShape(5)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                value = viewModel.firstName,
                placeholder = {
                    Text(text = "First name")
                },
                onValueChange = {
                    viewModel.changeFirstName(it)
                })
            TextField(
                modifier = Modifier
                    .padding(0.dp, 15.dp, 0.dp, 25.dp)
                    .border(0.8.dp, Color.Black, RoundedCornerShape(5)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                value = viewModel.lastName,
                placeholder = {
                    Text(text = "Last name")
                },
                onValueChange = {
                    viewModel.changeLastName(it)
                })
            OutlinedButton(
                modifier = Modifier
                    .width(170.dp)
                    .height(50.dp),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFFFF176),
                ),
                border = BorderStroke(0.5.dp, Color.Black),
                shape = RoundedCornerShape(50),
                elevation = androidx.compose.material.ButtonDefaults.elevation(
                    defaultElevation = 8.dp
                ),
                onClick = { viewModel.navigateToNextScreen() }
            ) {
                Text(
                    text = "Login",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF512DA8)
                )
            }
            if (viewModel.visible) {
                Text(text = viewModel.firstName)
            }
        }
    }
}