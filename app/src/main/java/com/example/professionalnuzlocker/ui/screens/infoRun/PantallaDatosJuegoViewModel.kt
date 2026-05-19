package com.example.professionalnuzlocker.ui.screens.infoRun

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.professionalnuzlocker.data.model.Partida
import com.example.professionalnuzlocker.data.model.enum_classes.EstadoPokemon
import com.example.professionalnuzlocker.data.remote.FirestorePartidaRepository
import kotlinx.coroutines.launch

/**
 * ViewModel de [PantallaDatosJuego] que carga la partida activa desde Firestore y permite
 * gestionar el equipo del jugador.
 *
 * Expone [partida] (snapshot de la partida actual) y [cargando] (indicador de carga inicial).
 * Las operaciones principales son [actualizarDatosPokemon] (editar mote, nivel y habilidad),
 * [moverPCaEquipo] (pasar un Pokémon del PC al equipo), [intercambiarConEquipo] (sustituir
 * un miembro del equipo por uno del PC) y [evolucionarPokemon] (cambiar la especie del
 * Pokémon y actualizar sus datos). Todas persisten cambios en Firestore.
 */
class PantallaDatosJuegoViewModel : ViewModel() {

    private val repository = FirestorePartidaRepository()
    private var partidaDocId: String? = null

    var partida by mutableStateOf<Partida?>(null)
        private set
    var cargando by mutableStateOf(true)
        private set

    init {
        cargarPartida()
    }

    /** Carga la partida activa desde Firestore y almacena el ID del documento. */
    private fun cargarPartida() {
        viewModelScope.launch {
            repository.cargarPartida().onSuccess { (docId, p) ->
                partidaDocId = docId
                partida = p
            }
            cargando = false
        }
    }

    fun actualizarDatosPokemon(pokemonId: String, mote: String?, nivel: Int, habilidad: String) {
        val p = partida ?: return
        val docId = partidaDocId ?: return

        val equipoActualizado = p.equipo.map { cap ->
            if (cap.id == pokemonId) cap.copy(mote = mote, nivel = nivel, habilidad = habilidad) else cap
        }.toMutableList()

        partida = p.copy(equipo = equipoActualizado)

        viewModelScope.launch {
            repository.actualizarEncuentrosYEquipo(
                docId = docId,
                encuentros = p.encuentrosRutas,
                equipo = equipoActualizado,
                pc = p.pc,
                muertos = p.muertos,
                vidas = p.vidas
            )
        }
    }

    fun moverPCaEquipo(pokemonId: String) {
        val p = partida ?: return
        val docId = partidaDocId ?: return
        val pokemon = p.pc.find { it.id == pokemonId } ?: return

        val pcActualizado = p.pc.filter { it.id != pokemonId }.toMutableList()
        val equipoActualizado = (p.equipo + pokemon.copy(estado = EstadoPokemon.EQUIPO)).toMutableList()

        partida = p.copy(equipo = equipoActualizado, pc = pcActualizado)

        viewModelScope.launch {
            repository.actualizarEncuentrosYEquipo(
                docId = docId,
                encuentros = p.encuentrosRutas,
                equipo = equipoActualizado,
                pc = pcActualizado,
                muertos = p.muertos,
                vidas = p.vidas
            )
        }
    }

    fun intercambiarConEquipo(pokemonPcId: String, pokemonEquipoId: String) {
        val p = partida ?: return
        val docId = partidaDocId ?: return
        val pokemonPC = p.pc.find { it.id == pokemonPcId } ?: return
        val pokemonEquipo = p.equipo.find { it.id == pokemonEquipoId } ?: return

        val pcActualizado = (p.pc.filter { it.id != pokemonPcId } + pokemonEquipo.copy(estado = EstadoPokemon.PC)).toMutableList()
        val equipoActualizado = p.equipo.map {
            if (it.id == pokemonEquipoId) pokemonPC.copy(estado = EstadoPokemon.EQUIPO) else it
        }.toMutableList()

        partida = p.copy(equipo = equipoActualizado, pc = pcActualizado)

        viewModelScope.launch {
            repository.actualizarEncuentrosYEquipo(
                docId = docId,
                encuentros = p.encuentrosRutas,
                equipo = equipoActualizado,
                pc = pcActualizado,
                muertos = p.muertos,
                vidas = p.vidas
            )
        }
    }

    fun evolucionarPokemon(pokemonId: String, nuevaEspecieId: Int, mote: String?, nivel: Int, habilidad: String) {
        val p = partida ?: return
        val docId = partidaDocId ?: return

        val equipoActualizado = p.equipo.map { cap ->
            if (cap.id == pokemonId) cap.copy(especieId = nuevaEspecieId, mote = mote, nivel = nivel, habilidad = habilidad) else cap
        }.toMutableList()

        partida = p.copy(equipo = equipoActualizado)

        viewModelScope.launch {
            repository.actualizarEncuentrosYEquipo(
                docId = docId,
                encuentros = p.encuentrosRutas,
                equipo = equipoActualizado,
                pc = p.pc,
                muertos = p.muertos,
                vidas = p.vidas
            )
        }
    }
}
