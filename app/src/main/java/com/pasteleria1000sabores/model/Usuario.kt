package com.pasteleria1000sabores.model

/**
 * Represents a user of the app.  The [rut] acts as the unique identifier
 * (analogous to a username), while [nombre], [email] and [password] capture
 * the remaining account details.  Passwords are stored in plaintext in this
 * example for simplicity; in a real application, never store passwords
 * without proper hashing and encryption.
 */
data class Usuario(
    val rut: String,
    val nombre: String,
    val email: String,
    val password: String
)