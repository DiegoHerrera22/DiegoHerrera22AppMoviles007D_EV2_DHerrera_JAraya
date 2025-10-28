package com.example.pasteleria1000sabores

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pasteleria1000sabores.data.DataRepository
import com.example.pasteleria1000sabores.model.Cart
import com.example.pasteleria1000sabores.model.Product

/**
 * Muestra los detalles de un producto seleccionado. Incluye la imagen,
 * descripción extendida, precio y un botón para agregar al carrito.
 */
class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val productId = intent.getIntExtra("productId", -1)
        val product: Product? = DataRepository.getProductById(productId)

        if (product == null) {
            Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val image: ImageView = findViewById(R.id.imageProduct)
        val textName: TextView = findViewById(R.id.textName)
        val textPrice: TextView = findViewById(R.id.textPrice)
        val textDescription: TextView = findViewById(R.id.textDescription)
        val buttonAdd: Button = findViewById(R.id.buttonAddToCart)

        image.setImageResource(product.imageRes)
        textName.text = product.name
        textPrice.text = "$${String.format("%.0f", product.price)}"
        textDescription.text = product.description

        buttonAdd.setOnClickListener {
            Cart.addProduct(product)
            Toast.makeText(this, "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
        }
    }
}