package com.example.professionalnuzlocker.ui.screens.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.professionalnuzlocker.data.model.CausaMuerte
import com.example.professionalnuzlocker.data.model.CombateImportante
import com.example.professionalnuzlocker.data.model.EncuentroRuta
import com.example.professionalnuzlocker.data.model.EstadoRutaRegistro
import com.example.professionalnuzlocker.data.model.Partida
import com.example.professionalnuzlocker.data.model.Pokemon
import com.example.professionalnuzlocker.data.model.PokemonCapturado
import com.example.professionalnuzlocker.data.model.ResultadoCombate
import com.example.professionalnuzlocker.data.model.enum_classes.EstadoPokemon
import com.example.professionalnuzlocker.data.model.enum_classes.ResultadoEncuentro
import com.example.professionalnuzlocker.data.model.enum_classes.Rutas
import com.example.professionalnuzlocker.data.remote.FirestorePartidaRepository
import com.example.professionalnuzlocker.data.repository.CombateRepository
import com.example.professionalnuzlocker.data.repository.Pokedex
import com.google.firebase.Timestamp
import kotlinx.coroutines.launch
import java.util.UUID

sealed class MotivoFin {
    object SinVidas : MotivoFin()
    object CombatePerdido : MotivoFin()
    object Victoria : MotivoFin()
}

class PantallaRegistroViewModel : ViewModel() {

    private val repository = FirestorePartidaRepository()

    private var partidaDocId: String? = null
    private var partida: Partida? = null

    private val _estados = mutableStateMapOf<Rutas, EstadoRutaRegistro>().apply {
        Rutas.entries.forEach { put(it, EstadoRutaRegistro.RutaLibre) }
    }
    val estados: Map<Rutas, EstadoRutaRegistro> = _estados

    private val _equipoActual = mutableStateListOf<PokemonCapturado>()
    val equipoActual: List<PokemonCapturado> = _equipoActual

    private val _listaItems = mutableStateListOf<RegistroItem>()
    val listaItems: List<RegistroItem> = _listaItems

    private val _resultadosCombates = mutableStateListOf<ResultadoCombate>()
    val resultadosCombates: List<ResultadoCombate> = _resultadosCombates

    private val _combatesOrdenados = mutableStateListOf<CombateImportante>()
    val combatesOrdenados: List<CombateImportante> = _combatesOrdenados

    var partidaInfo by mutableStateOf<Partida?>(null)
        private set

    private var combatesAgrupados: Map<Rutas, List<CombateImportante>> = emptyMap()

    var cargando by mutableStateOf(true)
        private set

    var vidas by mutableStateOf(10)
        private set

    var motivoFinDeJuego by mutableStateOf<MotivoFin?>(null)
        private set

    init {
        cargarDesdeFirestore()
    }

