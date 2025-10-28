package com.example.pasteleria1000sabores

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pasteleria1000sabores.data.DataRepository
import com.example.pasteleria1000sabores.model.Product
import com.example.pasteleria1000sabores.ui.ProductAdapter
import com.example.pasteleria1000sabores.util.*
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Pantalla de catálogo. Muestra la lista de productos disponibles
 * permitiendo seleccionar uno para ver sus detalles. También contiene un
 * botón flotante para abrir el carrito de compras y ahora un menú superior.
 */
class CatalogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)

        // === Toolbar ===
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Productos"

        // Cargamos los productos desde el repositorio
        DataRepository.loadProducts(this)
        val products: List<Product> = DataRepository.getProducts()

        val listView: ListView = findViewById(R.id.listViewProducts)
        val adapter = ProductAdapter(this, products)
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selected = products[position]
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra("productId", selected.id)
            startActivity(intent)
        }

        // Botón flotante para ir al carrito
        val buttonCart: FloatingActionButton = findViewById(R.id.buttonCart)
        buttonCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }

    // === Infla el menú superior ===
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // === Controla la visibilidad según sesión y dominio ===
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val isLoggedIn = UserManager.isLoggedIn(this)
        val email = try {
            UserManager.getLoggedEmail(this) ?: ""
        } catch (e: Exception) {
            ""
        }

        // Login / Registro solo visibles si NO hay sesión
        menu.findItem(R.id.menu_login)?.isVisible = !isLoggedIn
        menu.findItem(R.id.menu_register)?.isVisible = !isLoggedIn

        // Admin visible solo si hay sesión y correo termina en @admin.cl
        menu.findItem(R.id.menu_admin)?.isVisible =
            isLoggedIn && email.endsWith("@admin.cl", ignoreCase = true)

        // El resto siempre visibles
        menu.findItem(R.id.menu_home)?.isVisible = true
        menu.findItem(R.id.menu_products)?.isVisible = true
        menu.findItem(R.id.menu_cart)?.isVisible = true
        menu.findItem(R.id.menu_blog)?.isVisible = true
        menu.findItem(R.id.menu_about)?.isVisible = true
        menu.findItem(R.id.menu_contact)?.isVisible = true

        return super.onPrepareOptionsMenu(menu)
    }

    // === Acciones del menú ===
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.menu_home, R.id.menu_products -> {
                startActivity(Intent(this, CatalogActivity::class.java))
                return true
            }

            R.id.menu_cart -> {
                startActivity(Intent(this, CartActivity::class.java))
                return true
            }

            R.id.menu_login -> {
                startActivity(Intent(this, LoginActivity::class.java))
                return true
            }

            R.id.menu_register -> {
                startActivity(Intent(this, RegisterActivity::class.java))
                return true
            }

            R.id.menu_admin -> {
                startActivity(Intent(this, BackOfficeActivity::class.java))
                return true
            }

            R.id.menu_blog, R.id.menu_about, R.id.menu_contact -> {
                Toast.makeText(this, "Próximamente", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
