package com.example.pasteleria1000sabores.model

/**
 * Representa un producto disponible en la pastelería. Incluye un ID, nombre,
 * descripción breve, precio y un recurso de imagen opcional. El atributo
 * imageRes se refiere a un recurso drawable dentro de la carpeta res/drawable.
 */
data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageRes: Int
)