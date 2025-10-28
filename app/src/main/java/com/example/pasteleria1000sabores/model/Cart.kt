package com.example.pasteleria1000sabores.model

/**
 * Objeto singleton para gestionar los elementos en el carrito de compras.
 * Permite añadir productos, obtener el total y vaciar el carrito.
 */
object Cart {
    /** Lista mutable que contiene los elementos actuales del carrito. */
    val items: MutableList<CartItem> = mutableListOf()

    /**
     * Agrega un producto al carrito. Si el producto ya existe, incrementa
     * su cantidad.
     */
    fun addProduct(product: Product) {
        // Busca si el producto ya está en el carrito
        val existing = items.find { it.product.id == product.id }
        if (existing != null) {
            existing.quantity += 1
        } else {
            items.add(CartItem(product))
        }
    }

    /**
     * Calcula el total del carrito sumando precio * cantidad de cada ítem.
     */
    fun total(): Double {
        var sum = 0.0
        for (item in items) {
            sum += item.product.price * item.quantity
        }
        return sum
    }

    /**
     * Elimina todos los productos del carrito.
     */
    fun clear() {
        items.clear()
    }
}