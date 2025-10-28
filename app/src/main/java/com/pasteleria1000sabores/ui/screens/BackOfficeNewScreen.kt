package com.pasteleria1000sabores.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pasteleria1000sabores.model.Producto
import com.pasteleria1000sabores.viewmodel.CatalogoViewModel

/**
 * Form screen used by administrators to create a new product.  Once the
 * fields are filled in and the save button is pressed, the product is added
 * to the repository via [CatalogoViewModel] and the caller is notified via
 * [onProductoCreated].  The user can navigate back without saving via
 * [onNavigateBack].
 */
@Composable
fun BackOfficeNewScreen(
    onProductoCreated: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: CatalogoViewModel = viewModel()
) {
    val nombre = remember { mutableStateOf("") }
    val descripcion = remember { mutableStateOf("") }
    val precio = remember { mutableStateOf("") }
    val imagenUrl = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Nuevo producto")
        OutlinedTextField(
            value = nombre.value,
            onValueChange = { nombre.value = it },
            label = { Text("Nombre") },
            modifier = Modifier.padding(top = 16.dp)
        )
        OutlinedTextField(
            value = descripcion.value,
            onValueChange = { descripcion.value = it },
            label = { Text("Descripción") },
            modifier = Modifier.padding(top = 8.dp)
        )
        OutlinedTextField(
            value = precio.value,
            onValueChange = { precio.value = it },
            label = { Text("Precio") },
            modifier = Modifier.padding(top = 8.dp)
        )
        OutlinedTextField(
            value = imagenUrl.value,
            onValueChange = { imagenUrl.value = it },
            label = { Text("URL de la imagen") },
            modifier = Modifier.padding(top = 8.dp)
        )
        Button(
            onClick = {
                // Attempt to parse the price to a double.  If parsing fails,
                // default to zero.  In a real app you would validate this input.
                val priceValue = precio.value.toDoubleOrNull() ?: 0.0
                val newProducto = Producto(
                    id = viewModel.getNextProductId(),
                    nombre = nombre.value,
                    descripcion = descripcion.value,
                    precio = priceValue,
                    imagen = imagenUrl.value
                )
                viewModel.addProducto(newProducto)
                onProductoCreated()
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Guardar")
        }
        Button(
            onClick = onNavigateBack,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Cancelar")
        }
    }
}