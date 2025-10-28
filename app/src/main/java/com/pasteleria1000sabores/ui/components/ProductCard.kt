package com.pasteleria1000sabores.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pasteleria1000sabores.model.Producto

/**
 * Displays a single product in the catalogue.  A [Card] is used to provide
 * elevation and rounded corners.  When the card is tapped the [onClick]
 * callback is invoked with the ID of the product.
 */
@Composable
fun ProductCard(producto: Producto, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = producto.imagen,
                contentDescription = producto.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(80.dp)
            )
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(text = producto.nombre, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = producto.descripcion, style = MaterialTheme.typography.bodyMedium, maxLines = 2)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "$${String.format("%.0f", producto.precio)}", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}