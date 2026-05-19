package com.example.professionalnuzlocker.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.professionalnuzlocker.data.remote.FirestorePartidaRepository
import kotlinx.coroutines.launch

/**
 * ViewModel de [PantallaInicio] que comprueba si existe una partida guardada en Firestore
 * y permite borrarla antes de empezar una nueva.
 *
 * Estados: [hayPartida] (`null` mientras no se ha consultado, `true`/`false` tras
 * [comprobarPartida]) y [cargando] (activo durante la consulta).
 * Funciones principales: [comprobarPartida], [borrarPartidas], [limpiarEstado].
 */
class PantallaInicioViewModel : ViewModel() {

    private val repository = FirestorePartidaRepository()

    var hayPartida by mutableStateOf<Boolean?>(null)
        private set

    var cargando by mutableStateOf(false)
        private set

    /** Consulta Firestore para determinar si existe una partida activa y actualiza [hayPartida]. */
    fun comprobarPartida() {
        viewModelScope.launch {
            cargando = true

            val res = repository.existePartida()

            hayPartida = res.getOrNull() ?: false
            cargando = false
        }
    }

    fun borrarPartidas(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val res = repository.borrarPartidas()

            if (res.isSuccess) {
                onSuccess()
            }
        }
    }

    fun limpiarEstado() {
        hayPartida = null
    }
}