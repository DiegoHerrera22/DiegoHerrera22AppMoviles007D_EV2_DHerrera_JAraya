package com.pasteleria1000sabores.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pasteleria1000sabores.viewmodel.AuthViewModel

/**
 * Registration screen.  Allows the user to create a new account by entering
 * their RUT, name, email and password.  Upon successful registration, the
 * [onRegisterSuccess] callback is invoked.  The user can return to the
 * previous screen via [onNavigateBack].
 */
@Composable
fun RegistroScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Crear cuenta")
        OutlinedTextField(
            value = uiState.rut,
            onValueChange = { viewModel.onRutChanged(it) },
            label = { Text(text = "RUT") },
            singleLine = true,
            modifier = Modifier.padding(top = 16.dp)
        )
        OutlinedTextField(
            value = uiState.name,
            onValueChange = { viewModel.onNameChanged(it) },
            label = { Text(text = "Nombre") },
            singleLine = true,
            modifier = Modifier.padding(top = 8.dp)
        )
        OutlinedTextField(
            value = uiState.email,
            onValueChange = { viewModel.onEmailChanged(it) },
            label = { Text(text = "Correo") },
            singleLine = true,
            modifier = Modifier.padding(top = 8.dp)
        )
        OutlinedTextField(
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChanged(it) },
            label = { Text(text = "Contraseña") },
            singleLine = true,
            modifier = Modifier.padding(top = 8.dp)
        )
        if (uiState.errorMessage != null) {
            Text(text = uiState.errorMessage!!, modifier = Modifier.padding(top = 8.dp))
        }
        Button(
            onClick = {
                if (viewModel.register()) {
                    onRegisterSuccess()
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Registrarse")
        }
        Button(
            onClick = onNavigateBack,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Volver")
        }
    }
}