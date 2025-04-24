package com.example.carrentalapp.screens.depot_page

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
fun DepotPageScreen(
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: DepotPageViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

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
                        text = "${viewModel.clientName}`s shares",
                        style = MaterialTheme.typography.h5,
                        color = Color.White
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(5.dp, 0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(45.dp)
                        .height(30.dp)
                        .padding(0.dp, 0.dp, 15.dp, 0.dp),
                ) {
                    Text(
                        text = "Nr.",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(30.dp)
                        .padding(0.dp, 0.dp, 20.dp, 0.dp),
                ) {
                    Text(
                        text = "Symbols",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .width(110.dp)
                        .height(30.dp)
                        .padding(0.dp, 0.dp, 10.dp, 0.dp),
                ) {
                    Text(
                        text = "Amount",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(30.dp)
                ) {
                    Text(
                        text = "Price",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
            LazyColumn {
                items(viewModel.stocks) { stock ->
                    StockItem(
                        stock = stock,
                        index = viewModel.stocks.indexOf(stock) + 1,
                        onItemClick = {
                            viewModel.navigateToSell(it.symbols)
                        })
                }
            }
            Text(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 10.dp),
                text = "Total price of Your depot is: ${
                    String.format(
                        "%.2f",
                        viewModel.totalAmount
                    )
                }",
                fontSize = 20.sp
            )
        }
    }
}