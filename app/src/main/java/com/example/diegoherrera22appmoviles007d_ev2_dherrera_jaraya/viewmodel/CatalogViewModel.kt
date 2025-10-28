package com.example.diegoherrera22appmoviles007d_ev2_dherrera_jaraya.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.diegoherrera22appmoviles007d_ev2_dherrera_jaraya.model.Producto
import com.example.diegoherrera22appmoviles007d_ev2_dherrera_jaraya.repository.ProductRepository

class CatalogViewModel : ViewModel() {
    val products: List<Producto> = ProductRepository.getCatalog()

    // carrito mínimo en memoria
    private val _cart: SnapshotStateList<Producto> = mutableStateListOf()
    val cart: List<Producto> get() = _cart

    fun addToCart(product: Producto) {
        _cart.add(product)
    }

    fun totalCLP(): Int = _cart.sumOf { it.price }
    fun itemsCount(): Int = _cart.size
}