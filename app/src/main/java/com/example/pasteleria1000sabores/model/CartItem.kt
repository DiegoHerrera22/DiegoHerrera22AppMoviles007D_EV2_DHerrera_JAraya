package com.example.pasteleria1000sabores.model

/**
 * Representa un elemento dentro del carrito. Contiene el producto y la
 * cantidad solicitada. La cantidad se inicializa en 1 pero puede ser
 * modificada por el usuario en la pantalla del carrito.
 */
data class CartItem(
    val product: Product,
    var quantity: Int = 1
)