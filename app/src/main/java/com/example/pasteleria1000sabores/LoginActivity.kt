package com.example.pasteleria1000sabores

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pasteleria1000sabores.util.UserManager

/**
 * Pantalla de inicio de sesión. Permite al usuario ingresar sus credenciales y
 * navegar a la pantalla de catálogo si son correctas. También ofrece un enlace
 * para ir al formulario de registro.
 */
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val inputEmail: EditText = findViewById(R.id.inputEmail)
        val inputPassword: EditText = findViewById(R.id.inputPassword)
        val buttonLogin: Button = findViewById(R.id.buttonLogin)
        val textRegister: TextView = findViewById(R.id.textRegister)

        buttonLogin.setOnClickListener {
            val email = inputEmail.text.toString().trim()
            val password = inputPassword.text.toString().trim()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Debes completar todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val success = UserManager.login(this, email, password)
            if (success) {
                // Iniciamos la pantalla de catálogo
                val intent = Intent(this, CatalogActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        textRegister.setOnClickListener {
            // Navegamos a la actividad de registro
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}