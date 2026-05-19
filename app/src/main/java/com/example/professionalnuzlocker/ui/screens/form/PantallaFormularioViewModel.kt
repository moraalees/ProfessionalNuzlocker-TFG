package com.example.professionalnuzlocker.ui.screens.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.professionalnuzlocker.data.remote.FirestorePartidaRepository
import com.example.professionalnuzlocker.data.model.Partida
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel de [PantallaFormulario] que persiste la nueva partida creada durante el flujo
 * introductorio con la Prof. Encina.
 *
 * Expone [estadoGuardado] como `StateFlow<Result<String>?>`, que se actualiza a
 * `Result.success` con el ID del documento una vez guardado, o a `Result.failure` si
 * hay un error. La función principal es [guardarPartida].
 */
class PantallaFormularioViewModel : ViewModel() {

    private val repository = FirestorePartidaRepository()

    private val _estadoGuardado = MutableStateFlow<Result<String>?>(null)
    val estadoGuardado: StateFlow<Result<String>?> = _estadoGuardado

    /** Guarda [partida] en Firestore y actualiza [estadoGuardado] con el resultado. */
    fun guardarPartida(partida: Partida) {
        viewModelScope.launch {
            val resultado = try {
                repository.guardarPartida(partida)
            } catch (e: Exception) {
                Result.failure(e)
            }

            _estadoGuardado.value = resultado
        }
    }
}