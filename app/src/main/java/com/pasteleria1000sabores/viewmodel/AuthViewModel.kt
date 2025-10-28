package com.pasteleria1000sabores.viewmodel

import androidx.lifecycle.ViewModel
import com.pasteleria1000sabores.core.validation.RutValidator
import com.pasteleria1000sabores.model.Usuario
import com.pasteleria1000sabores.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel handling user authentication (login and registration).  It
 * maintains an [AuthUiState] containing the current form values and any
 * validation error.  The underlying authentication logic is delegated to
 * [AuthRepository].
 */
class AuthViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    fun onRutChanged(newValue: String) {
        _uiState.value = _uiState.value.copy(rut = newValue, errorMessage = null)
    }

    fun onNameChanged(newValue: String) {
        _uiState.value = _uiState.value.copy(name = newValue, errorMessage = null)
    }

    fun onEmailChanged(newValue: String) {
        _uiState.value = _uiState.value.copy(email = newValue, errorMessage = null)
    }

    fun onPasswordChanged(newValue: String) {
        _uiState.value = _uiState.value.copy(password = newValue, errorMessage = null)
    }

    /**
     * Attempts to authenticate the current credentials.  Returns true on
     * success and updates the UI state with an error message on failure.
     */
    fun login(): Boolean {
        val rut = _uiState.value.rut
        val password = _uiState.value.password
        val success = repository.login(rut, password)
        return if (success) {
            true
        } else {
            _uiState.value = _uiState.value.copy(errorMessage = "RUT o contraseña incorrectos")
            false
        }
    }

    /**
     * Attempts to register a new user with the current form values.  Validates
     * the RUT and checks that all fields are filled.  On success the
     * repository is updated and the UI state cleared; otherwise an error
     * message is set.  Returns true on success.
     */
    fun register(): Boolean {
        val state = _uiState.value
        if (state.rut.isBlank() || state.name.isBlank() || state.email.isBlank() || state.password.isBlank()) {
            _uiState.value = state.copy(errorMessage = "Todos los campos son obligatorios")
            return false
        }
        if (!RutValidator.isValidRut(state.rut)) {
            _uiState.value = state.copy(errorMessage = "RUT inválido")
            return false
        }
        val user = Usuario(
            rut = state.rut,
            nombre = state.name,
            email = state.email,
            password = state.password
        )
        val success = repository.register(user)
        return if (success) {
            _uiState.value = AuthUiState() // reset state
            true
        } else {
            _uiState.value = state.copy(errorMessage = "El RUT ya está registrado")
            false
        }
    }
}

/**
 * Data class capturing the current state of the authentication form.  This
 * includes the input fields and an optional [errorMessage] displayed when
 * validation fails.
 */
data class AuthUiState(
    val rut: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val errorMessage: String? = null
)