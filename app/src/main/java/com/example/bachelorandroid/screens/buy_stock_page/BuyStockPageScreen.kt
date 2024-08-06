package com.example.bachelorandroid.screens.buy_stock_page

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
                backgroundColor = Color(0xFF2962FF), //Todo change color
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
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            Arrangement.Top,
            Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = Modifier
                    .padding(0.dp, 15.dp, 0.dp, 25.dp)
                    .border(0.8.dp, Color.Black, RoundedCornerShape(5)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                value = viewModel.stockSymbols,
                onValueChange = { viewModel.changeStockSymbols(it) },
                placeholder = {
                    Text(text = "Enter symbols of desired stock")
                },
                /*trailingIcon = {
                    IconButton(onClick = { viewModel.findStock() }) {
                        Icons.Default.Send
                    }
                },*/
            )
            TextField(
                modifier = Modifier
                    .padding(0.dp, 15.dp, 0.dp, 25.dp)
                    .border(0.8.dp, Color.Black, RoundedCornerShape(5)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                value = viewModel.amount,
                onValueChange = { viewModel.changeAmount(it) },
                placeholder = {
                    Text(text = "Enter amount to buy")
                },
            )
            Text(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 25.dp),
                text = viewModel.text,)
            Button(
                modifier = Modifier
                    .width(170.dp)
                    .height(60.dp)
                    .padding(0.dp, 0.dp, 0.dp, 25.dp),
                shape = RoundedCornerShape(50),
                onClick = { viewModel.findStock() }
            ) {
                Text(text = "Find Stock")
            } //Todo replace with an clickable icon near TextField (if possible)
            Button(
                modifier = Modifier
                    .width(170.dp)
                    .height(60.dp)
                    .padding(0.dp, 0.dp, 0.dp, 25.dp),
                shape = RoundedCornerShape(50),
                onClick = { viewModel.buyShares() }
            ) {
                Text(text = "Buy shares")
            }
        }
    }
}