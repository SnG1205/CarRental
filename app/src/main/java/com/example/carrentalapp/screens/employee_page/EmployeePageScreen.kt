package com.example.carrentalapp.screens.employee_page

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.carrentalapp.util.UiEvent

@Composable
fun EmployeePageScreen(
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: EmployeePageViewModel = hiltViewModel()
){
    val scaffoldState = rememberScaffoldState()
    val token by viewModel.token.collectAsState()

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
                        text = "Admin panel",
                        style = MaterialTheme.typography.h5,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ){
        Column(
            modifier = Modifier
                .padding(5.dp, 190.dp, 5.dp, 5.dp)
                .fillMaxSize(),
            Arrangement.Top,
            Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier
                    .width(230.dp)
                    .height(130.dp)
                    .padding(0.dp, 0.dp, 0.dp, 60.dp),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFF3E5F5),
                    contentColor = Color(0xFF424242)
                ),
                shape = RoundedCornerShape(50),
                onClick = { viewModel.createUser() }
            ) {
                Text(text = "Create User", fontSize = 25.sp)
            }
            Button(
                modifier = Modifier
                    .width(230.dp)
                    .height(130.dp)
                    .padding(0.dp, 0.dp, 0.dp, 60.dp),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFF3E5F5),
                    contentColor = Color(0xFF424242)
                ),
                shape = RoundedCornerShape(50),
                onClick = { viewModel.addCar() }
            ) {
                Text(text = "Add Car", fontSize = 25.sp)
            }
            Button(
                modifier = Modifier
                    .width(230.dp)
                    .height(130.dp)
                    .padding(0.dp, 0.dp, 0.dp, 60.dp),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFF3E5F5),
                    contentColor = Color(0xFF424242)
                ),
                shape = RoundedCornerShape(50),
                onClick = { viewModel.displayUsers() }
            ) {
                Text(text = "Display Users", fontSize = 25.sp)
            }
            Button(
                modifier = Modifier
                    .width(230.dp)
                    .height(130.dp)
                    .padding(0.dp, 0.dp, 0.dp, 60.dp),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFF3E5F5),
                    contentColor = Color(0xFF424242)
                ),
                shape = RoundedCornerShape(50),
                onClick = { viewModel.displayCars() }
            ) {
                Text(text = "Display Cars", fontSize = 25.sp)
            }
            Text(text = token.toString())
        }
    }
}