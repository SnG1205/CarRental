package com.example.bachelorandroid.screens.depot_page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bachelorandroid.data.Stock
import com.example.bachelorandroid.util.UiEvent

@Composable
fun StockItem(
    stock: Stock,
    index: Int,
    onItemClick: (Stock) -> Unit,
) {

    Row(
        modifier = Modifier
            .clickable { onItemClick(stock) }
            .padding(5.dp, 5.dp, 5.dp, 15.dp)
    ) {
        Box(
            modifier = Modifier
                .width(45.dp)
                .height(30.dp)
                .padding(5.dp, 0.dp, 15.dp, 0.dp)
        ) {
            Text(
                text = index.toString(),
                fontSize = 20.sp
            )
        }
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(30.dp)
                .padding(0.dp, 0.dp, 25.dp, 0.dp)
        ) {
            Text(
                text = stock.symbols,
                fontSize = 20.sp
            )
        }
        Box(
            modifier = Modifier
                .width(110.dp)
                .height(30.dp)
                .padding(20.dp, 0.dp, 10.dp, 0.dp),
        ) {
            Text(
                text = stock.amount.toString(),
                fontSize = 20.sp
            )
        }
        Box(
            modifier = Modifier
                .width(110.dp)
                .height(30.dp)
                .padding(0.dp, 0.dp, 5.dp, 0.dp),
        ) {
            Text(
                text = stock.price.toString(),
                fontSize = 20.sp
            )
        }
    }
}