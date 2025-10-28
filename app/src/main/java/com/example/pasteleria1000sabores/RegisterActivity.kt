package com.example.pasteleria1000sabores

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pasteleria1000sabores.util.RutValidator
import com.example.pasteleria1000sabores.util.UserManager

/**
 * Pantalla de registro. Permite al usuario crear una nueva cuenta
 * introduciendo sus datos. Valida el RUT chileno antes de registrar.
 */
class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val inputName: EditText = findViewById(R.id.inputName)
        val inputEmail: EditText = findViewById(R.id.inputEmail)
        val inputPassword: EditText = findViewById(R.id.inputPassword)
        val inputAddress: EditText = findViewById(R.id.inputAddress)
        val inputRut: EditText = findViewById(R.id.inputRut)
        val buttonRegister: Button = findViewById(R.id.buttonRegister)

        buttonRegister.setOnClickListener {
            val name = inputName.text.toString().trim()
            val email = inputEmail.text.toString().trim()
            val password = inputPassword.text.toString().trim()
            val address = inputAddress.text.toString().trim()
            val rut = inputRut.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty() || rut.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Validamos RUT
            if (!RutValidator.isValid(rut)) {
                Toast.makeText(this, "RUT inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Registramos al usuario
            UserManager.register(this, name, email, password, address, rut)
            Toast.makeText(this, "Registro exitoso. Ahora puedes iniciar sesión", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}