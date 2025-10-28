package com.pasteleria1000sabores.model

/**
 * Represents a single product available for purchase.  Each product has a
 * unique [id], a [nombre], a [descripcion], a [precio] and a URL to an
 * [imagen].  Additional fields (e.g. category, stock) could be added as
 * requirements expand.
 */
data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val imagen: String
)