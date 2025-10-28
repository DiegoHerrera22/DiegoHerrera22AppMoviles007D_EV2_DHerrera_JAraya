package com.example.pasteleria1000sabores

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * Formulario de administración para agregar un nuevo producto. Esta pantalla es
 * únicamente visual según los requerimientos; no persiste los datos ni los
 * muestra en la lista. Permite al usuario ingresar información de un producto
 * y confirma la acción mediante un mensaje.
 */
class AddProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        val inputName: EditText = findViewById(R.id.inputProductName)
        val inputPrice: EditText = findViewById(R.id.inputProductPrice)
        val inputDescription: EditText = findViewById(R.id.inputProductDescription)
        val inputImage: EditText = findViewById(R.id.inputProductImage)
        val buttonSave: Button = findViewById(R.id.buttonSaveProduct)

        buttonSave.setOnClickListener {
            val name = inputName.text.toString().trim()
            val price = inputPrice.text.toString().trim()
            val description = inputDescription.text.toString().trim()
            val image = inputImage.text.toString().trim()
            if (name.isEmpty() || price.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Todos los campos excepto imagen son requeridos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Toast.makeText(this, "Producto agregado (solo visual, no persiste)", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}