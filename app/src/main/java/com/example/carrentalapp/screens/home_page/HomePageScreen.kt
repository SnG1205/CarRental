package com.example.carrentalapp.screens.home_page

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


import androidx.hilt.navigation.compose.hiltViewModel
import com.example.carrentalapp.util.UiEvent

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
                backgroundColor = Color(0xFFD50000),
            ) {
                Text(text = "Car Rental Application", style = MaterialTheme.typography.h5, color = Color.White)
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
            Text(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 10.dp),
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
                value = viewModel.email,
                placeholder = {
                    Text(text = "Email")
                },
                onValueChange = {
                    viewModel.changeEmail(it)
                })
            TextField(
                modifier = Modifier
                    .padding(0.dp, 15.dp, 0.dp, 25.dp)
                    .border(0.8.dp, Color.Black, RoundedCornerShape(5)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                value = viewModel.password,
                placeholder = {
                    Text(text = "Password")
                },
                visualTransformation = VisualTransformation {
                    TransformedText(
                        AnnotatedString("*".repeat(it.length)),
                        OffsetMapping.Identity)
                },
                onValueChange = {
                    viewModel.changePassword(it)
                })
            Button(
                modifier = Modifier
                    .width(170.dp)
                    .height(50.dp)
                    .padding(0.dp, 0.dp, 0.dp, 5.dp),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFF3E5F5),
                    contentColor = Color(0xFF424242)
                ),
                shape = RoundedCornerShape(50),
                onClick = { viewModel.navigateToLogin() }
            ) {
                Text(
                    text = "Login",
                    fontSize = 20.sp,
                )
            }
            Text(
                modifier = Modifier
                    .padding(0.dp, 30.dp, 0.dp, 15.dp),
                text = "Don`t have an account yet? Create one below.",
                //fontWeight = FontWeight.Bold
            )
            Button(
                modifier = Modifier
                    .width(170.dp)
                    .height(50.dp)
                    .padding(0.dp, 0.dp, 0.dp, 5.dp),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFF3E5F5),
                    contentColor = Color(0xFF424242)
                ),
                shape = RoundedCornerShape(50),
                onClick = { viewModel.navigateToRegister() }
            ) {
                Text(
                    text = "Register",
                    fontSize = 20.sp,
                )
            }
            Text(text = viewModel.token)
        }
    }
}