package com.example.bachelorandroid.screens.sell_stock_page

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
import com.example.bachelorandroid.util.UiEvent

@Composable
fun SellStockPageScreen(
    onPopBackStack: () -> Unit,
    viewModel: SellStockPageViewModel = hiltViewModel()
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
                        text = "Sell ${viewModel.symbols} stocks",
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
                .padding(10.dp),
            Arrangement.Top,
            Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(5.dp, 10.dp, 5.dp, 10.dp),
                text = "Please enter below the amount You want to sell",
                fontSize = 18.sp
            )
            TextField(
                modifier = Modifier
                    .padding(0.dp, 15.dp, 0.dp, 45.dp)
                    .border(0.8.dp, Color.Black, RoundedCornerShape(5)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                value = viewModel.amount,
                placeholder = {
                    Text(text = "Enter amount")
                },
                onValueChange = { viewModel.changeAmount(it) }
            )
            Button(
                modifier = Modifier
                    .width(180.dp)
                    .height(75.dp)
                    .padding(0.dp, 0.dp, 0.dp, 25.dp),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF76FF03),
                    contentColor = Color(0xFF424242)
                ),
                shape = RoundedCornerShape(50),
                onClick = { viewModel.sellStock() }
            ) {
                Text(text = "Sell Shares", fontWeight = FontWeight.Bold, fontSize = 17.sp)
            }
        }
    }
}