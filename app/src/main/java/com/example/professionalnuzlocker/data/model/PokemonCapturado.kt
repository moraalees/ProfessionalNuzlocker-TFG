package com.example.professionalnuzlocker.data.model

import com.example.professionalnuzlocker.data.model.enum_classes.EstadoPokemon
import com.google.firebase.Timestamp

data class PokemonCapturado(
    val id: String = "",
    val especieId: Int = 0,
    var mote: String? = null,
    var nivel: Int = 1,
    val habilidad: String = "",
    var objeto: String? = null,
    val rutaId: String = "",
    var estado: EstadoPokemon = EstadoPokemon.EQUIPO,
    var causaMuerte: CausaMuerte? = null,
    val fechaCaptura: Timestamp? = null,
)
