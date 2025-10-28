package com.pasteleria1000sabores.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.pasteleria1000sabores.viewmodel.CatalogoViewModel

/**
 * Detail screen for a single product.  Displays a larger image, full
 * description and price.  Allows the user to add the product to their cart
 * via [onAddToCart], and provides a back navigation via [onBack].  If the
 * productId is null or not found, a simple error message is shown.
 */
@Composable
fun DetalleProductoScreen(
    productoId: Int?,
    onAddToCart: () -> Unit,
    onBack: () -> Unit,
    viewModel: CatalogoViewModel = viewModel()
) {
    val productos by viewModel.productos.collectAsState()
    val producto = productos.firstOrNull { it.id == productoId }

    if (producto == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Producto no encontrado")
            Button(onClick = onBack, modifier = Modifier.padding(top = 16.dp)) {
                Text("Volver")
            }
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = producto.imagen,
            contentDescription = producto.nombre,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = producto.nombre, style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = producto.descripcion, style = androidx.compose.material3.MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = "$${String.format("%.0f", producto.precio)}", style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.size(16.dp))
        Button(onClick = onAddToCart) {
            Text("Agregar al carrito")
        }
        Spacer(modifier = Modifier.size(8.dp))
        Button(onClick = onBack) {
            Text("Volver")
        }
    }
}