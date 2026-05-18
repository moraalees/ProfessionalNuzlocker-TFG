package com.example.professionalnuzlocker.ui.screens.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.professionalnuzlocker.data.model.ChatPregunta
import com.example.professionalnuzlocker.data.remote.ChatApi
import com.example.professionalnuzlocker.data.remote.FirestorePartidaRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class PantallaChatViewModel : ViewModel() {

    data class Mensaje(val texto: String, val esUsuario: Boolean)

    private val repository = FirestorePartidaRepository()

    var mensajes by mutableStateOf(listOf<Mensaje>())
        private set
    var cargando by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set
    var cargandoPartida by mutableStateOf(true)
        private set
    var partidaId by mutableStateOf<String?>(null)
        private set

    init {
        viewModelScope.launch {
            repository.cargarPartida().onSuccess { (docId, _) ->
                partidaId = docId
            }
            cargandoPartida = false
        }
    }

    fun enviarMensaje(texto: String) {
        val docId = partidaId ?: return

        mensajes = mensajes + Mensaje(texto, esUsuario = true)
        cargando = true
        error = null

        viewModelScope.launch {
            try {
                val token = obtenerTokenFirebase() ?: throw Exception("Sin sesión")
                val respuesta = ChatApi.service.enviarMensaje(
                    token = "Bearer $token",
                    request = ChatPregunta(message = texto, partida_id = docId)
                )
                mensajes = mensajes + Mensaje(respuesta.response, esUsuario = false)
                repository.incrementarConsultasIA(docId)
            } catch (e: Exception) {
                error = "Error al conectar con NuzBot. Inténtalo de nuevo."
            } finally {
                cargando = false
            }
        }
    }

    private suspend fun obtenerTokenFirebase(): String? {
        return suspendCancellableCoroutine { cont ->
            FirebaseAuth.getInstance().currentUser
                ?.getIdToken(false)
                ?.addOnSuccessListener { result -> cont.resume(result.token) }
                ?.addOnFailureListener { cont.resume(null) }
                ?: cont.resume(null)
        }
    }
}
