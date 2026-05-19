package com.example.professionalnuzlocker.data.model.enum_classes

/** Tipo de entrenador que puede causar la muerte de un Pokémon en el Nuzlocke, con el nombre que se muestra en pantalla. */
enum class TipoEntrenador(val displayName: String) {
    RIVAL("Rival"),
    LIDER_GIMNASIO("Líder de Gimnasio"),
    LIDER_EQUIPO_PLASMA("Líder del Equipo Plasma"),
    RECLUTA_EQUIPO_PLASMA("Recluta del Equipo Plasma"),
    SECUAZ_GIMNASIO("Secuaz del Gimnasio"),
    ENTRENADOR_NORMAL("Entrenador Normal"),
    POKEMON_SALVAJE("Pokémon Salvaje"),
    ALTO_MANDO("Alto Mando"),
    CAMPEON("Campeón")
}
