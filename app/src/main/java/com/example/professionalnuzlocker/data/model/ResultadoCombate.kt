package com.example.professionalnuzlocker.data.model

data class ResultadoCombate(
    val combateId: Int = 0,
    val derrotado: Boolean = false,
    val equipoUsado: List<String> = emptyList()
)
