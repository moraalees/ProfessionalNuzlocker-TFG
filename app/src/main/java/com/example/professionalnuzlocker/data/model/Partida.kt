package com.example.professionalnuzlocker.data.model

import com.google.firebase.Timestamp

data class Partida(
    val versionJuego: String? = null,
    val nombreJugador: String = "",
    val sexoJugador: String? = null,
    val pokemonInicial: String? = null,
    val medallas: MutableList<Int> = mutableListOf(),
    val equipo: MutableList<PokemonCapturado> = mutableListOf(),
    val pc: MutableList<PokemonCapturado> = mutableListOf(),
    val muertos: MutableList<PokemonCapturado> = mutableListOf(),
    val resultadosCombates: MutableList<ResultadoCombate> = mutableListOf(),
    val encuentrosRutas: MutableList<EncuentroRuta> = mutableListOf(),
    val createdAt: Timestamp? = null,
    val vidas: Int = 10,
    var finDeLocke: Boolean = false,
    val consultasIA: Int = 0
)
