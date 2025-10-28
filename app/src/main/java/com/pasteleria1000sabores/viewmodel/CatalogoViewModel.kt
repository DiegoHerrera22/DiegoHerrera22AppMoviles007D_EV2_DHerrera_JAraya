package com.pasteleria1000sabores.viewmodel

import androidx.lifecycle.ViewModel
import com.pasteleria1000sabores.model.Producto
import com.pasteleria1000sabores.repository.ProductoRepository
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel exposing the catalogue of products.  It delegates all data
 * operations to [ProductoRepository] and provides access to the current list
 * of products via a [StateFlow].  Additional business logic, such as
 * filtering or sorting, can be added here as the requirements grow.
 */
class CatalogoViewModel(
    private val repository: ProductoRepository = ProductoRepository()
) : ViewModel() {
    val productos: StateFlow<List<Producto>> = repository.productos

    fun addProducto(producto: Producto) {
        repository.addProducto(producto)
    }

    fun getNextProductId(): Int = repository.getNextProductId()
}