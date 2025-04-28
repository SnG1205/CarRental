package com.example.carrentalapp.screens.book_car_page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.carrentalapp.screens.bookings_page.BookingItem
import com.example.carrentalapp.screens.user_page.CarItem
import com.example.carrentalapp.util.UiEvent

@Composable
fun BookCarPageScreen(
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: BookCarPageViewModel = hiltViewModel()
){
    val scaffoldState = rememberScaffoldState()
    val car = viewModel.carToBook.collectAsState()
    val userId = viewModel.userId.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
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
                Row() {
                    Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow Back",
                        tint = Color.White,
                        modifier = Modifier
                            .clickable {
                                viewModel.popBack()
                            }
                            .padding(0.dp, 10.dp, 0.dp, 0.dp))

                    Spacer(modifier = Modifier.width(20.dp))

                    Text(
                        text = "Bookings History",
                        style = MaterialTheme.typography.h5,
                        color = Color.White,
                        modifier = Modifier
                            .padding(0.dp, 5.dp, 0.dp, 0.dp)
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Column(modifier = Modifier
                .padding(5.dp)
                /*.fillMaxSize()*/,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)) {
                BookCarItem(car = car.value!!)
                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                )
                androidx.compose.material.Button(
                    modifier = Modifier
                        .width(200.dp)
                        .height(100.dp)
                        .padding(0.dp, 0.dp, 0.dp, 25.dp),
                    colors = androidx.compose.material.ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFF4511E),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(50),
                    onClick = { viewModel.bookCar() }
                ) {
                    Text(text = "Book Car", fontWeight = FontWeight.Bold, fontSize = 22.sp)
                }
                /*Text(text = viewModel.date.toString())
                Text(text = userId.value!!.toString())
                Text(text = car.value!!.id!!.toString())*/
            }
        }
    }
}