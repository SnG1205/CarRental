package com.example.bachelorandroid.screens.depot_page

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bachelorandroid.util.UiEvent

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
            Row {
                Text(text = "Nr.", fontWeight = FontWeight.Bold)
                Text(text = "Symbols", fontWeight = FontWeight.Bold)
                Text(text = "Amount", fontWeight = FontWeight.Bold)
                Text(text = "Price", fontWeight = FontWeight.Bold)
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
                text = "Total price of Your depot is: ${
                    String.format(
                        "%.2f",
                        viewModel.totalAmount
                    )
                }"
            )
        }
    }
}