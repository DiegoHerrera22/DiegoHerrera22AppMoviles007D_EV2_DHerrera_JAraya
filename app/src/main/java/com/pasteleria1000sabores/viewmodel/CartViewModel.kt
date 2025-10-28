package com.pasteleria1000sabores.viewmodel

import androidx.lifecycle.ViewModel
import com.pasteleria1000sabores.model.CartItem
import com.pasteleria1000sabores.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlin.random.Random

/**
 * ViewModel responsible for managing the shopping cart.  Keeps track of the
 * list of [CartItem]s as a [StateFlow] so that UI can reactively update.
 * Provides helper methods to add, remove and clear items as well as
 * calculating the total price.  A simple checkout simulation randomly
 * determines success and clears the cart on success.
 */
class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    // Returns the total price of all items in the cart.  This value is
    // calculated on demand rather than exposed as a reactive flow since the
    // calculation is inexpensive and callers can derive it directly from
    // [cartItems].
    fun getTotal(): Double = _cartItems.value.sumOf { it.producto.precio * it.cantidad }

    /**
     * Adds the given [producto] to the cart.  If the product is already in
     * the cart, its quantity is incremented.  Otherwise a new [CartItem]
     * is created.
     */
    fun addToCart(producto: Producto) {
        val mutableList = _cartItems.value.toMutableList()
        val existing = mutableList.firstOrNull { it.producto.id == producto.id }
        if (existing != null) {
            existing.cantidad += 1
        } else {
            mutableList += CartItem(producto, 1)
        }
        _cartItems.value = mutableList
    }

    /**
     * Removes one instance of the given [producto] from the cart.  If the
     * quantity reaches zero the item is removed entirely.
     */
    fun removeFromCart(producto: Producto) {
        val mutableList = _cartItems.value.toMutableList()
        val existing = mutableList.firstOrNull { it.producto.id == producto.id }
        if (existing != null) {
            if (existing.cantidad > 1) {
                existing.cantidad -= 1
            } else {
                mutableList.remove(existing)
            }
            _cartItems.value = mutableList
        }
    }

    /**
     * Clears the cart entirely, removing all items.
     */
    fun clearCart() {
        _cartItems.value = emptyList()
    }

    /**
     * Simulates a checkout process.  Returns true if the purchase was
     * successful and false otherwise.  On success the cart is cleared.  In
     * a real application this method would invoke a payment API.
     */
    fun checkout(): Boolean {
        val success = Random.nextBoolean()
        if (success) {
            clearCart()
        }
        return success
    }
}