package com.example.pasteleria1000sabores

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.pasteleria1000sabores.data.DataRepository
import com.example.pasteleria1000sabores.model.Product
import com.example.pasteleria1000sabores.ui.ProductAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Pantalla de catálogo. Muestra la lista de productos disponibles
 * permitiendo seleccionar uno para ver sus detalles. También contiene un
 * botón flotante para abrir el carrito de compras.
 */
class CatalogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)

        // Cargamos los productos desde el repositorio
        DataRepository.loadProducts(this)
        val products: List<Product> = DataRepository.getProducts()

        val listView: ListView = findViewById(R.id.listViewProducts)
        val adapter = ProductAdapter(this, products)
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selected = products[position]
            // Abrimos la pantalla de detalle pasando el ID del producto
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra("productId", selected.id)
            startActivity(intent)
        }

        // Configuramos el botón flotante del carrito
        val buttonCart: FloatingActionButton = findViewById(R.id.buttonCart)
        buttonCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }
}