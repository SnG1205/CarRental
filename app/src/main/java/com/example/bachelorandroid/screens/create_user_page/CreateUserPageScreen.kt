package com.example.bachelorandroid.screens.create_user_page

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bachelorandroid.util.UiEvent

@Composable
fun CreateUserPageScreen(
    onPopBackStack: () -> Unit,
    viewModel: CreateUserPageViewModel = hiltViewModel()
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
                else -> Unit
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
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            viewModel.popBack()
                        })

                    Spacer(modifier = Modifier.width(20.dp))

                    Text(text = "Client creation", style = MaterialTheme.typography.h5, color = Color.White)
                }
            }
        }
    ){
        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize()
        ) {
            Text(text = "Write full name of the client below")
            TextField(
                value = viewModel.firstName,
                placeholder = {
                    Text(text = "Enter first name of the client")
                },
                onValueChange = {
                    viewModel.changeFirstName(it)
                }
            )
            TextField(
                value = viewModel.lastName,
                placeholder = {
                    Text(text = "Enter last name of the client")
                },
                onValueChange = {
                    viewModel.changeLastName(it)
                }
            )
            TextField(
                value = viewModel.address,
                placeholder = {
                    Text(text = "Enter address of the client")
                },
                onValueChange = {
                    viewModel.changeAddress(it)
                }
            )
            Button(onClick = { viewModel.createUser() }) {
                Text(text = "Create client")
            }
        }
    }
}