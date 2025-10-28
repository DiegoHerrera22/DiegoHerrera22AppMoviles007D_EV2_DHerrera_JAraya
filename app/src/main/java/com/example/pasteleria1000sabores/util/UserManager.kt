package com.example.pasteleria1000sabores.util

import android.content.Context

/**
 * Gestor simple de usuarios que utiliza SharedPreferences para almacenar
 * credenciales. Este ejemplo soporta un único usuario registrado, ya que
 * cumple con los requerimientos mínimos de la evaluación.
 */
object UserManager {
    private const val PREFS_NAME = "user_prefs"
    private const val KEY_EMAIL = "email"
    private const val KEY_PASSWORD = "password"
    private const val KEY_NAME = "name"
    private const val KEY_ADDRESS = "address"
    private const val KEY_RUT = "rut"

    /**
     * Registra un nuevo usuario. Almacena sus datos en SharedPreferences.
     * Si ya existe un usuario con el mismo correo, lo sobrescribe.
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
        }.apply()
    }

    /**
     * Verifica si las credenciales proporcionadas coinciden con las almacenadas.
     *
     * @return true si coinciden, false en caso contrario
     */
    fun login(context: Context, email: String, password: String): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val savedEmail = prefs.getString(KEY_EMAIL, null)
        val savedPassword = prefs.getString(KEY_PASSWORD, null)
        return email == savedEmail && password == savedPassword
    }
}