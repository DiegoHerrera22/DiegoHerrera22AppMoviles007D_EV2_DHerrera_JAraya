package com.pasteleria1000sabores.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Confirmation screen displayed when a purchase is successful.  Shows a
 * congratulatory message and a button to return to the catalogue.
 */
@Composable
fun CompraExitosaScreen(onContinue: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("¡Compra exitosa!")
        Button(onClick = onContinue, modifier = Modifier.padding(top = 16.dp)) {
            Text("Seguir comprando")
        }
    }
}