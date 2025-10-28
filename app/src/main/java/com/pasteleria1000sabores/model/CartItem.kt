package com.pasteleria1000sabores.model

/**
 * Represents an item in the shopping cart.  Each item references a
 * [producto] and stores the [cantidad] selected by the user.  The total
 * for this item can be derived by multiplying the product's price by the
 * quantity.
 */
data class CartItem(
    val producto: Producto,
    var cantidad: Int
)