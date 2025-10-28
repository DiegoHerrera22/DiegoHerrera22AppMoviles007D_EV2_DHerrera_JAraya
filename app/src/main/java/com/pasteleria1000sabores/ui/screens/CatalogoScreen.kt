package com.pasteleria1000sabores.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pasteleria1000sabores.ui.components.ProductCard
import com.pasteleria1000sabores.viewmodel.CatalogoViewModel

/**
 * Shows a list of available products.  Tapping a product navigates to the
 * detail screen via [onProductoClick].  Buttons at the top allow the user
 * to navigate to the cart or to the back office screens.  The products are
 * loaded from [CatalogoViewModel].
 */
@Composable
fun CatalogoScreen(
    onProductoClick: (Int) -> Unit,
    onCartClick: () -> Unit,
    onBackOfficeClick: () -> Unit,
    viewModel: CatalogoViewModel = viewModel()
) {
    val productos by viewModel.productos.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onCartClick) { Text("Carrito") }
            Button(onClick = onBackOfficeClick) { Text("Back Office") }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            items(productos) { producto ->
                ProductCard(producto = producto, onClick = { onProductoClick(producto.id) })
            }
        }
    }
}