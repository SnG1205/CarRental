package com.example.bachelorandroid.screens.display_clients_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bachelorandroid.data.Client
import com.example.bachelorandroid.util.UiEvent

@Composable
fun ClientsItem(
    client: Client,
){
    Row(
        modifier = Modifier
            .padding(0.dp, 0.dp, 20.dp, 0.dp)
            .fillMaxSize(),
        Arrangement.Absolute.Left
    ) {
        Text(text = client.id.toString())
        Text(text = client.firstName)
        Text(text = client.lastName)
        Text(text = client.address!!)
    }
}