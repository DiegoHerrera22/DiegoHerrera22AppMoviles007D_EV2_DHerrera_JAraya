package com.pasteleria1000sabores.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pasteleria1000sabores.viewmodel.CartViewModel

/**
 * Shopping cart screen.  Displays the items currently in the user's cart along
 * with the running total.  The user can remove items or proceed to checkout.
 * When the checkout button is tapped, [onCompra] is invoked with a boolean
 * indicating whether the purchase was successful.  A back button lets the
 * user return to the catalogue.
 */
@Composable
fun CarritoScreen(
    onCompra: (Boolean) -> Unit,
    onBack: () -> Unit,
    viewModel: CartViewModel
) {
    val cartItems by viewModel.cartItems.collectAsState()
    val total = viewModel.getTotal()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            if (cartItems.isEmpty()) {
                item {
                    Text("Tu carrito está vacío", modifier = Modifier.padding(top = 16.dp))
                }
            } else {
                items(cartItems) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "${item.producto.nombre} x${item.cantidad}")
                        Text(text = "$${String.format("%.0f", item.producto.precio * item.cantidad)}")
                    }
                }
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Total: $${String.format("%.0f", total)}")
            Button(onClick = { onBack() }) {
                Text("Volver")
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            onClick = {
                // In a real app, you would perform payment processing here.  For
                // now, we simulate a random outcome for demonstration purposes.
                val success = viewModel.checkout()
                onCompra(success)
            },
            enabled = cartItems.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Finalizar compra")
        }
    }
}