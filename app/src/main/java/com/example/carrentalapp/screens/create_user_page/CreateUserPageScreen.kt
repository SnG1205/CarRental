package com.example.carrentalapp.screens.create_user_page

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.carrentalapp.util.UiEvent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText

@Composable
fun CreateUserPageScreen(
    onPopBackStack: () -> Unit,
    viewModel: CreateUserPageViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
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
                backgroundColor = Color(0xFF2962FF), //Todo change color
            ) {
                Row(
                    modifier = Modifier,
                    Arrangement.Absolute.Left,
                    Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow Back",
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            viewModel.popBack()
                        })

                    Spacer(modifier = Modifier.width(20.dp))

                    Text(
                        text = "User creation",
                        style = MaterialTheme.typography.h5,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize(),
            Arrangement.Top,
            Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(0.dp, 5.dp, 0.dp, 10.dp),
                text = "Write information about the user below",
                fontSize = 20.sp
            )
            TextField(
                modifier = Modifier
                    .padding(0.dp, 15.dp, 0.dp, 20.dp)
                    .border(0.8.dp, Color.Black, RoundedCornerShape(5)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                value = viewModel.username,
                placeholder = {
                    Text(text = "Enter Your username")
                },
                onValueChange = {
                    viewModel.changeFirstName(it)
                }
            )
            TextField(
                modifier = Modifier
                    .padding(0.dp, 15.dp, 0.dp, 20.dp)
                    .border(0.8.dp, Color.Black, RoundedCornerShape(5)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                value = viewModel.email,
                placeholder = {
                    Text(text = "Enter Your email address")
                },
                onValueChange = {
                    viewModel.changeEmail(it)
                }
            )
            TextField(
                modifier = Modifier
                    .padding(0.dp, 15.dp, 0.dp, 30.dp)
                    .border(0.8.dp, Color.Black, RoundedCornerShape(5)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                value = viewModel.password,
                visualTransformation = VisualTransformation {
                    TransformedText(
                        AnnotatedString("*".repeat(it.length)),
                        OffsetMapping.Identity)
                },
                placeholder = {
                    Text(text = "Enter Your password")
                },
                onValueChange = {
                    viewModel.changePassword(it)
                }
            )
            Button(
                modifier = Modifier
                    .width(170.dp)
                    .height(70.dp)
                    .padding(0.dp, 0.dp, 0.dp, 25.dp),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFF4511E),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(50),
                onClick = { viewModel.createUser() }
            ) {
                Text(text = "Create user", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}