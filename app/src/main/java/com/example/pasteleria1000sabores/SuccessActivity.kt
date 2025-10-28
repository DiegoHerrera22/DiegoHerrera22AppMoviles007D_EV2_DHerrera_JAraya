package com.example.pasteleria1000sabores

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Muestra un mensaje de compra exitosa junto con un resumen del pedido. Ofrece
 * la opción de volver al catálogo para seguir comprando.
 */
class SuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        val textOrderDetails: TextView = findViewById(R.id.textOrderDetails)
        val buttonContinue: Button = findViewById(R.id.buttonContinue)

        // Obtenemos el resumen del pedido desde el intent
        val summary = intent.getStringExtra("orderSummary") ?: ""
        textOrderDetails.text = summary

        buttonContinue.setOnClickListener {
            // Regresamos al catálogo
            val intent = Intent(this, CatalogActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }
}