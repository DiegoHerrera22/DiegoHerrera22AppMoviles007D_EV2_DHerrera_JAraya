package com.example.diegoherrera22appmoviles007d_ev2_dherrera_jaraya.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.diegoherrera22appmoviles007d_ev2_dherrera_jaraya.model.Producto
import com.example.diegoherrera22appmoviles007d_ev2_dherrera_jaraya.viewmodel.CatalogViewModel
import com.example.diegoherrera22appmoviles007d_ev2_dherrera_jaraya.views.components.ProductCard
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(email: String?) {

    val catalogVM: CatalogViewModel = viewModel()
    val snackbarHostState = remember { SnackbarHostState() }

    // 👇 Estado para saber qué producto se agregó últimamente
    var lastAdded by remember { mutableStateOf<Producto?>(null) }

    // 👇 Cuando `lastAdded` cambia, se ejecuta este efecto y muestra el snackbar
    LaunchedEffect(lastAdded) {
        lastAdded?.let { product ->
            snackbarHostState.showSnackbar("Agregado: ${product.name}")
        }
    }

    val money = remember {
        NumberFormat.getCurrencyInstance(Locale("es", "CL")).apply { maximumFractionDigits = 0 }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo de Pastelería") },
                actions = {
                    AssistChip(
                        onClick = { /* TODO: CartScreen */ },
                        label = { Text("Carrito: ${catalogVM.itemsCount()} • ${money.format(catalogVM.totalCLP())}") }
                    )
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(16.dp)
        ) {

            if (!email.isNullOrBlank()) {
                Text("Bienvenido, $email", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
            }

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 220.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(catalogVM.products, key = { it.id }) { p ->
                    ProductCard(
                        product = p,
                        onAddToCart = { added ->
                            catalogVM.addToCart(added)

                            // 👇 Aquí actualizamos el estado
                            lastAdded = added
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}