    private fun cargarDesdeFirestore() {
        viewModelScope.launch {
            val result = repository.cargarPartida()
            result.onSuccess { (docId, partidaCargada) ->
                partidaDocId = docId
                partida = partidaCargada
                vidas = partidaCargada.vidas

                _equipoActual.clear()
                _equipoActual.addAll(partidaCargada.equipo)

                partidaCargada.encuentrosRutas.forEach { encuentro ->
                    val ruta = Rutas.entries.find { it.nombreRuta == encuentro.rutaId } ?: return@forEach
                    when (encuentro.resultado) {
                        ResultadoEncuentro.CAPTURADO.name -> {
                            val pokCap = (partidaCargada.equipo + partidaCargada.pc + partidaCargada.muertos)
                                .find { it.id == encuentro.pokemonCapturadoId }
                            val pokemon = pokCap?.let { cap ->
                                Pokedex.getPokemon().find { it.idPokedex == cap.especieId }
                            }
                            if (pokemon != null && pokCap != null) {
                                _estados[ruta] = EstadoRutaRegistro.PokemonCapturado(
                                    pokemon = pokemon,
                                    mote = pokCap.mote ?: "",
                                    nivel = pokCap.nivel.toString()
                                )
                            }
                        }
                        ResultadoEncuentro.PERDIDO.name -> {
                            val especieId = encuentro.pokemonCapturadoId?.toIntOrNull()
                            val pokemon = especieId?.let { id ->
                                Pokedex.getPokemon().find { it.idPokedex == id }
                            }
                            if (pokemon != null) {
                                _estados[ruta] = EstadoRutaRegistro.PokemonDebilitado(pokemon)
                            }
                        }
                        else -> {}
                    }
                }

                _resultadosCombates.clear()
                _resultadosCombates.addAll(partidaCargada.resultadosCombates)

                val combates = CombateRepository.generarListaCombates(partidaCargada)
                _combatesOrdenados.clear()
                _combatesOrdenados.addAll(combates.sortedBy { it.id })
                combatesAgrupados = combates.groupBy { it.despuesDe }
                construirListaItems()

                partidaInfo = partidaCargada

                val motivoFromData = when {
                    partidaCargada.vidas < 0 -> MotivoFin.SinVidas
                    partidaCargada.resultadosCombates.any { !it.derrotado } -> MotivoFin.CombatePerdido
                    partidaCargada.resultadosCombates.any { it.combateId == 30 && it.derrotado } -> MotivoFin.Victoria
                    else -> null
                }
                motivoFinDeJuego = motivoFromData ?: if (partidaCargada.finDeLocke) MotivoFin.SinVidas else null

                if (_estados[Rutas.PUEBLO_ARCILLA] is EstadoRutaRegistro.RutaLibre) {
                    val nombreInicial = partidaCargada.pokemonInicial
                    if (!nombreInicial.isNullOrBlank()) {
                        val starter = Pokedex.getPokemon()
                            .find { it.nombre.equals(nombreInicial, ignoreCase = true) }
                        if (starter != null) {
                            _estados[Rutas.PUEBLO_ARCILLA] = EstadoRutaRegistro.PokemonElegido(
                                pokemon = starter,
                                esInicial = true
                            )
                        }
                    }
                }
            }
            cargando = false
        }
    }

    private fun construirListaItems() {
        val items = Rutas.entries.flatMap { ruta ->
            listOf(RegistroItem.RutaItem(ruta)) +
                (combatesAgrupados[ruta]?.map { RegistroItem.CombateItem(it) } ?: emptyList())
        }
        _listaItems.clear()
        _listaItems.addAll(items)
    }

    fun getRutaActiva(): Rutas? = Rutas.entries.firstOrNull {
        _estados[it] is EstadoRutaRegistro.RutaLibre || _estados[it] is EstadoRutaRegistro.PokemonElegido
    }

    fun seleccionarPokemon(ruta: Rutas, pokemon: Pokemon) {
        if (_estados[ruta] is EstadoRutaRegistro.RutaLibre) {
            _estados[ruta] = EstadoRutaRegistro.PokemonElegido(pokemon)
        }
    }

    fun confirmarCaptura(ruta: Rutas, mote: String, nivel: String) {
        val estado = _estados[ruta]
        if (estado !is EstadoRutaRegistro.PokemonElegido) return

        _estados[ruta] = EstadoRutaRegistro.PokemonCapturado(estado.pokemon, mote.trim(), nivel.trim())

        viewModelScope.launch {
            val docId = partidaDocId ?: return@launch
            val p = partida ?: return@launch

            val pokId = UUID.randomUUID().toString()
            val vaAlEquipo = p.equipo.size < 6
            val pokCapturado = PokemonCapturado(
                id = pokId,
                especieId = estado.pokemon.idPokedex,
                mote = mote.trim(),
                nivel = nivel.trim().toIntOrNull() ?: 1,
                rutaId = ruta.nombreRuta,
                estado = if (vaAlEquipo) EstadoPokemon.EQUIPO else EstadoPokemon.PC,
                fechaCaptura = Timestamp.now()
            )

            if (vaAlEquipo) {
                p.equipo.add(pokCapturado)
                _equipoActual.add(pokCapturado)
            } else {
                p.pc.add(pokCapturado)
            }

            val encuentro = EncuentroRuta(
                rutaId = ruta.nombreRuta,
                resultado = ResultadoEncuentro.CAPTURADO.name,
                pokemonCapturadoId = pokId
            )
            p.encuentrosRutas.removeAll { it.rutaId == ruta.nombreRuta }
            p.encuentrosRutas.add(encuentro)

            repository.actualizarEncuentrosYEquipo(
                docId = docId,
                encuentros = p.encuentrosRutas,
                equipo = p.equipo,
                pc = p.pc,
                muertos = p.muertos,
                vidas = p.vidas
            )
        }
    }

