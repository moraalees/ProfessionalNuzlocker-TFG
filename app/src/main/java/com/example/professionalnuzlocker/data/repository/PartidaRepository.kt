package com.example.professionalnuzlocker.data.repository

import com.example.professionalnuzlocker.data.model.EncuentroRuta
import com.example.professionalnuzlocker.data.model.Partida
import com.example.professionalnuzlocker.data.model.PokemonCapturado
import com.example.professionalnuzlocker.data.model.ResultadoCombate

/**
 * Contrato de persistencia de una partida Nuzlocke.
 *
 * Cubre el ciclo de vida completo: crear ([guardarPartida]), consultar ([existePartida],
 * [cargarPartida]), actualizar en los dos momentos clave del juego ([actualizarEncuentrosYEquipo]
 * tras registrar un encuentro y [actualizarResultadosCombates] tras un combate), y finalizar
 * ([marcarFinDeLocke]). También controla el contador de consultas a la IA ([incrementarConsultasIA]).
 * La implementación concreta es [FirestorePartidaRepository].
 */
interface PartidaRepository {
    /** Persiste una nueva partida y devuelve el ID del documento generado. */
    suspend fun guardarPartida(partida: Partida): Result<String>

    /** Comprueba si el usuario ya tiene alguna partida guardada. */
    suspend fun existePartida(): Result<Boolean>

    /** Elimina todas las partidas del usuario. */
    suspend fun borrarPartidas(): Result<Unit>

    /** Carga la partida activa y devuelve su ID junto al objeto deserializado. */
    suspend fun cargarPartida(): Result<Pair<String, Partida>>

    /** Actualiza los encuentros de rutas, el equipo/PC/muertos y las vidas tras registrar un encuentro. */
    suspend fun actualizarEncuentrosYEquipo(
        docId: String,
        encuentros: List<EncuentroRuta>,
        equipo: List<PokemonCapturado>,
        pc: List<PokemonCapturado>,
        muertos: List<PokemonCapturado>,
        vidas: Int
    ): Result<Unit>

    /** Actualiza los resultados de combates, el equipo, los muertos y las vidas tras un combate importante. */
    suspend fun actualizarResultadosCombates(
        docId: String,
        resultados: List<ResultadoCombate>,
        equipo: List<PokemonCapturado>,
        muertos: List<PokemonCapturado>,
        vidas: Int
    ): Result<Unit>

    /** Marca la partida como terminada (finDeLocke = true). */
    suspend fun marcarFinDeLocke(docId: String): Result<Unit>

    /** Incrementa el contador de consultas a la IA de la partida. */
    suspend fun incrementarConsultasIA(docId: String): Result<Unit>
}
