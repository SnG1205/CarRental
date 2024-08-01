package com.example.bachelorandroid.screens.depot_page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
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
    ) {
        Text(text = index.toString())
        Text(text = stock.symbols)
        Text(text = stock.amount.toString())
        Text(text = stock.price.toString())
    }
}