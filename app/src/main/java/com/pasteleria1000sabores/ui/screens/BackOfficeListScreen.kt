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
 * Back office screen showing the list of products.  Administrators can
 * navigate to a form to add a new product via [onAddProducto].  A back
 * button returns to the catalogue.  Product editing is not implemented,
 * but could be added as part of an extended version of the app.
 */
@Composable
fun BackOfficeListScreen(
    onAddProducto: () -> Unit,
    onNavigateBack: () -> Unit,
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
            Button(onClick = onNavigateBack) { Text("Volver") }
            Button(onClick = onAddProducto) { Text("Agregar producto") }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            items(productos) { producto ->
                ProductCard(producto = producto, onClick = { /* Could navigate to edit */ })
            }
        }
    }
}