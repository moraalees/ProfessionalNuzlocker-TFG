package com.example.professionalnuzlocker.ui.screens.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.professionalnuzlocker.data.remote.FirebaseAuthRepository
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val repository = FirebaseAuthRepository()

    var cargando by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    fun login(email: String, password: String, onExito: () -> Unit) {
        viewModelScope.launch {
            cargando = true
            error = null
            val result = repository.login(email.trim(), password)
            cargando = false
            if (result.isSuccess) onExito()
            else error = mensajeError(result.exceptionOrNull())
        }
    }

    fun registrar(email: String, password: String, onExito: () -> Unit) {
        viewModelScope.launch {
            cargando = true
            error = null
            val result = repository.registro(email.trim(), password)
            cargando = false
            if (result.isSuccess) onExito()
            else error = mensajeError(result.exceptionOrNull())
        }
    }

    fun limpiarError() { error = null }

    private fun mensajeError(e: Throwable?): String {
        if (e is FirebaseNetworkException) return "Sin conexión a internet. Revisa tu red."

        val errorCode = (e as? FirebaseAuthException)?.errorCode ?: ""
        val msg = e?.message ?: ""

        return when {
            errorCode == "ERROR_INVALID_CREDENTIAL" ||
            errorCode == "ERROR_WRONG_PASSWORD" ||
            errorCode == "ERROR_USER_NOT_FOUND" ||
            msg.contains("INVALID_LOGIN_CREDENTIALS") ||
            msg.contains("password is invalid") ||
            msg.contains("no user record") ->
                "Correo o contraseña incorrectos."

            errorCode == "ERROR_EMAIL_ALREADY_IN_USE" ||
            msg.contains("email address is already in use") ->
                "Este correo ya está registrado. Prueba a iniciar sesión."

            errorCode == "ERROR_INVALID_EMAIL" ||
            msg.contains("badly formatted") ->
                "El formato del correo no es válido."

            errorCode == "ERROR_WEAK_PASSWORD" ||
            msg.contains("weak password") ->
                "La contraseña es demasiado débil."

            errorCode == "ERROR_TOO_MANY_REQUESTS" ||
            msg.contains("TOO_MANY_REQUESTS") ->
                "Demasiados intentos fallidos. Espera unos minutos."

            errorCode == "ERROR_USER_DISABLED" ->
                "Esta cuenta ha sido deshabilitada."

            msg.contains("NETWORK_REQUEST_FAILED") ||
            msg.contains("Unable to resolve host") ->
                "Sin conexión a internet. Revisa tu red."

            else -> "Error inesperado. Inténtalo de nuevo."
        }
    }
}
