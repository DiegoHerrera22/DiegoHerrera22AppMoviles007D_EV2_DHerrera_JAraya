package com.example.pasteleria1000sabores

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Pantalla que informa sobre un fallo en el proceso de compra. Muestra la
 * razón del error y permite volver al carrito para intentar nuevamente.
 */
class FailureActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_failure)

        val textReason: TextView = findViewById(R.id.textReason)
        val buttonRetry: Button = findViewById(R.id.buttonRetry)

        val reason = intent.getStringExtra("reason") ?: "Error desconocido"
        textReason.text = reason

        buttonRetry.setOnClickListener {
            // Regresamos al carrito
            val intent = Intent(this, CartActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }
}