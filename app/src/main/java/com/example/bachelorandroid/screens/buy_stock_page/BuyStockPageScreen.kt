package com.example.bachelorandroid.screens.buy_stock_page

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bachelorandroid.util.UiEvent

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BuyStockPageScreen(
    onPopBackStack: () -> Unit,
    viewModel: BuyStockPageViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    val trailingIcon = @Composable {
        IconButton(onClick = { viewModel.findStock() }) {
            Icons.Default.Send
        }
    }

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
                        text = "Buy Stock",
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
        ) {
            TextField(
                value = viewModel.stockSymbols,
                onValueChange = { viewModel.changeStockSymbols(it) },
                placeholder = {
                    Text(text = "Enter symbols of desired stock")
                },
                //trailingIcon = { Icons.Default.Send } //Todo trailing icon
            )
            TextField(
                value = viewModel.amount,
                onValueChange = { viewModel.changeAmount(it) },
                placeholder = {
                    Text(text = "Enter amount to buy")
                },
            )
            Text(text = viewModel.text)
            Button(onClick = { viewModel.findStock() }) {
                Text(text = "Find Stock")
            } //Todo replace with an clickable icon near TextField (if possible)
            Button(onClick = { viewModel.buyShares() }) {
                Text(text = "Buy shares")
            }
        }
    }
}