package com.example.pasteleria1000sabores

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pasteleria1000sabores.model.Cart
import com.example.pasteleria1000sabores.ui.CartAdapter
import kotlin.random.Random

/**
 * Pantalla del carrito de compras. Muestra los productos seleccionados y
 * permite modificar las cantidades. También calcula el total y permite
 * finalizar la compra simulando un resultado aleatorio.
 */
class CartActivity : AppCompatActivity() {
    private lateinit var adapter: CartAdapter
    private lateinit var textTotal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val listView: ListView = findViewById(R.id.listViewCart)
        textTotal = findViewById(R.id.textTotal)
        val buttonCheckout: Button = findViewById(R.id.buttonCheckout)

        adapter = CartAdapter(this, Cart.items, onQuantityChanged = {
            updateTotal()
        })
        listView.adapter = adapter

        updateTotal()

        buttonCheckout.setOnClickListener {
            if (Cart.items.isEmpty()) {
                Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Simulamos el proceso de compra: 70% éxito, 30% fracaso
            val success = Random.nextFloat() > 0.3f
            if (success) {
                // Creamos un resumen del pedido antes de limpiar el carrito
                val orderSummary = buildOrderSummary()
                // Limpiamos el carrito
                Cart.clear()
                // Navegamos a la pantalla de éxito pasando el resumen por intent
                val intent = Intent(this, SuccessActivity::class.java)
                intent.putExtra("orderSummary", orderSummary)
                startActivity(intent)
                finish()
            } else {
                // Navegamos a la pantalla de fallo con un mensaje
                val intent = Intent(this, FailureActivity::class.java)
                intent.putExtra("reason", "No se pudo procesar el pago o falta de stock")
                startActivity(intent)
            }
        }
    }

    private fun updateTotal() {
        val total = Cart.total()
        textTotal.text = "Total: $${String.format("%.0f", total)}"
    }

    private fun buildOrderSummary(): String {
        val builder = StringBuilder()
        for (item in Cart.items) {
            builder.append("${item.product.name} x${item.quantity} - $${String.format("%.0f", item.product.price * item.quantity)}\n")
        }
        builder.append("\nTotal pagado: $${String.format("%.0f", Cart.total())}")
        return builder.toString()
    }
}