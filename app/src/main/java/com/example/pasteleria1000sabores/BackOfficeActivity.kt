package com.example.pasteleria1000sabores

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.pasteleria1000sabores.data.DataRepository
import com.example.pasteleria1000sabores.model.Product
import com.example.pasteleria1000sabores.ui.ProductAdapter

/**
 * Pantalla del back office. Presenta la misma lista de productos que el
 * catálogo, pero con la finalidad de administración (en esta evaluación es
 * únicamente visual). Permite abrir una pantalla para agregar un nuevo
 * producto (sin funcionalidad real).
 */
class BackOfficeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_back_office)

        DataRepository.loadProducts(this)
        val products: List<Product> = DataRepository.getProducts()

        val listView: ListView = findViewById(R.id.listViewBackOffice)
        val adapter = ProductAdapter(this, products)
        listView.adapter = adapter
        // En el back office, los elementos no tienen acción al pulsar, pero
        // podríamos reutilizar la pantalla de detalles si se deseara.
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            // No hacemos nada. Podrías mostrar detalles como en el catálogo.
        }

        val buttonAdd: Button = findViewById(R.id.buttonAddProduct)
        buttonAdd.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            startActivity(intent)
        }
    }
}