    fun confirmarDebilitado(ruta: Rutas) {
        val estado = _estados[ruta]
        if (estado !is EstadoRutaRegistro.PokemonElegido) return

        _estados[ruta] = EstadoRutaRegistro.PokemonDebilitado(estado.pokemon)

        viewModelScope.launch {
            val docId = partidaDocId ?: return@launch
            val p = partida ?: return@launch

            val encuentro = EncuentroRuta(
                rutaId = ruta.nombreRuta,
                resultado = ResultadoEncuentro.PERDIDO.name,
                pokemonCapturadoId = estado.pokemon.idPokedex.toString()
            )
            p.encuentrosRutas.removeAll { it.rutaId == ruta.nombreRuta }
            p.encuentrosRutas.add(encuentro)

            repository.actualizarEncuentrosYEquipo(
                docId = docId,
                encuentros = p.encuentrosRutas,
                equipo = p.equipo,
                pc = p.pc,
                muertos = p.muertos,
                vidas = p.vidas
            )
        }
    }

    fun moverEquipoAPC(pokemonId: String) {
        val p = partida ?: return
        val docId = partidaDocId ?: return
        val pokemon = p.equipo.find { it.id == pokemonId } ?: return

        val equipoActualizado = p.equipo.filter { it.id != pokemonId }.toMutableList()
        val pcActualizado = (p.pc + pokemon.copy(estado = EstadoPokemon.PC)).toMutableList()

        partida = p.copy(equipo = equipoActualizado, pc = pcActualizado)
        _equipoActual.clear()
        _equipoActual.addAll(equipoActualizado)

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

    fun registrarBatalla(combateId: Int, ganada: Boolean, equipoUsadoIds: List<String>) {
        val p = partida ?: return
        val docId = partidaDocId ?: return

        p.resultadosCombates.removeAll { it.combateId == combateId }
        p.resultadosCombates.add(ResultadoCombate(
            combateId = combateId,
            derrotado = ganada,
            equipoUsado = equipoUsadoIds
        ))
        _resultadosCombates.clear()
        _resultadosCombates.addAll(p.resultadosCombates)

        val motivoAntes = motivoFinDeJuego
        if (motivoFinDeJuego == null) {
            motivoFinDeJuego = when {
                !ganada -> MotivoFin.CombatePerdido
                combateId == 30 -> MotivoFin.Victoria
                else -> null
            }
        }
        val lockeTerminaAhora = motivoAntes == null && motivoFinDeJuego != null
        if (lockeTerminaAhora) {
            viewModelScope.launch { repository.marcarFinDeLocke(docId) }
        }

        viewModelScope.launch {
            repository.actualizarResultadosCombates(
                docId = docId,
                resultados = p.resultadosCombates,
                equipo = p.equipo,
                muertos = p.muertos,
                vidas = p.vidas
            )
        }
    }

    fun matarPokemon(pokemonId: String, tipoEntrenador: String, ataque: String?, idPokemonAsesino: Int) {
        val p = partida ?: return
        val docId = partidaDocId ?: return
        val pokemon = p.equipo.find { it.id == pokemonId } ?: return

        val causaMuerte = CausaMuerte(
            tipoEntrenador = tipoEntrenador,
            ataque = ataque,
            idPokemonAsesino = idPokemonAsesino,
            fechaMuerte = Timestamp.now()
        )
        val nuevasVidas = p.vidas - 1
        val equipoActualizado = p.equipo.filter { it.id != pokemonId }.toMutableList()
        val muertosActualizado = (p.muertos + pokemon.copy(
            estado = EstadoPokemon.MUERTO,
            causaMuerte = causaMuerte
        )).toMutableList()

        partida = p.copy(equipo = equipoActualizado, muertos = muertosActualizado, vidas = nuevasVidas)
        _equipoActual.clear()
        _equipoActual.addAll(equipoActualizado)
        vidas = nuevasVidas

        if (nuevasVidas < 0 && motivoFinDeJuego == null) {
            motivoFinDeJuego = MotivoFin.SinVidas
            viewModelScope.launch { repository.marcarFinDeLocke(docId) }
        }

        viewModelScope.launch {
            repository.actualizarEncuentrosYEquipo(
                docId = docId,
                encuentros = p.encuentrosRutas,
                equipo = equipoActualizado,
                pc = p.pc,
                muertos = muertosActualizado,
                vidas = nuevasVidas
            )
        }
    }
}
