package com.example.carrentalapp.screens.display_clients_page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
fun DisplayClientsPageScreen(
    onPopBackStack: () -> Unit,
    viewModel: DisplayClientsPageViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopBackStack()
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
                        text = "List of clients",
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
                .padding(5.dp),
            Arrangement.Top,
            Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .padding(0.dp, 5.dp),
            ) {
                Text(
                    modifier = Modifier
                        .padding(10.dp, 0.dp, 10.dp, 0.dp),
                    text = "ID",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                )
                Text(
                    modifier = Modifier
                        .padding(10.dp, 0.dp, 10.dp, 0.dp),
                    text = "First Name",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                )
                Text(
                    modifier = Modifier
                        .padding(10.dp, 0.dp, 10.dp, 0.dp),
                    text = "Last Name",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    )
                Text(
                    modifier = Modifier
                        .padding(10.dp, 0.dp, 10.dp, 0.dp),
                    text = "Address",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(viewModel.clients) { client ->
                    ClientsItem(client = client)
                }
            }
        }
    }
}