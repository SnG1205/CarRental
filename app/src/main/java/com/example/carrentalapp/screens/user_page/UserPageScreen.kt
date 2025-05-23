package com.example.carrentalapp.screens.user_page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.carrentalapp.util.UiEvent

@Composable
fun UserPageScreen(
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: UserPageViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val carsFlow by viewModel.carsFlow.collectAsState()
    val userId by viewModel.userId.collectAsState()

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
                        text = "Available Cars",
                        style = MaterialTheme.typography.h5,
                        color = Color.White,
                        modifier = Modifier
                            .padding(0.dp, 5.dp, 0.dp, 0.dp)
                    )
                    Spacer(Modifier.weight(1f))
                    Box {
                        IconButton(onClick = { viewModel.toggleDropdownState() }) {
                            Icon(
                                Icons.Default.MoreVert, contentDescription = "More options",
                                tint = Color.White
                            )
                        }
                        DropdownMenu(
                            expanded = viewModel.expanded,
                            modifier = Modifier
                                .align(Alignment.TopEnd),
                            onDismissRequest = { viewModel.setExpandedToFalse() }
                        ) {
                            DropdownMenuItem(
                                onClick = { viewModel.navigateToActiveBookings() }
                            )
                            {
                                Text("My cars")
                            }
                            DropdownMenuItem(
                                onClick = { viewModel.navigateToBookingsHistory() }
                            ) {
                                Text("Booking History")
                            }
                        }
                    }
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(carsFlow) { car ->
                CarItem(car = car, { viewModel.navigateToBooking(car) })
            }
        }
    }
}