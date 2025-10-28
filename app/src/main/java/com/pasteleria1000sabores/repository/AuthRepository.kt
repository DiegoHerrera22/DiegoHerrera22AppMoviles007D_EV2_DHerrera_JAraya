package com.pasteleria1000sabores.repository

import com.pasteleria1000sabores.model.Usuario

/**
 * In‑memory repository for user accounts.  This simplistic implementation
 * stores users in a mutable list and performs basic credential checks.
 * In a production app you would replace this with a persistent storage
 * mechanism (e.g. Room database or remote server) and never store
 * passwords in plaintext.
 */
class AuthRepository {
    private val users = mutableListOf<Usuario>()

    /**
     * Attempts to authenticate a user with the given [rut] and [password].
     * Returns true if a matching user is found; otherwise false.
     */
    fun login(rut: String, password: String): Boolean {
        val user = users.firstOrNull { it.rut.equals(rut, ignoreCase = true) }
        return user?.password == password
    }

    /**
     * Registers a new [Usuario].  If the RUT is already used by another
     * account, registration fails and the method returns false.  On
     * successful registration the new user is added to the list and true
     * is returned.
     */
    fun register(usuario: Usuario): Boolean {
        if (users.any { it.rut.equals(usuario.rut, ignoreCase = true) }) {
            return false
        }
        users += usuario
        return true
    }
}