package com.example.professionalnuzlocker.data.repository

import com.example.professionalnuzlocker.data.model.EncuentroRuta
import com.example.professionalnuzlocker.data.model.Partida
import com.example.professionalnuzlocker.data.model.PokemonCapturado
import com.example.professionalnuzlocker.data.model.ResultadoCombate

interface PartidaRepository {
    suspend fun guardarPartida(partida: Partida): Result<String>

    suspend fun existePartida(): Result<Boolean>

    suspend fun borrarPartidas(): Result<Unit>

    suspend fun cargarPartida(): Result<Pair<String, Partida>>

    suspend fun actualizarEncuentrosYEquipo(
        docId: String,
        encuentros: List<EncuentroRuta>,
        equipo: List<PokemonCapturado>,
        pc: List<PokemonCapturado>,
        muertos: List<PokemonCapturado>,
        vidas: Int
    ): Result<Unit>

    suspend fun actualizarResultadosCombates(
        docId: String,
        resultados: List<ResultadoCombate>,
        equipo: List<PokemonCapturado>,
        muertos: List<PokemonCapturado>,
        vidas: Int
    ): Result<Unit>

    suspend fun marcarFinDeLocke(docId: String): Result<Unit>

    suspend fun incrementarConsultasIA(docId: String): Result<Unit>
}
