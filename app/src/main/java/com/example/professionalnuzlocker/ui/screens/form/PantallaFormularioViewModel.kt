package com.example.professionalnuzlocker.ui.screens.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.professionalnuzlocker.data.remote.FirestorePartidaRepository
import com.example.professionalnuzlocker.data.model.Partida
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PantallaFormularioViewModel : ViewModel() {

    private val repository = FirestorePartidaRepository()

    private val _estadoGuardado = MutableStateFlow<Result<String>?>(null)
    val estadoGuardado: StateFlow<Result<String>?> = _estadoGuardado

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