package com.example.carrentalapp.screens.add_car_page

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.carrentalapp.util.UiEvent

@Composable
fun AddCarPageScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddCarPageViewModel = hiltViewModel()
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
                        text = "Car creation",
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
                text = "Write information about the car below",
                fontSize = 20.sp
            )
            TextField(
                modifier = Modifier
                    .padding(0.dp, 15.dp, 0.dp, 20.dp)
                    .border(0.8.dp, Color.Black, RoundedCornerShape(5)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                value = viewModel.brand,
                placeholder = {
                    Text(text = "Enter car`s brand")
                },
                onValueChange = {
                    viewModel.changeBrand(it)
                }
            )
            TextField(
                modifier = Modifier
                    .padding(0.dp, 15.dp, 0.dp, 20.dp)
                    .border(0.8.dp, Color.Black, RoundedCornerShape(5)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                value = viewModel.model,
                placeholder = {
                    Text(text = "Enter car`s model")
                },
                onValueChange = {
                    viewModel.changeModel(it)
                }
            )
            TextField(
                modifier = Modifier
                    .padding(0.dp, 15.dp, 0.dp, 30.dp)
                    .border(0.8.dp, Color.Black, RoundedCornerShape(5)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                value = viewModel.licensePlate,
                placeholder = {
                    Text(text = "Enter car`s license plate")
                },
                onValueChange = {
                    viewModel.changeLicensePlate(it)
                }
            )
            TextField(
                modifier = Modifier
                    .padding(0.dp, 15.dp, 0.dp, 30.dp)
                    .border(0.8.dp, Color.Black, RoundedCornerShape(5)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                value = viewModel.manufactureYear,
                placeholder = {
                    Text(text = "Enter car`s manufacture year")
                },
                onValueChange = {
                    viewModel.changeManufactureYear(it)
                }
            )
            TextField(
                modifier = Modifier
                    .padding(0.dp, 15.dp, 0.dp, 30.dp)
                    .border(0.8.dp, Color.Black, RoundedCornerShape(5)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                value = viewModel.pricePerDayUsd,
                placeholder = {
                    Text(text = "Enter car`s price per day")
                },
                onValueChange = {
                    viewModel.changePricePerDayUsd(it)
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
                onClick = { viewModel.addCar() }
            ) {
                Text(text = "Add car", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}