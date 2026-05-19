package com.example.professionalnuzlocker.ui.screens.stats

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.professionalnuzlocker.data.model.CombateImportante
import com.example.professionalnuzlocker.data.model.Pokemon
import com.example.professionalnuzlocker.data.model.PokemonCapturado
import com.example.professionalnuzlocker.data.model.enum_classes.ResultadoEncuentro
import com.example.professionalnuzlocker.data.model.enum_classes.Rutas
import com.example.professionalnuzlocker.data.model.enum_classes.TipoPokemon
import com.example.professionalnuzlocker.data.remote.FirestorePartidaRepository
import com.example.professionalnuzlocker.data.repository.CombateRepository
import com.example.professionalnuzlocker.data.repository.Pokedex
import kotlinx.coroutines.launch

/** Entrada del ranking de Pokémon más usados: el capturado, su especie de la Pokédex y el número de combates en los que participó. */
data class EntradaUsado(val pokemon: PokemonCapturado, val especie: Pokemon, val veces: Int)
/** Entrada del ranking de combates más mortales: el combate y el número de Pokémon del jugador que murieron en él. */
data class EntradaMortal(val combate: CombateImportante, val muertes: Int)
/** Entrada del ranking de rivales más letales: la especie del Pokémon asesino, los ataques que usó y el total de víctimas. */
data class EntradaAsesino(val especie: Pokemon, val ataques: List<String>, val victimas: Int)

/** Snapshot de todas las estadísticas calculadas de la partida, listo para ser mostrado en [PantallaEstadisticas] y exportado a PDF. */
data class EstadisticasData(
    val esVictoria: Boolean,
    val vidas: Int,
    val totalRutas: Int,
    val capturadas: Int,
    val perdidas: Int,
    val topUsados: List<EntradaUsado>,
    val topCombatesMortales: List<EntradaMortal>,
    val topAsesinos: List<EntradaAsesino>,
    val distribucionTipos: Map<TipoPokemon, Int>,
    val consultasIA: Int
)

/**
 * ViewModel de [PantallaEstadisticas] que carga la partida desde Firestore y calcula
 * el objeto [EstadisticasData] con todos los indicadores de la aventura.
 *
 * Expone [datos] (resultado del cálculo, `null` mientras [cargando] es `true`). Los cálculos
 * incluyen: resultado de victoria, capturas/pérdidas por ruta, top 3 de Pokémon más usados,
 * top 3 de combates más mortales, top 3 de rivales más letales, distribución de tipos
 * y total de consultas a la IA.
 */
class PantallaEstadisticasViewModel : ViewModel() {

    private val repository = FirestorePartidaRepository()

    var cargando by mutableStateOf(true)
        private set

    var datos by mutableStateOf<EstadisticasData?>(null)
        private set

    init { cargar() }

    private fun cargar() {
        viewModelScope.launch {
            repository.cargarPartida().onSuccess { (_, partida) ->
                val pokedex = Pokedex.getPokemon()
                val todos = partida.equipo + partida.pc + partida.muertos
                val muertosIds = partida.muertos.map { it.id }.toSet()

                val esVictoria = partida.vidas >= 0 &&
                    partida.resultadosCombates.none { !it.derrotado }

                val totalRutas = Rutas.entries.size
                val capturadas = partida.encuentrosRutas.count {
                    it.resultado == ResultadoEncuentro.CAPTURADO.name
                }
                val perdidas = partida.encuentrosRutas.count {
                    it.resultado == ResultadoEncuentro.PERDIDO.name
                }

                val usoCounts = mutableMapOf<String, Int>()
                partida.resultadosCombates.forEach { res ->
                    res.equipoUsado.forEach { id -> usoCounts[id] = (usoCounts[id] ?: 0) + 1 }
                }
                val topUsados = usoCounts.entries
                    .sortedByDescending { it.value }
                    .take(3)
                    .mapNotNull { (id, count) ->
                        val cap = todos.find { it.id == id } ?: return@mapNotNull null
                        val esp = pokedex.find { it.idPokedex == cap.especieId } ?: return@mapNotNull null
                        EntradaUsado(cap, esp, count)
                    }

                val combates = CombateRepository.generarListaCombates(partida)
                val topCombates = partida.resultadosCombates
                    .map { res ->
                        val muertes = res.equipoUsado.count { it in muertosIds }
                        val combate = combates.find { it.id == res.combateId }
                        combate to muertes
                    }
                    .filter { (c, d) -> c != null && d > 0 }
                    .sortedByDescending { it.second }
                    .take(3)
                    .map { (c, d) -> EntradaMortal(c!!, d) }

                val victimCounts = mutableMapOf<Int, Int>()
                partida.muertos.forEach { dead ->
                    dead.causaMuerte?.let { cm ->
                        victimCounts[cm.idPokemonAsesino] = (victimCounts[cm.idPokemonAsesino] ?: 0) + 1
                    }
                }
                val topAsesinos = victimCounts.entries
                    .sortedByDescending { it.value }
                    .take(3)
                    .mapNotNull { (speciesId, victimas) ->
                        val esp = pokedex.find { it.idPokedex == speciesId } ?: return@mapNotNull null
                        val ataques = partida.muertos
                            .filter { it.causaMuerte?.idPokemonAsesino == speciesId }
                            .mapNotNull { it.causaMuerte?.ataque }
                            .distinct()
                        EntradaAsesino(esp, ataques, victimas)
                    }

                val tipoCounts = mutableMapOf<TipoPokemon, Int>()

                todos.forEach { cap ->
                    val pokemon = pokedex.find { it.idPokedex == cap.especieId } ?: return@forEach
                    tipoCounts[pokemon.tipo1] = (tipoCounts[pokemon.tipo1] ?: 0) + 1
                    pokemon.tipo2?.let { tipo2 ->
                        tipoCounts[tipo2] = (tipoCounts[tipo2] ?: 0) + 1
                    }
                }

                datos = EstadisticasData(
                    esVictoria = esVictoria,
                    vidas = partida.vidas,
                    totalRutas = totalRutas,
                    capturadas = capturadas,
                    perdidas = perdidas,
                    topUsados = topUsados,
                    topCombatesMortales = topCombates,
                    topAsesinos = topAsesinos,
                    distribucionTipos = tipoCounts,
                    consultasIA = partida.consultasIA
                )
            }
            cargando = false
        }
    }
}
