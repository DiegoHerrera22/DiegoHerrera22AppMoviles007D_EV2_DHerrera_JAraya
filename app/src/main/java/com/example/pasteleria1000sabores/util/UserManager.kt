package com.example.pasteleria1000sabores.util

import android.content.Context

/**
 * Manejo básico de usuarios con SharedPreferences.
 * Soporta: registro, login, obtener datos y estado de sesión.
 */
object UserManager {

    private const val PREFS_NAME = "user_prefs"

    private const val KEY_EMAIL = "email"
    private const val KEY_PASSWORD = "password"
    private const val KEY_NAME = "name"
    private const val KEY_ADDRESS = "address"
    private const val KEY_RUT = "rut"

    // Nuevo: estado de sesión
    private const val KEY_IS_LOGGED_IN = "is_logged_in"

    /**
     * Registra un nuevo usuario. Si se registra uno nuevo, reemplaza el existente.
     */
    fun register(
        context: Context,
        name: String,
        email: String,
        password: String,
        address: String,
        rut: String
    ) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putString(KEY_EMAIL, email)
            putString(KEY_PASSWORD, password)
            putString(KEY_NAME, name)
            putString(KEY_ADDRESS, address)
            putString(KEY_RUT, rut)
            putBoolean(KEY_IS_LOGGED_IN, false) // Aún no inicia sesión
        }.apply()
    }

    /**
     * Inicia sesión si el correo y contraseña coinciden.
     * Si son correctos, marca la sesión como activa.
     */
    fun login(context: Context, email: String, password: String): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val savedEmail = prefs.getString(KEY_EMAIL, null)
        val savedPassword = prefs.getString(KEY_PASSWORD, null)

        val success = (email == savedEmail && password == savedPassword)

        if (success) {
            prefs.edit().putBoolean(KEY_IS_LOGGED_IN, true).apply()
        }

        return success
    }

    /**
     * Cerrar sesión (logout)
     */
    fun logout(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_IS_LOGGED_IN, false).apply()
    }

    /**
     * Saber si hay sesión activa
     */
    fun isLoggedIn(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    /**
     * Obtener correo del usuario actual
     */
    fun getLoggedEmail(context: Context): String? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_EMAIL, null)
    }

    /**
     * Saber si el usuario actual es Admin (correo @admin.cl)
     */
    fun isAdmin(context: Context): Boolean {
        val email = getLoggedEmail(context) ?: return false
        return email.endsWith("@admin.cl", ignoreCase = true)
    }
}
