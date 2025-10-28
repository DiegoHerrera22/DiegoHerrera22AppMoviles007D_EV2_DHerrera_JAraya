package com.example.pasteleria1000sabores.data

import android.content.Context
import com.example.pasteleria1000sabores.R
import com.example.pasteleria1000sabores.model.Product
import org.json.JSONArray
import org.json.JSONObject

/**
 * Repositorio de datos que carga el listado de productos desde un archivo JSON
 * en la carpeta raw. Este repositorio se mantiene en memoria durante toda la
 * ejecución de la app para no leer repetidamente el archivo.
 */
object DataRepository {
    private var loaded = false
    private val productsList: MutableList<Product> = mutableListOf()

    /**
     * Carga los productos desde el archivo JSON si aún no se han cargado.
     */
    fun loadProducts(context: Context) {
        if (loaded) return
        val inputStream = context.resources.openRawResource(R.raw.products)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val jsonArray = JSONArray(jsonString)
        for (i in 0 until jsonArray.length()) {
            val obj: JSONObject = jsonArray.getJSONObject(i)
            val id = obj.getInt("id")
            val name = obj.getString("name")
            val description = obj.getString("description")
            val price = obj.getDouble("price")
            val imageName = obj.optString("image", "ic_launcher_foreground")
            // Obtenemos el id del recurso de imagen a partir del nombre
            val resId = context.resources.getIdentifier(
                imageName, "drawable", context.packageName
            ).takeIf { it != 0 } ?: R.drawable.ic_launcher_foreground
            productsList.add(
                Product(
                    id = id,
                    name = name,
                    description = description,
                    price = price,
                    imageRes = resId
                )
            )
        }
        loaded = true
    }

    /**
     * Devuelve la lista de productos cargados. Asegúrate de llamar a loadProducts
     * antes de invocar este método.
     */
    fun getProducts(): List<Product> = productsList

    /**
     * Busca un producto por su ID. Devuelve null si no existe.
     */
    fun getProductById(id: Int): Product? = productsList.find { it.id == id }
}