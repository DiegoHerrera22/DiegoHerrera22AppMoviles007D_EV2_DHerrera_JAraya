package com.pasteleria1000sabores.repository

import android.content.Context
import com.pasteleria1000sabores.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONArray

/**
 * Repository responsible for managing the catalogue of products.  On
 * initialisation it loads products from the bundled JSON file in the
 * assets directory.  Products can also be added at runtime (e.g. via the
 * back office) and the list is exposed as a [StateFlow] to enable
 * reactive updates in the UI.
 */
class ProductoRepository(private val context: Context? = null) {
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    init {
        // When a Context is provided the repository attempts to load products
        // from the JSON file in the assets directory.  Otherwise a small
        // default set of products is used.  This allows the repository to
        // function in previews and tests where no Android context is available.
        if (context != null) {
            loadProductosFromAssets()
        } else {
            loadDefaultProductos()
        }
    }

    private fun loadProductosFromAssets() {
        try {
            val jsonStr = context!!.assets.open("productos.json")
                .bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(jsonStr)
            val list = mutableListOf<Producto>()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                list.add(
                    Producto(
                        id = obj.getInt("id"),
                        nombre = obj.getString("nombre"),
                        descripcion = obj.getString("descripcion"),
                        precio = obj.getDouble("precio"),
                        imagen = obj.getString("imagen")
                    )
                )
            }
            _productos.value = list
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Fallback product list used when no Android context is available.  The
    // contents mirror the sample JSON file bundled with the project.
    private fun loadDefaultProductos() {
        _productos.value = listOf(
            Producto(1, "Torta de Chocolate", "Deliciosa torta de chocolate con relleno de crema.", 15000.0, "https://example.com/chocolate.jpg"),
            Producto(2, "Cheesecake de Frutilla", "Suave cheesecake con frutillas frescas.", 12000.0, "https://example.com/cheesecake.jpg"),
            Producto(3, "Pie de Limón", "Clásico pie de limón con merengue.", 10000.0, "https://example.com/piedelimon.jpg")
        )
    }

    fun getProductoById(id: Int): Producto? = _productos.value.firstOrNull { it.id == id }

    fun addProducto(producto: Producto) {
        val newList = _productos.value.toMutableList()
        newList.add(producto)
        _productos.value = newList
    }

    fun getNextProductId(): Int = (_productos.value.maxOfOrNull { it.id } ?: 0) + 1
}