package com.example.professionalnuzlocker.data.model

/** Resultado registrado de un combate importante: si el jugador fue derrotado y con qué equipo lo intentó. */
data class ResultadoCombate(
    val combateId: Int = 0,
    val derrotado: Boolean = false,
    val equipoUsado: List<String> = emptyList()
)
