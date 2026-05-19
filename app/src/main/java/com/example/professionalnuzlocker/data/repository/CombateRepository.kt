package com.example.professionalnuzlocker.data.repository

import com.example.professionalnuzlocker.R
import com.example.professionalnuzlocker.data.model.Partida
import com.example.professionalnuzlocker.data.model.CombateImportante
import com.example.professionalnuzlocker.data.model.enum_classes.PokemonInicial
import com.example.professionalnuzlocker.data.model.PokemonRival
import com.example.professionalnuzlocker.data.model.enum_classes.Rutas
import com.example.professionalnuzlocker.data.model.enum_classes.TipoCombate
import com.example.professionalnuzlocker.data.model.enum_classes.TipoPokemon
import com.example.professionalnuzlocker.data.model.enum_classes.VersionJuego

/**
 * Repositorio singleton con los 30 combates importantes del Nuzlocke de Pokémon Negro/Blanco.
 *
 * Incluye peleas contra rivales (Bel, Cheren), líderes de gimnasio (Aloe, Camus, Camila…),
 * el Equipo Plasma (N, Ghechis) y la Liga Pokémon (Alto Mando + Campeón).
 *
 * Los equipos rivales se adaptan al Pokémon inicial del jugador y, en los combates 23 y 29,
 * también a la versión del juego (Negro/Blanco). Llama a [generarListaCombates] para obtener
 * la lista ordenada de combates.
 */
object CombateRepository {
    /** Regenera y devuelve la lista completa de combates importantes a partir del estado actual de la partida. */
    fun generarListaCombates(estado: Partida): List<CombateImportante> {
        listaCombatesImportantes.clear()

        val combate1 = configurarCombate1(estado.pokemonInicial)
        val combate2 = configurarCombate2(estado.pokemonInicial)
        val combate3 = configurarCombate3()
        val combate4 = configurarCombate4(estado.pokemonInicial)
        val combate5 = configurarCombate5(estado.pokemonInicial)
        val combate6 = configurarCombate6(estado.pokemonInicial)
        val combate7 = configurarCombate7(estado.pokemonInicial)
        val combate8 = configurarCombate8()
        val combate9 = configurarCombate9()
        val combate10 = configurarCombate10()
        val combate11 = configurarCombate11(estado.pokemonInicial)
        val combate12 = configurarCombate12(estado.pokemonInicial)
        val combate13 = configurarCombate13()
        val combate14 = configurarCombate14()
        val combate15 = configurarCombate15(estado.pokemonInicial)
        val combate16 = configurarCombate16()
        val combate17 = configurarCombate17(estado.pokemonInicial)
        val combate18 = configurarCombate18()
        val combate19 = configurarCombate19()
        val combate20 = configurarCombate20(estado.pokemonInicial)
        val combate21 = configurarCombate21()
        val combate22 = configurarCombate22(estado.pokemonInicial)
        val combate23 = configurarCombate23(estado.versionJuego)
        val combate24 = configurarCombate24(estado.pokemonInicial)
        val combate25 = configurarCombate25()
        val combate26 = configurarCombate26()
        val combate27 = configurarCombate27()
        val combate28 = configurarCombate28()
        val combate29 = configurarCombate29(estado.versionJuego)
        val combate30 = configurarCombate30()

        listaCombatesImportantes.add(combate1)
        listaCombatesImportantes.add(combate2)
        listaCombatesImportantes.add(combate3)
        listaCombatesImportantes.add(combate4)
        listaCombatesImportantes.add(combate5)
        listaCombatesImportantes.add(combate6)
        listaCombatesImportantes.add(combate7)
        listaCombatesImportantes.add(combate8)
        listaCombatesImportantes.add(combate9)
        listaCombatesImportantes.add(combate10)
        listaCombatesImportantes.add(combate11)
        listaCombatesImportantes.add(combate12)
        listaCombatesImportantes.add(combate13)
        listaCombatesImportantes.add(combate14)
        listaCombatesImportantes.add(combate15)
        listaCombatesImportantes.add(combate16)
        listaCombatesImportantes.add(combate17)
        listaCombatesImportantes.add(combate18)
        listaCombatesImportantes.add(combate19)
        listaCombatesImportantes.add(combate20)
        listaCombatesImportantes.add(combate21)
        listaCombatesImportantes.add(combate22)
        listaCombatesImportantes.add(combate23)
        listaCombatesImportantes.add(combate24)
        listaCombatesImportantes.add(combate25)
        listaCombatesImportantes.add(combate26)
        listaCombatesImportantes.add(combate27)
        listaCombatesImportantes.add(combate28)
        listaCombatesImportantes.add(combate29)
        listaCombatesImportantes.add(combate30)

        return listaCombatesImportantes
    }

    private val listaCombatesImportantes: MutableList<CombateImportante> = mutableListOf()

    /** Combate 1 – Bel en Pueblo Arcilla. Primera pelea del juego; el rival usa el inicial con ventaja de tipo sobre el jugador. */
    private fun configurarCombate1(inicial: String?): CombateImportante {
        return when (inicial) {
            PokemonInicial.SNIVY.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(7)
                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 5,
                    movimientos = mapOf("Placaje" to TipoPokemon.NORMAL, "Látigo" to TipoPokemon.NORMAL),
                    habilidad = "Torrente"
                )

                CombateImportante(
                    1,
                    "Bel",
                    "Pueblo Arcilla",
                    TipoCombate.RIVAL,
                    mutableListOf(primero),
                    R.drawable.bel,
                    Rutas.PUEBLO_ARCILLA
                )
            }
            PokemonInicial.TEPIG.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(1)
                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 5,
                    movimientos = mapOf("Placaje" to TipoPokemon.NORMAL, "Malicioso" to TipoPokemon.NORMAL),
                    habilidad = "Espesura"
                )

                CombateImportante(
                    1,
                    "Bel",
                    "Pueblo Arcilla",
                    TipoCombate.RIVAL,
                    mutableListOf(primero),
                    R.drawable.bel,
                    Rutas.PUEBLO_ARCILLA
                )
            }
            PokemonInicial.OSHAWOTT.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(4)
                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 5,
                    movimientos = mapOf("Placaje" to TipoPokemon.NORMAL, "Látigo" to TipoPokemon.NORMAL),
                    habilidad = "Mar Llamas"
                )

                CombateImportante(
                    1,
                    "Bel",
                    "Pueblo Arcilla",
                    TipoCombate.RIVAL,
                    mutableListOf(primero),
                    R.drawable.bel,
                    Rutas.PUEBLO_ARCILLA
                )
            }
            else -> {
                CombateImportante()
            }
        }
    }

    /** Combate 2 – Cheren en Pueblo Arcilla. Segunda pelea inmediata al inicio del juego. */
    private fun configurarCombate2(inicial: String?): CombateImportante {
        return when (inicial) {
            PokemonInicial.SNIVY.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(4)
                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 5,
                    movimientos = mapOf("Placaje" to TipoPokemon.NORMAL, "Látigo" to TipoPokemon.NORMAL),
                    habilidad = "Mar Llamas"
                )

                CombateImportante(
                    2,
                    "Cheren",
                    "Pueblo Arcilla",
                    TipoCombate.RIVAL,
                    mutableListOf(primero),
                    R.drawable.cheren,
                    Rutas.PUEBLO_ARCILLA
                )
            }
            PokemonInicial.TEPIG.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(7)
                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 5,
                    movimientos = mapOf("Placaje" to TipoPokemon.NORMAL, "Látigo" to TipoPokemon.NORMAL),
                    habilidad = "Torrente"
                )

                CombateImportante(
                    2,
                    "Cheren",
                    "Pueblo Arcilla",
                    TipoCombate.RIVAL,
                    mutableListOf(primero),
                    R.drawable.cheren,
                    Rutas.PUEBLO_ARCILLA
                )
            }
            PokemonInicial.OSHAWOTT.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(1)
                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 5,
                    movimientos = mapOf("Placaje" to TipoPokemon.NORMAL, "Malicioso" to TipoPokemon.NORMAL),
                    habilidad = "Espesura"
                )

                CombateImportante(
                    2,
                    "Cheren",
                    "Pueblo Arcilla",
                    TipoCombate.RIVAL,
                    mutableListOf(primero),
                    R.drawable.cheren,
                    Rutas.PUEBLO_ARCILLA
                )
            }
            else -> {
                CombateImportante()
            }
        }
    }

    private fun configurarCombate3(): CombateImportante {
        val especiePrimero = Pokedex.getPokemonById(15)
        val primero = PokemonRival(
            especie = especiePrimero,
            nivel = 7,
            movimientos = mapOf("Placaje" to TipoPokemon.NORMAL, "Arañazo" to TipoPokemon.NORMAL, "Gruñido" to TipoPokemon.NORMAL, "Ayuda" to TipoPokemon.NORMAL),
            habilidad = "Liviano"
        )

        return CombateImportante(
            3,
            "N",
            "Pueblo Terracota",
            TipoCombate.EQUIPO_PLASMA,
            mutableListOf(primero),
            imagenRival = R.drawable.n,
            Rutas.RUTA_1
        )
    }

    private fun configurarCombate4(inicial: String?): CombateImportante {
        return when (inicial) {
            PokemonInicial.SNIVY.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(12)
                val especieSegundo = Pokedex.getPokemonById(7)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 6,
                    movimientos = mapOf("Placaje" to TipoPokemon.NORMAL, "Malicioso" to TipoPokemon.NORMAL, "Rastreo" to TipoPokemon.NORMAL),
                    habilidad = "Espíritu Vital"
                )

                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 7,
                    movimientos = mapOf("Placaje" to TipoPokemon.NORMAL, "Látigo" to TipoPokemon.NORMAL, "Pistola Agua" to TipoPokemon.AGUA),
                    habilidad = "Torrente"
                )

                CombateImportante(
                    4,
                    "Bel",
                    "Ruta 2",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo),
                    R.drawable.bel,
                    Rutas.RUTA_2
                )
            }
            PokemonInicial.TEPIG.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(12)
                val especieSegundo = Pokedex.getPokemonById(1)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 6,
                    movimientos = mapOf("Placaje" to TipoPokemon.NORMAL, "Malicioso" to TipoPokemon.NORMAL, "Rastreo" to TipoPokemon.NORMAL),
                    habilidad = "Espíritu Vital"
                )

                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 7,
                    movimientos = mapOf("Placaje" to TipoPokemon.NORMAL, "Látigo" to TipoPokemon.NORMAL, "Látigo Cepa" to TipoPokemon.PLANTA),
                    habilidad = "Espesura"
                )

                CombateImportante(
                    4,
                    "Bel",
                    "Ruta 2",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo),
                    R.drawable.bel,
                    Rutas.RUTA_2
                )
            }
            PokemonInicial.OSHAWOTT.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(12)
                val especieSegundo = Pokedex.getPokemonById(4)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 6,
                    movimientos = mapOf("Placaje" to TipoPokemon.NORMAL, "Malicioso" to TipoPokemon.NORMAL, "Rastreo" to TipoPokemon.NORMAL),
                    habilidad = "Espíritu Vital"
                )

                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 7,
                    movimientos = mapOf("Placaje" to TipoPokemon.NORMAL, "Látigo" to TipoPokemon.NORMAL, "Ascuas" to TipoPokemon.FUEGO),
                    habilidad = "Mar LLamas"
                )

                CombateImportante(
                    4,
                    "Bel",
                    "Ruta 2",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo),
                    R.drawable.bel,
                    Rutas.RUTA_2
                )
            }
            else -> {
                CombateImportante()
            }
        }
    }

    private fun configurarCombate5(inicial: String?): CombateImportante {
        return when (inicial) {
            PokemonInicial.SNIVY.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(4)
                val especieSegundo = Pokedex.getPokemonById(15)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 8,
                    movimientos = mapOf("Placaje" to TipoPokemon.NORMAL, "Látigo" to TipoPokemon.NORMAL, "Ascuas" to TipoPokemon.FUEGO),
                    habilidad = "Mar Llamas",
                    objeto = "Baya Aranja"
                )

                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 8,
                    movimientos = mapOf("Gruñido" to TipoPokemon.NORMAL, "Arañazo" to TipoPokemon.NORMAL, "Ayuda" to TipoPokemon.NORMAL),
                    habilidad = "Liviano"
                )

                CombateImportante(
                    5,
                    "Cheren",
                    "Ciudad Gres",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo),
                    R.drawable.cheren,
                    Rutas.SOLAR_DE_LOS_SUENOS
                )
            }
            PokemonInicial.TEPIG.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(7)
                val especieSegundo = Pokedex.getPokemonById(15)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 8,
                    movimientos = mapOf("Placaje" to TipoPokemon.NORMAL, "Látigo" to TipoPokemon.NORMAL, "Pistola Agua" to TipoPokemon.AGUA),
                    habilidad = "Torrente",
                    objeto = "Baya Aranja"
                )

                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 8,
                    movimientos = mapOf("Gruñido" to TipoPokemon.NORMAL, "Arañazo" to TipoPokemon.NORMAL, "Ayuda" to TipoPokemon.NORMAL),
                    habilidad = "Liviano"
                )

                CombateImportante(
                    5,
                    "Cheren",
                    "Ciudad Gres",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo),
                    R.drawable.cheren,
                    Rutas.SOLAR_DE_LOS_SUENOS
                )
            }
            PokemonInicial.OSHAWOTT.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(1)
                val especieSegundo = Pokedex.getPokemonById(15)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 8,
                    movimientos = mapOf("Placaje" to TipoPokemon.NORMAL, "Látigo" to TipoPokemon.NORMAL, "Látigo Cepa" to TipoPokemon.PLANTA),
                    habilidad = "Espesura",
                    objeto = "Baya Aranja"
                )

                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 8,
                    movimientos = mapOf("Gruñido" to TipoPokemon.NORMAL, "Arañazo" to TipoPokemon.NORMAL, "Ayuda" to TipoPokemon.NORMAL),
                    habilidad = "Liviano"
                )

                CombateImportante(
                    5,
                    "Cheren",
                    "Ciudad Gres",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo),
                    R.drawable.cheren,
                    Rutas.SOLAR_DE_LOS_SUENOS
                )
            }
            else -> {
                CombateImportante()
            }
        }
    }

    private fun configurarCombate6(inicial: String?): CombateImportante {
        return when (inicial) {
            PokemonInicial.SNIVY.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(12)
                val especieSegundo = Pokedex.getPokemonById(19)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 12,
                    movimientos = mapOf("Mordisco" to TipoPokemon.SINIESTRO, "Avivar" to TipoPokemon.NORMAL, "Rastreo" to TipoPokemon.NORMAL, "Refuerzo" to TipoPokemon.NORMAL),
                    habilidad = "Recogida"
                )

                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 14,
                    movimientos = mapOf("Calcinación" to TipoPokemon.FUEGO, "Lengüetazo" to TipoPokemon.FANTASMA, "Golpes Furia" to TipoPokemon.NORMAL, "Avivar" to TipoPokemon.NORMAL),
                    habilidad = "Gula"
                )

                CombateImportante(
                    6,
                    "Zeo",
                    "Ciudad Gres",
                    TipoCombate.GIMNASIO,
                    mutableListOf(primero, segundo),
                    R.drawable.zeo,
                    Rutas.SOLAR_DE_LOS_SUENOS
                )
            }
            PokemonInicial.TEPIG.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(12)
                val especieSegundo = Pokedex.getPokemonById(21)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 12,
                    movimientos = mapOf("Mordisco" to TipoPokemon.SINIESTRO, "Avivar" to TipoPokemon.NORMAL, "Rastreo" to TipoPokemon.NORMAL, "Refuerzo" to TipoPokemon.NORMAL),
                    habilidad = "Recogida"
                )

                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 14,
                    movimientos = mapOf("Pistola Agua" to TipoPokemon.AGUA, "Lengüetazo" to TipoPokemon.FANTASMA, "Golpes Furia" to TipoPokemon.NORMAL, "Avivar" to TipoPokemon.NORMAL),
                    habilidad = "Gula"
                )

                CombateImportante(
                    6,
                    "Maíz",
                    "Ciudad Gres",
                    TipoCombate.GIMNASIO,
                    mutableListOf(primero, segundo),
                    R.drawable.maiz,
                    Rutas.SOLAR_DE_LOS_SUENOS
                )
            }
            PokemonInicial.OSHAWOTT.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(12)
                val especieSegundo = Pokedex.getPokemonById(17)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 12,
                    movimientos = mapOf("Mordisco" to TipoPokemon.SINIESTRO, "Avivar" to TipoPokemon.NORMAL, "Rastreo" to TipoPokemon.NORMAL, "Refuerzo" to TipoPokemon.NORMAL),
                    habilidad = "Recogida"
                )

                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 14,
                    movimientos = mapOf("Látigo Cepa" to TipoPokemon.PLANTA, "Lengüetazo" to TipoPokemon.FANTASMA, "Golpes Furia" to TipoPokemon.NORMAL, "Avivar" to TipoPokemon.NORMAL),
                    habilidad = "Gula"
                )

                CombateImportante(
                    6,
                    "Millo",
                    "Ciudad Gres",
                    TipoCombate.GIMNASIO,
                    mutableListOf(primero, segundo),
                    R.drawable.millo,
                    Rutas.SOLAR_DE_LOS_SUENOS
                )
            }
            else -> {
                CombateImportante()
            }
        }
    }

    private fun configurarCombate7(inicial: String?): CombateImportante {
        return when (inicial) {
            PokemonInicial.SNIVY.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(4)
                val especieSegundo = Pokedex.getPokemonById(15)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 14,
                    movimientos = mapOf("Ascuas" to TipoPokemon.FUEGO, "Látigo" to TipoPokemon.NORMAL, "Rizo Defensa" to TipoPokemon.NORMAL, "Rastreo" to TipoPokemon.NORMAL),
                    habilidad = "Mar Llamas",
                    objeto = "Baya Aranja"
                )

                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 12,
                    movimientos = mapOf("Gruñido" to TipoPokemon.NORMAL, "Arañazo" to TipoPokemon.NORMAL, "Ayuda" to TipoPokemon.NORMAL, "Ataque Arena" to TipoPokemon.NORMAL),
                    habilidad = "Liviano"
                )

                CombateImportante(
                    7,
                    "Cheren",
                    "Ruta 3",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo),
                    R.drawable.cheren,
                    Rutas.RUTA_3
                )
            }
            PokemonInicial.TEPIG.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(7)
                val especieSegundo = Pokedex.getPokemonById(15)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 14,
                    movimientos = mapOf("Foco Energía" to TipoPokemon.NORMAL, "Látigo" to TipoPokemon.NORMAL, "Hidrochorro" to TipoPokemon.AGUA, "Pistola Agua" to TipoPokemon.AGUA),
                    habilidad = "Torrente",
                    objeto = "Baya Aranja"
                )

                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 12,
                    movimientos = mapOf("Gruñido" to TipoPokemon.NORMAL, "Arañazo" to TipoPokemon.NORMAL, "Ayuda" to TipoPokemon.NORMAL, "Ataque Arena" to TipoPokemon.TIERRA),
                    habilidad = "Liviano"
                )

                CombateImportante(
                    7,
                    "Cheren",
                    "Ruta 3",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo),
                    R.drawable.cheren,
                    Rutas.RUTA_3
                )
            }
            PokemonInicial.OSHAWOTT.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(1)
                val especieSegundo = Pokedex.getPokemonById(15)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 14,
                    movimientos = mapOf("Desarrollo" to TipoPokemon.NORMAL, "Malicioso" to TipoPokemon.NORMAL, "Látigo Cepa" to TipoPokemon.PLANTA, "Constricción" to TipoPokemon.NORMAL),
                    habilidad = "Espesura",
                    objeto = "Baya Aranja"
                )

                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 12,
                    movimientos = mapOf("Gruñido" to TipoPokemon.NORMAL, "Arañazo" to TipoPokemon.NORMAL, "Ayuda" to TipoPokemon.NORMAL, "Ataque Arena" to TipoPokemon.TIERRA),
                    habilidad = "Liviano"
                )

                CombateImportante(
                    7,
                    "Cheren",
                    "Ruta 3",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo),
                    R.drawable.cheren,
                    Rutas.RUTA_3
                )
            }
            else -> {
                CombateImportante()
            }
        }
    }

    private fun configurarCombate8(): CombateImportante {
        val especiePrimero = Pokedex.getPokemonById(25)
        val especieSegundo = Pokedex.getPokemonById(41)
        val especieTercero = Pokedex.getPokemonById(38)

        val primero = PokemonRival(
            especie = especiePrimero,
            nivel = 13,
            movimientos = mapOf("Malicioso" to TipoPokemon.NORMAL, "Tornado" to TipoPokemon.VOLADOR, "Ataque Rápido" to TipoPokemon.NORMAL, "Gruñido" to TipoPokemon.NORMAL),
            habilidad = "Sacapecho"
        )

        val segundo = PokemonRival(
            especie = especieSegundo,
            nivel = 13,
            movimientos = mapOf("Gruñido" to TipoPokemon.NORMAL, "Rayo Burbuja" to TipoPokemon.AGUA, "Canon" to TipoPokemon.NORMAL, "Supersónico" to TipoPokemon.NORMAL),
            habilidad = "Nado Rápido"
        )

        val tercero = PokemonRival(
            especie = especieTercero,
            nivel = 13,
            movimientos = mapOf("Venganza" to TipoPokemon.NORMAL, "Foco Energía" to TipoPokemon.NORMAL, "Patada Baja" to TipoPokemon.LUCHA, "Malicioso" to TipoPokemon.NORMAL),
            habilidad = "Potencia Bruta"
        )

        return CombateImportante(
            8,
            "N",
            "Ciudad Esmalte",
            TipoCombate.EQUIPO_PLASMA,
            mutableListOf(primero, segundo, tercero),
            imagenRival = R.drawable.n,
            Rutas.CUEVA_MANANTIAL
        )
    }

    private fun configurarCombate9(): CombateImportante {
        val especiePrimero = Pokedex.getPokemonById(13)
        val especieSegundo = Pokedex.getPokemonById(11)

        val primero = PokemonRival(
            especie = especiePrimero,
            nivel = 18,
            movimientos = mapOf("Represalia" to TipoPokemon.NORMAL, "Derribo" to TipoPokemon.NORMAL, "Mordisco" to TipoPokemon.SINIESTRO, "Malicioso" to TipoPokemon.NORMAL),
            habilidad = "Intimidación"
        )

        val segundo = PokemonRival(
            especie = especieSegundo,
            nivel = 20,
            movimientos = mapOf("Triturar" to TipoPokemon.SINIESTRO, "Represalia" to TipoPokemon.NORMAL, "Malicioso" to TipoPokemon.NORMAL, "Hipnosis" to TipoPokemon.PSIQUICO),
            habilidad = "Iluminación"
        )

        return CombateImportante(
            9,
            "Aloe",
            "Ciudad Esmalte",
            TipoCombate.GIMNASIO,
            mutableListOf(primero, segundo),
            imagenRival = R.drawable.aloe,
            Rutas.BOSQUE_AZULEJO
        )
    }

    private fun configurarCombate10(): CombateImportante {
        val especiePrimero = Pokedex.getPokemonById(50)
        val especieSegundo = Pokedex.getPokemonById(63)
        val especieTercero = Pokedex.getPokemonById(48)

        val primero = PokemonRival(
            especie = especiePrimero,
            nivel = 21,
            movimientos = mapOf("Cola Veneno" to TipoPokemon.VENENO, "Persecución" to TipoPokemon.SINIESTRO, "Chirrido" to TipoPokemon.NORMAL, "Estoicismo" to TipoPokemon.BICHO),
            habilidad = "Enjambre"
        )

        val segundo = PokemonRival(
            especie = especieSegundo,
            nivel = 21,
            movimientos = mapOf("Antiaéreo" to TipoPokemon.ROCA, "Finta" to TipoPokemon.SINIESTRO, "Ataque Arena" to TipoPokemon.TIERRA, "Estoicismo" to TipoPokemon.BICHO),
            habilidad = "Caparazón"
        )

        val tercero = PokemonRival(
            especie = especieTercero,
            nivel = 23,
            movimientos = mapOf("Hoja Afilada" to TipoPokemon.PLANTA, "Protección" to TipoPokemon.NORMAL, "Estoicismo" to TipoPokemon.BICHO, "Disparo Demora" to TipoPokemon.BICHO),
            habilidad = "Enjambre"
        )

        return CombateImportante(
            10,
            "Camus",
            "Ciudad Porcelana",
            TipoCombate.GIMNASIO,
            mutableListOf(primero, segundo, tercero),
            imagenRival = R.drawable.camus,
            Rutas.BOSQUE_AZULEJO
        )
    }

    private fun configurarCombate11(inicial: String?): CombateImportante {
        return when (inicial) {
            PokemonInicial.SNIVY.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(13)
                val especieSegundo = Pokedex.getPokemonById(23)
                val especieTercero = Pokedex.getPokemonById(19)
                val especieCuarto = Pokedex.getPokemonById(8)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 18,
                    movimientos = mapOf("Mordisco" to TipoPokemon.SINIESTRO, "Derribo" to TipoPokemon.NORMAL, "Rastreo" to TipoPokemon.NORMAL, "Refuerzo" to TipoPokemon.NORMAL),
                    habilidad = "Intimidación"
                )
                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 18,
                    movimientos = mapOf("Bostezo" to TipoPokemon.NORMAL, "Psicorrayo" to TipoPokemon.PSIQUICO, "Cerca" to TipoPokemon.PSIQUICO, "Luz Lunar" to TipoPokemon.NORMAL),
                    habilidad = "Alerta"
                )
                val tercero = PokemonRival(
                    especie = especieTercero,
                    nivel = 18,
                    movimientos = mapOf("Lengüetazo" to TipoPokemon.FANTASMA, "Calcinación" to TipoPokemon.FUEGO, "Golpes Furia" to TipoPokemon.NORMAL, "Bostezo" to TipoPokemon.NORMAL),
                    habilidad = "Gula",
                )
                val cuarto = PokemonRival(
                    especie = especieCuarto,
                    nivel = 20,
                    movimientos = mapOf("Hidrochorro" to TipoPokemon.AGUA, "Foco Energía" to TipoPokemon.NORMAL, "Concha Filo" to TipoPokemon.AGUA, "Corte Furia" to TipoPokemon.BICHO),
                    habilidad = "Torrente"
                )


                CombateImportante(
                    11,
                    "Bel",
                    "Ciudad Porcelana",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo, tercero, cuarto),
                    R.drawable.bel,
                    Rutas.BOSQUE_AZULEJO
                )
            }
            PokemonInicial.TEPIG.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(13)
                val especieSegundo = Pokedex.getPokemonById(23)
                val especieTercero = Pokedex.getPokemonById(21)
                val especieCuarto = Pokedex.getPokemonById(2)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 18,
                    movimientos = mapOf("Mordisco" to TipoPokemon.SINIESTRO, "Derribo" to TipoPokemon.NORMAL, "Rastreo" to TipoPokemon.NORMAL, "Refuerzo" to TipoPokemon.NORMAL),
                    habilidad = "Intimidación"
                )
                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 18,
                    movimientos = mapOf("Bostezo" to TipoPokemon.NORMAL, "Psicorrayo" to TipoPokemon.PSIQUICO, "Cerca" to TipoPokemon.PSIQUICO, "Luz Lunar" to TipoPokemon.NORMAL),
                    habilidad = "Alerta"
                )
                val tercero = PokemonRival(
                    especie = especieTercero,
                    nivel = 18,
                    movimientos = mapOf("Lengüetazo" to TipoPokemon.FANTASMA, "Pistola Agua" to TipoPokemon.AGUA, "Golpes Furia" to TipoPokemon.NORMAL, "Hidrochorro" to TipoPokemon.AGUA),
                    habilidad = "Gula",
                )
                val cuarto = PokemonRival(
                    especie = especieCuarto,
                    nivel = 20,
                    movimientos = mapOf("Constricción" to TipoPokemon.NORMAL, "Desarrollo" to TipoPokemon.NORMAL, "Ciclón de Hojas" to TipoPokemon.PLANTA, "Drenadoras" to TipoPokemon.PLANTA),
                    habilidad = "Espesura"
                )


                CombateImportante(
                    11,
                    "Bel",
                    "Ciudad Porcelana",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo, tercero, cuarto),
                    R.drawable.bel,
                    Rutas.BOSQUE_AZULEJO
                )
            }
            PokemonInicial.OSHAWOTT.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(13)
                val especieSegundo = Pokedex.getPokemonById(23)
                val especieTercero = Pokedex.getPokemonById(17)
                val especieCuarto = Pokedex.getPokemonById(5)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 18,
                    movimientos = mapOf("Mordisco" to TipoPokemon.SINIESTRO, "Derribo" to TipoPokemon.NORMAL, "Rastreo" to TipoPokemon.NORMAL, "Refuerzo" to TipoPokemon.NORMAL),
                    habilidad = "Intimidación"
                )
                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 18,
                    movimientos = mapOf("Bostezo" to TipoPokemon.NORMAL, "Psicorrayo" to TipoPokemon.PSIQUICO, "Cerca" to TipoPokemon.PSIQUICO, "Luz Lunar" to TipoPokemon.NORMAL),
                    habilidad = "Alerta"
                )
                val tercero = PokemonRival(
                    especie = especieTercero,
                    nivel = 18,
                    movimientos = mapOf("Lengüetazo" to TipoPokemon.FANTASMA, "Látigo Cepa" to TipoPokemon.PLANTA, "Golpes Furia" to TipoPokemon.NORMAL, "Drenadoras" to TipoPokemon.PLANTA),
                    habilidad = "Gula",
                )
                val cuarto = PokemonRival(
                    especie = especieCuarto,
                    nivel = 20,
                    movimientos = mapOf("Rizo Defensa" to TipoPokemon.NORMAL, "Nitrocarga" to TipoPokemon.FUEGO, "Empujón" to TipoPokemon.LUCHA, "Polución" to TipoPokemon.VENENO),
                    habilidad = "Mar LLamas"
                )


                CombateImportante(
                    11,
                    "Bel",
                    "Ciudad Porcelana",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo, tercero, cuarto),
                    R.drawable.bel,
                    Rutas.BOSQUE_AZULEJO
                )
            }
            else -> {
                CombateImportante()
            }
        }
    }

    private fun configurarCombate12(inicial: String?): CombateImportante {
        return when (inicial) {
            PokemonInicial.SNIVY.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(25)
                val especieSegundo = Pokedex.getPokemonById(17)
                val especieTercero = Pokedex.getPokemonById(16)
                val especieCuarto = Pokedex.getPokemonById(5)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 20,
                    movimientos = mapOf("Malicioso" to TipoPokemon.NORMAL, "Aire Alifado" to TipoPokemon.VOLADOR, "Ataque Rápido" to TipoPokemon.NORMAL, "Respiro" to TipoPokemon.VOLADOR),
                    habilidad = "Afortunado"
                )
                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 20,
                    movimientos = mapOf("Mordisco" to TipoPokemon.SINIESTRO, "Látigo Cepa" to TipoPokemon.PLANTA, "Golpes Furia" to TipoPokemon.NORMAL, "Lengüetazo" to TipoPokemon.FANTASMA),
                    habilidad = "Gula",
                )
                val tercero = PokemonRival(
                    especie = especieTercero,
                    nivel = 20,
                    movimientos = mapOf("Persecución" to TipoPokemon.SINIESTRO, "Ataque Arena" to TipoPokemon.TIERRA, "Tormento" to TipoPokemon.SINIESTRO, "Golpes Furia" to TipoPokemon.NORMAL),
                    habilidad = "Liviano"
                )
                val cuarto = PokemonRival(
                    especie = especieCuarto,
                    nivel = 22,
                    movimientos = mapOf("Rizo Defensa" to TipoPokemon.NORMAL, "Nitrocarga" to TipoPokemon.FUEGO, "Empujón" to TipoPokemon.LUCHA, "Polución" to TipoPokemon.VENENO),
                    habilidad = "Mar Llamas",
                    objeto = "Baya Zidra"
                )

                CombateImportante(
                    12,
                    "Cheren",
                    "Ruta 4",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo, tercero, cuarto),
                    R.drawable.cheren,
                    Rutas.RUTA_4
                )
            }
            PokemonInicial.TEPIG.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(25)
                val especieSegundo = Pokedex.getPokemonById(19)
                val especieTercero = Pokedex.getPokemonById(16)
                val especieCuarto = Pokedex.getPokemonById(8)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 20,
                    movimientos = mapOf("Malicioso" to TipoPokemon.NORMAL, "Aire Alifado" to TipoPokemon.VOLADOR, "Ataque Rápido" to TipoPokemon.NORMAL, "Respiro" to TipoPokemon.VOLADOR),
                    habilidad = "Afortunado"
                )
                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 20,
                    movimientos = mapOf("Mordisco" to TipoPokemon.SINIESTRO, "Calcinación" to TipoPokemon.FUEGO, "Golpes Furia" to TipoPokemon.NORMAL, "Lengüetazo" to TipoPokemon.FANTASMA),
                    habilidad = "Gula",
                )
                val tercero = PokemonRival(
                    especie = especieTercero,
                    nivel = 20,
                    movimientos = mapOf("Persecución" to TipoPokemon.SINIESTRO, "Ataque Arena" to TipoPokemon.TIERRA, "Tormento" to TipoPokemon.SINIESTRO, "Golpes Furia" to TipoPokemon.NORMAL),
                    habilidad = "Liviano"
                )
                val cuarto = PokemonRival(
                    especie = especieCuarto,
                    nivel = 22,
                    movimientos = mapOf("Corte Furia" to TipoPokemon.BICHO, "Concha Filo" to TipoPokemon.AGUA, "Foco Energía" to TipoPokemon.NORMAL, "Hidrochorro" to TipoPokemon.AGUA),
                    habilidad = "Torrente",
                    objeto = "Baya Zidra"
                )

                CombateImportante(
                    12,
                    "Cheren",
                    "Ruta 4",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo, tercero, cuarto),
                    R.drawable.cheren,
                    Rutas.RUTA_4
                )
            }
            PokemonInicial.OSHAWOTT.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(25)
                val especieSegundo = Pokedex.getPokemonById(19)
                val especieTercero = Pokedex.getPokemonById(16)
                val especieCuarto = Pokedex.getPokemonById(2)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 20,
                    movimientos = mapOf("Malicioso" to TipoPokemon.NORMAL, "Aire Alifado" to TipoPokemon.VOLADOR, "Ataque Rápido" to TipoPokemon.NORMAL, "Respiro" to TipoPokemon.VOLADOR),
                    habilidad = "Afortunado"
                )
                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 20,
                    movimientos = mapOf("Mordisco" to TipoPokemon.SINIESTRO, "Pistola Agua" to TipoPokemon.AGUA, "Golpes Furia" to TipoPokemon.NORMAL, "Lengüetazo" to TipoPokemon.FANTASMA),
                    habilidad = "Gula",
                )
                val tercero = PokemonRival(
                    especie = especieTercero,
                    nivel = 20,
                    movimientos = mapOf("Persecución" to TipoPokemon.SINIESTRO, "Ataque Arena" to TipoPokemon.TIERRA, "Tormento" to TipoPokemon.SINIESTRO, "Golpes Furia" to TipoPokemon.NORMAL),
                    habilidad = "Liviano"
                )
                val cuarto = PokemonRival(
                    especie = especieCuarto,
                    nivel = 22,
                    movimientos = mapOf("Drenadoras" to TipoPokemon.PLANTA, "Ciclón de Hojas" to TipoPokemon.PLANTA, "Desarrollo" to TipoPokemon.NORMAL, "Constricción" to TipoPokemon.NORMAL),
                    habilidad = "Espesura",
                    objeto = "Baya Zidra"
                )

                CombateImportante(
                    12,
                    "Cheren",
                    "Ruta 4",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo, tercero, cuarto),
                    R.drawable.cheren,
                    Rutas.RUTA_4
                )
            }
            else -> {
                CombateImportante()
            }
        }
    }

    private fun configurarCombate13(): CombateImportante {
        val especiePrimero = Pokedex.getPokemonById(57)
        val especieSegundo = Pokedex.getPokemonById(60)
        val especieTercero = Pokedex.getPokemonById(65)
        val especieCuarto = Pokedex.getPokemonById(67)

        val primero = PokemonRival(
            especie = especiePrimero,
            nivel = 22,
            movimientos = mapOf("Embargo" to TipoPokemon.SINIESTRO, "Buena Baza" to TipoPokemon.SINIESTRO, "Bucle Arena" to TipoPokemon.TIERRA, "Bofetón Lodo" to TipoPokemon.TIERRA),
            habilidad = "Autoestima"
        )

        val segundo = PokemonRival(
            especie = especieSegundo,
            nivel = 22,
            movimientos = mapOf("Puño Fuego" to TipoPokemon.FUEGO, "Golpe Cabeza" to TipoPokemon.NORMAL, "Imagen" to TipoPokemon.NORMAL, "Alboroto" to TipoPokemon.NORMAL),
            habilidad = "Entusiasmo"
        )

        val tercero = PokemonRival(
            especie = especieTercero,
            nivel = 22,
            movimientos = mapOf("Contoneo" to TipoPokemon.NORMAL, "Golpe Cabeza" to TipoPokemon.NORMAL, "Demolición" to TipoPokemon.LUCHA, "Finta" to TipoPokemon.SINIESTRO),
            habilidad = "Mudar"
        )

        val cuarto = PokemonRival(
            especie = especieCuarto,
            nivel = 22,
            movimientos = mapOf("Aire Afilado" to TipoPokemon.VOLADOR, "Psicorrayo" to TipoPokemon.PSIQUICO, "Viento Afín" to TipoPokemon.VOLADOR, "Remolino" to TipoPokemon.NORMAL),
            habilidad = "Muro Mágico"
        )

        return CombateImportante(
            13,
            "N",
            "Ciudad Mayólica",
            TipoCombate.EQUIPO_PLASMA,
            mutableListOf(primero, segundo, tercero, cuarto),
            imagenRival = R.drawable.n,
            Rutas.BOSQUE_PERDIDOS
        )
    }

    private fun configurarCombate14(): CombateImportante {
        val especiePrimero = Pokedex.getPokemonById(93)
        val especieSegundo = Pokedex.getPokemonById(93)
        val especieTercero = Pokedex.getPokemonById(29)

        val primero = PokemonRival(
            especie = especiePrimero,
            nivel = 25,
            movimientos = mapOf("Voltiocambio" to TipoPokemon.ELECTRICO, "Persecución" to TipoPokemon.SINIESTRO, "Ataque Rápido" to TipoPokemon.NORMAL, "Golpe Aéreo" to TipoPokemon.VOLADOR),
            habilidad = "Electricidad Estática"
        )

        val segundo = PokemonRival(
            especie = especieSegundo,
            nivel = 25,
            movimientos = mapOf("Voltiocambio" to TipoPokemon.ELECTRICO, "Persecución" to TipoPokemon.SINIESTRO, "Ataque Rápido" to TipoPokemon.NORMAL, "Golpe Aéreo" to TipoPokemon.VOLADOR),
            habilidad = "Electricidad Estática"
        )

        val tercero = PokemonRival(
            especie = especieTercero,
            nivel = 27,
            movimientos = mapOf("Voltiocambio" to TipoPokemon.ELECTRICO, "Ataque Rápido" to TipoPokemon.NORMAL, "Chispa" to TipoPokemon.ELECTRICO, "Nitrocarga" to TipoPokemon.FUEGO),
            habilidad = "Pararrayos"
        )

        return CombateImportante(
            14,
            "Camila",
            "Ciudad Mayólica",
            TipoCombate.GIMNASIO,
            mutableListOf(primero, segundo, tercero),
            imagenRival = R.drawable.camila,
            Rutas.BOSQUE_PERDIDOS
        )
    }

    private fun configurarCombate15(inicial: String?): CombateImportante {
        return when (inicial) {
            PokemonInicial.SNIVY.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(26)
                val especieSegundo = Pokedex.getPokemonById(17)
                val especieTercero = Pokedex.getPokemonById(16)
                val especieCuarto = Pokedex.getPokemonById(5)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 24,
                    movimientos = mapOf("Detección" to TipoPokemon.LUCHA, "Aire Alifado" to TipoPokemon.VOLADOR, "Ataque Rápido" to TipoPokemon.NORMAL, "Respiro" to TipoPokemon.VOLADOR),
                    habilidad = "Afortunado",
                    objeto = "Periscopio"
                )
                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 24,
                    movimientos = mapOf("Bomba Germen" to TipoPokemon.PLANTA, "Mordisco" to TipoPokemon.SINIESTRO, "Golpes Furia" to TipoPokemon.NORMAL, "Drenadoras" to TipoPokemon.PLANTA),
                    habilidad = "Gula",
                    objeto = "Semilla Milagro"
                )
                val tercero = PokemonRival(
                    especie = especieTercero,
                    nivel = 24,
                    movimientos = mapOf("Sorpresa" to TipoPokemon.NORMAL, "Persecución" to TipoPokemon.SINIESTRO, "Tormento" to TipoPokemon.SINIESTRO, "Golpes Furia" to TipoPokemon.NORMAL),
                    habilidad = "Liviano"
                )
                val cuarto = PokemonRival(
                    especie = especieCuarto,
                    nivel = 26,
                    movimientos = mapOf("Desenrollar" to TipoPokemon.ROCA, "Nitrocarga" to TipoPokemon.FUEGO, "Empujón" to TipoPokemon.LUCHA, "Polución" to TipoPokemon.VENENO),
                    habilidad = "Mar Llamas",
                    objeto = "Baya Zidra"
                )

                CombateImportante(
                    15,
                    "Cheren",
                    "Ruta 5",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo, tercero, cuarto),
                    R.drawable.cheren,
                    Rutas.RUTA_5
                )
            }
            PokemonInicial.TEPIG.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(26)
                val especieSegundo = Pokedex.getPokemonById(19)
                val especieTercero = Pokedex.getPokemonById(16)
                val especieCuarto = Pokedex.getPokemonById(8)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 24,
                    movimientos = mapOf("Detección" to TipoPokemon.LUCHA, "Aire Alifado" to TipoPokemon.VOLADOR, "Ataque Rápido" to TipoPokemon.NORMAL, "Respiro" to TipoPokemon.VOLADOR),
                    habilidad = "Afortunado",
                    objeto = "Periscopio"
                )
                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 24,
                    movimientos = mapOf("Pirotecnia" to TipoPokemon.FUEGO, "Mordisco" to TipoPokemon.SINIESTRO, "Golpes Furia" to TipoPokemon.NORMAL, "Bostezo" to TipoPokemon.NORMAL),
                    habilidad = "Gula",
                    objeto = "Carbón"
                )
                val tercero = PokemonRival(
                    especie = especieTercero,
                    nivel = 24,
                    movimientos = mapOf("Sorpresa" to TipoPokemon.NORMAL, "Persecución" to TipoPokemon.SINIESTRO, "Tormento" to TipoPokemon.SINIESTRO, "Golpes Furia" to TipoPokemon.NORMAL),
                    habilidad = "Liviano"
                )
                val cuarto = PokemonRival(
                    especie = especieCuarto,
                    nivel = 26,
                    movimientos = mapOf("Concha Filo" to TipoPokemon.AGUA, "Hidropulso" to TipoPokemon.AGUA, "Corte Furia" to TipoPokemon.BICHO, "Foco Energía" to TipoPokemon.NORMAL),
                    habilidad = "Torrente",
                    objeto = "Baya Zidra"
                )

                CombateImportante(
                    15,
                    "Cheren",
                    "Ruta 5",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo, tercero, cuarto),
                    R.drawable.cheren,
                    Rutas.RUTA_5
                )
            }
            PokemonInicial.OSHAWOTT.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(26)
                val especieSegundo = Pokedex.getPokemonById(21)
                val especieTercero = Pokedex.getPokemonById(16)
                val especieCuarto = Pokedex.getPokemonById(2)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 24,
                    movimientos = mapOf("Detección" to TipoPokemon.LUCHA, "Aire Alifado" to TipoPokemon.VOLADOR, "Ataque Rápido" to TipoPokemon.NORMAL, "Respiro" to TipoPokemon.VOLADOR),
                    habilidad = "Afortunado",
                    objeto = "Periscopio"
                )
                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 24,
                    movimientos = mapOf("Escaldar" to TipoPokemon.AGUA, "Mordisco" to TipoPokemon.SINIESTRO, "Golpes Furia" to TipoPokemon.NORMAL, "Hidrochorro" to TipoPokemon.AGUA),
                    habilidad = "Gula",
                    objeto = "Agua Mística"
                )
                val tercero = PokemonRival(
                    especie = especieTercero,
                    nivel = 24,
                    movimientos = mapOf("Sorpresa" to TipoPokemon.NORMAL, "Persecución" to TipoPokemon.SINIESTRO, "Tormento" to TipoPokemon.SINIESTRO, "Golpes Furia" to TipoPokemon.NORMAL),
                    habilidad = "Liviano"
                )
                val cuarto = PokemonRival(
                    especie = especieCuarto,
                    nivel = 26,
                    movimientos = mapOf("Megaagotar" to TipoPokemon.PLANTA, "Drenadoras" to TipoPokemon.PLANTA, "Ciclón de Hojas" to TipoPokemon.PLANTA, "Desarrollo" to TipoPokemon.NORMAL),
                    habilidad = "Espesura",
                    objeto = "Baya Zidra"
                )

                CombateImportante(
                    15,
                    "Cheren",
                    "Ruta 5",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo, tercero, cuarto),
                    R.drawable.cheren,
                    Rutas.RUTA_5
                )
            }
            else -> {
                CombateImportante()
            }
        }
    }

    private fun configurarCombate16(): CombateImportante {
        val especiePrimero = Pokedex.getPokemonById(58)
        val especieSegundo = Pokedex.getPokemonById(42)
        val especieTercero = Pokedex.getPokemonById(36)

        val primero = PokemonRival(
            especie = especiePrimero,
            nivel = 29,
            movimientos = mapOf("Tormento" to TipoPokemon.SINIESTRO, "Contoneo" to TipoPokemon.NORMAL, "Triturar" to TipoPokemon.SINIESTRO, "Terratemblor" to TipoPokemon.TIERRA),
            habilidad = "Autoestima"
        )
        val segundo = PokemonRival(
            especie = especieSegundo,
            nivel = 29,
            movimientos = mapOf("Rayo Burbuja" to TipoPokemon.AGUA, "Agua Lodosa" to TipoPokemon.AGUA, "Terratemblor" to TipoPokemon.TIERRA, "Acua Aro" to TipoPokemon.AGUA),
            habilidad = "Nado Rápido"
        )
        val tercero = PokemonRival(
            especie = especieTercero,
            nivel = 31,
            movimientos = mapOf("Afilagarras" to TipoPokemon.SINIESTRO, "Avalancha" to TipoPokemon.ROCA, "Cuchillada" to TipoPokemon.NORMAL, "Terratemblor" to TipoPokemon.TIERRA),
            habilidad = "Ímpetu Arena"
        )

        return CombateImportante(
            16,
            "Yakón",
            "Ciudad Fayenza",
            TipoCombate.GIMNASIO,
            mutableListOf(primero, segundo, tercero),
            imagenRival = R.drawable.yakon,
            Rutas.ALMACENES_FRIGORIFICOS
        )
    }

    private fun configurarCombate17(inicial: String?): CombateImportante {
        return when (inicial) {
            PokemonInicial.SNIVY.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(13)
                val especieSegundo = Pokedex.getPokemonById(24)
                val especieTercero = Pokedex.getPokemonById(19)
                val especieCuarto = Pokedex.getPokemonById(8)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 26,
                    movimientos = mapOf("Triturar" to TipoPokemon.SINIESTRO, "Derribo" to TipoPokemon.NORMAL, "Avivar" to TipoPokemon.NORMAL, "Refuerzo" to TipoPokemon.NORMAL),
                    habilidad = "Intimidación"
                )
                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 26,
                    movimientos = mapOf("Conjuro" to TipoPokemon.NORMAL, "Psicorrayo" to TipoPokemon.PSIQUICO, "Rizo Defensa" to TipoPokemon.NORMAL, "Hipnosis" to TipoPokemon.PSIQUICO),
                    habilidad = "Alerta"
                )
                val tercero = PokemonRival(
                    especie = especieTercero,
                    nivel = 26,
                    movimientos = mapOf("Amnesia" to TipoPokemon.PSIQUICO, "Pirotecnia" to TipoPokemon.FUEGO, "Mordisco" to TipoPokemon.SINIESTRO, "Bostezo" to TipoPokemon.NORMAL),
                    habilidad = "Gula",
                )
                val cuarto = PokemonRival(
                    especie = especieCuarto,
                    nivel = 28,
                    movimientos = mapOf("Desquite" to TipoPokemon.LUCHA, "Hidropulso" to TipoPokemon.AGUA, "Concha Filo" to TipoPokemon.AGUA, "Corte Furia" to TipoPokemon.BICHO),
                    habilidad = "Torrente"
                )

                CombateImportante(
                    17,
                    "Bel",
                    "Ruta 6",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo, tercero, cuarto),
                    R.drawable.bel,
                    Rutas.ALMACENES_FRIGORIFICOS
                )
            }
            PokemonInicial.TEPIG.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(13)
                val especieSegundo = Pokedex.getPokemonById(24)
                val especieTercero = Pokedex.getPokemonById(21)
                val especieCuarto = Pokedex.getPokemonById(2)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 26,
                    movimientos = mapOf("Triturar" to TipoPokemon.SINIESTRO, "Derribo" to TipoPokemon.NORMAL, "Avivar" to TipoPokemon.NORMAL, "Refuerzo" to TipoPokemon.NORMAL),
                    habilidad = "Intimidación"
                )
                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 26,
                    movimientos = mapOf("Conjuro" to TipoPokemon.NORMAL, "Psicorrayo" to TipoPokemon.PSIQUICO, "Rizo Defensa" to TipoPokemon.NORMAL, "Hipnosis" to TipoPokemon.PSIQUICO),
                    habilidad = "Alerta"
                )
                val tercero = PokemonRival(
                    especie = especieTercero,
                    nivel = 26,
                    movimientos = mapOf("Mofa" to TipoPokemon.SINIESTRO, "Escaldar" to TipoPokemon.AGUA, "Mordisco" to TipoPokemon.SINIESTRO, "Hidrochorro" to TipoPokemon.AGUA),
                    habilidad = "Gula",
                )
                val cuarto = PokemonRival(
                    especie = especieCuarto,
                    nivel = 28,
                    movimientos = mapOf("Ciclón de Hojas" to TipoPokemon.PLANTA, "Drenadoras" to TipoPokemon.PLANTA, "Megaagotar" to TipoPokemon.PLANTA, "Atizar" to TipoPokemon.NORMAL),
                    habilidad = "Espesura"
                )

                CombateImportante(
                    17,
                    "Bel",
                    "Ruta 6",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo, tercero, cuarto),
                    R.drawable.bel,
                    Rutas.ALMACENES_FRIGORIFICOS
                )
            }
            PokemonInicial.OSHAWOTT.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(13)
                val especieSegundo = Pokedex.getPokemonById(24)
                val especieTercero = Pokedex.getPokemonById(17)
                val especieCuarto = Pokedex.getPokemonById(5)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 26,
                    movimientos = mapOf("Triturar" to TipoPokemon.SINIESTRO, "Derribo" to TipoPokemon.NORMAL, "Avivar" to TipoPokemon.NORMAL, "Refuerzo" to TipoPokemon.NORMAL),
                    habilidad = "Intimidación"
                )
                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 26,
                    movimientos = mapOf("Conjuro" to TipoPokemon.NORMAL, "Psicorrayo" to TipoPokemon.PSIQUICO, "Rizo Defensa" to TipoPokemon.NORMAL, "Hipnosis" to TipoPokemon.PSIQUICO),
                    habilidad = "Alerta"
                )
                val tercero = PokemonRival(
                    especie = especieTercero,
                    nivel = 26,
                    movimientos = mapOf("Drenadoras" to TipoPokemon.PLANTA, "Bomba Germen" to TipoPokemon.PLANTA, "Mordisco" to TipoPokemon.SINIESTRO, "Tormento" to TipoPokemon.SINIESTRO),
                    habilidad = "Gula",
                )
                val cuarto = PokemonRival(
                    especie = especieCuarto,
                    nivel = 28,
                    movimientos = mapOf("Empujón" to TipoPokemon.LUCHA, "Polución" to TipoPokemon.VENENO, "Desenrollar" to TipoPokemon.ROCA, "Derribo" to TipoPokemon.NORMAL),
                    habilidad = "Mar Llamas"
                )

                CombateImportante(
                    17,
                    "Bel",
                    "Ruta 6",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo, tercero, cuarto),
                    R.drawable.bel,
                    Rutas.ALMACENES_FRIGORIFICOS
                )
            }
            else -> {
                CombateImportante()
            }
        }
    }

    private fun configurarCombate18(): CombateImportante {
        val especiePrimero = Pokedex.getPokemonById(31)
        val especieSegundo = Pokedex.getPokemonById(101)
        val especieTercero = Pokedex.getPokemonById(105)
        val especieCuarto = Pokedex.getPokemonById(103)

        val primero = PokemonRival(
            especie = especiePrimero,
            nivel = 28,
            movimientos = mapOf("Joya de Luz" to TipoPokemon.ROCA, "Defensa Férrea" to TipoPokemon.ACERO, "Bofetón Lodo" to TipoPokemon.TIERRA, "Antiaéreo" to TipoPokemon.ROCA),
            habilidad = "Robustez"
        )
        val segundo = PokemonRival(
            especie = especieSegundo,
            nivel = 28,
            movimientos = mapOf("Picadura" to TipoPokemon.BICHO, "Bilis" to TipoPokemon.VENENO, "Electrotela" to TipoPokemon.ELECTRICO, "Cuchillada" to TipoPokemon.NORMAL),
            habilidad = "Nerviosismo"
        )
        val tercero = PokemonRival(
            especie = especieTercero,
            nivel = 28,
            movimientos = mapOf("Rayo Carga" to TipoPokemon.ELECTRICO, "Impactrueno" to TipoPokemon.ELECTRICO, "Rueda Doble" to TipoPokemon.ACERO, "Atadura" to TipoPokemon.NORMAL),
            habilidad = "Más"
        )
        val cuarto = PokemonRival(
            especie = especieCuarto,
            nivel = 28,
            movimientos = mapOf("Garra Metal" to TipoPokemon.ACERO, "Pin Misil" to TipoPokemon.BICHO, "Defensa Férrea" to TipoPokemon.ACERO, "Giro Bola" to TipoPokemon.ACERO),
            habilidad = "Punta Acero"
        )

        return CombateImportante(
            18,
            "N",
            "Cueva Electrorroca",
            TipoCombate.EQUIPO_PLASMA,
            mutableListOf(primero, segundo, tercero, cuarto),
            imagenRival = R.drawable.n,
            Rutas.CUEVA_ELECTRORROCA
        )
    }

    private fun configurarCombate19(): CombateImportante {
        val especiePrimero = Pokedex.getPokemonById(34)
        val especieSegundo = Pokedex.getPokemonById(87)
        val especieTercero = Pokedex.getPokemonById(27)

        val primero = PokemonRival(
            especie = especiePrimero,
            nivel = 33,
            movimientos = mapOf("Amnesia" to TipoPokemon.PSIQUICO, "Buena Baza" to TipoPokemon.SINIESTRO, "Acróbata" to TipoPokemon.VOLADOR, "Arrumaco" to TipoPokemon.PSIQUICO),
            habilidad = "Ignorante"
        )
        val segundo = PokemonRival(
            especie = especieSegundo,
            nivel = 35,
            movimientos = mapOf("Tajo Aéreo" to TipoPokemon.VOLADOR, "Acua Aro" to TipoPokemon.AGUA, "Golpe Aéreo" to TipoPokemon.VOLADOR, "Rayo Burbuja" to TipoPokemon.AGUA),
            habilidad = "Vista Lince",
        )
        val tercero = PokemonRival(
            especie = especieTercero,
            nivel = 33,
            movimientos = mapOf("Malicioso" to TipoPokemon.NORMAL, "Tajo Aéreo" to TipoPokemon.VOLADOR, "Viento Cortante" to TipoPokemon.NORMAL, "Ataque Rápido" to TipoPokemon.NORMAL),
            habilidad = "Sacapecho",
        )

        return CombateImportante(
            19,
            "Gerania",
            "Ciudad Loza",
            TipoCombate.GIMNASIO,
            mutableListOf(primero, segundo, tercero),
            imagenRival = R.drawable.gerania,
            Rutas.TORRE_DE_LOS_CIELOS
        )
    }

    private fun configurarCombate20(inicial: String?): CombateImportante {
        return when (inicial) {
            PokemonInicial.SNIVY.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(27)
                val especieSegundo = Pokedex.getPokemonById(18)
                val especieTercero = Pokedex.getPokemonById(16)
                val especieCuarto = Pokedex.getPokemonById(5)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 33,
                    movimientos = mapOf("Mofa" to TipoPokemon.SINIESTRO, "Tajo Aéreo" to TipoPokemon.VOLADOR, "Detección" to TipoPokemon.LUCHA, "Respiro" to TipoPokemon.VOLADOR),
                    habilidad = "Afortunado",
                    objeto = "Periscopio"
                )
                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 33,
                    movimientos = mapOf("Malicioso" to TipoPokemon.NORMAL, "Bomba Germen" to TipoPokemon.PLANTA, "Golpes Furia" to TipoPokemon.NORMAL, "Lengüetazo" to TipoPokemon.FANTASMA),
                    habilidad = "Gula",
                    objeto = "Semilla Milagro"
                )
                val tercero = PokemonRival(
                    especie = especieTercero,
                    nivel = 33,
                    movimientos = mapOf("Buena Baza" to TipoPokemon.SINIESTRO, "Afilagarras" to TipoPokemon.NORMAL, "Tormento" to TipoPokemon.SINIESTRO, "Sorpresa" to TipoPokemon.NORMAL),
                    habilidad = "Liviano",
                    objeto = "Baya Zidra"
                )
                val cuarto = PokemonRival(
                    especie = especieCuarto,
                    nivel = 35,
                    movimientos = mapOf("Golpe Calor" to TipoPokemon.FUEGO, "Derribo" to TipoPokemon.NORMAL, "Desenrollar" to TipoPokemon.ROCA, "Polución" to TipoPokemon.VENENO),
                    habilidad = "Mar Llamas",
                    objeto = "Restos"
                )

                CombateImportante(
                    20,
                    "Cheren",
                    "Ruta 7",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo, tercero, cuarto),
                    R.drawable.cheren,
                    Rutas.TORRE_DE_LOS_CIELOS
                )
            }
            PokemonInicial.TEPIG.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(27)
                val especieSegundo = Pokedex.getPokemonById(20)
                val especieTercero = Pokedex.getPokemonById(16)
                val especieCuarto = Pokedex.getPokemonById(8)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 33,
                    movimientos = mapOf("Mofa" to TipoPokemon.SINIESTRO, "Tajo Aéreo" to TipoPokemon.VOLADOR, "Detección" to TipoPokemon.LUCHA, "Respiro" to TipoPokemon.VOLADOR),
                    habilidad = "Afortunado",
                    objeto = "Periscopio"
                )
                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 33,
                    movimientos = mapOf("Malicioso" to TipoPokemon.NORMAL, "Pirotecnia" to TipoPokemon.FUEGO, "Golpes Furia" to TipoPokemon.NORMAL, "Lengüetazo" to TipoPokemon.FANTASMA),
                    habilidad = "Gula",
                    objeto = "Carbón"
                )
                val tercero = PokemonRival(
                    especie = especieTercero,
                    nivel = 33,
                    movimientos = mapOf("Buena Baza" to TipoPokemon.SINIESTRO, "Afilagarras" to TipoPokemon.NORMAL, "Tormento" to TipoPokemon.SINIESTRO, "Sorpresa" to TipoPokemon.NORMAL),
                    habilidad = "Liviano",
                    objeto = "Baya Zidra"
                )
                val cuarto = PokemonRival(
                    especie = especieCuarto,
                    nivel = 35,
                    movimientos = mapOf("Acua Jet" to TipoPokemon.AGUA, "Desquite" to TipoPokemon.LUCHA, "Hidropulso" to TipoPokemon.AGUA, "Corte Furia" to TipoPokemon.BICHO),
                    habilidad = "Torrente",
                    objeto = "Restos"
                )

                CombateImportante(
                    20,
                    "Cheren",
                    "Ruta 7",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo, tercero, cuarto),
                    R.drawable.cheren,
                    Rutas.TORRE_DE_LOS_CIELOS
                )
            }
            PokemonInicial.OSHAWOTT.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(27)
                val especieSegundo = Pokedex.getPokemonById(22)
                val especieTercero = Pokedex.getPokemonById(16)
                val especieCuarto = Pokedex.getPokemonById(2)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 33,
                    movimientos = mapOf("Mofa" to TipoPokemon.SINIESTRO, "Tajo Aéreo" to TipoPokemon.VOLADOR, "Detección" to TipoPokemon.LUCHA, "Respiro" to TipoPokemon.VOLADOR),
                    habilidad = "Afortunado",
                    objeto = "Periscopio"
                )
                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 33,
                    movimientos = mapOf("Malicioso" to TipoPokemon.NORMAL, "Escaldar" to TipoPokemon.AGUA, "Golpes Furia" to TipoPokemon.NORMAL, "Lengüetazo" to TipoPokemon.FANTASMA),
                    habilidad = "Gula",
                    objeto = "Agua Mística"
                )
                val tercero = PokemonRival(
                    especie = especieTercero,
                    nivel = 33,
                    movimientos = mapOf("Buena Baza" to TipoPokemon.SINIESTRO, "Afilagarras" to TipoPokemon.NORMAL, "Tormento" to TipoPokemon.SINIESTRO, "Sorpresa" to TipoPokemon.NORMAL),
                    habilidad = "Liviano",
                    objeto = "Baya Zidra"
                )
                val cuarto = PokemonRival(
                    especie = especieCuarto,
                    nivel = 35,
                    movimientos = mapOf("Hoja Aguda" to TipoPokemon.PLANTA, "Atizar" to TipoPokemon.NORMAL, "Megaagotar" to TipoPokemon.PLANTA, "Drenadoras" to TipoPokemon.PLANTA),
                    habilidad = "Espesura",
                    objeto = "Restos"
                )

                CombateImportante(
                    20,
                    "Cheren",
                    "Ruta 7",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo, tercero, cuarto),
                    R.drawable.cheren,
                    Rutas.TORRE_DE_LOS_CIELOS
                )
            }
            else -> {
                CombateImportante()
            }
        }
    }

    private fun configurarCombate21(): CombateImportante {
        val especiePrimero = Pokedex.getPokemonById(89)
        val especieSegundo = Pokedex.getPokemonById(121)
        val especieTercero = Pokedex.getPokemonById(120)

        val primero = PokemonRival(
            especie = especiePrimero,
            nivel = 37,
            movimientos = mapOf("Vaho Gélido" to TipoPokemon.HIELO, "Armadura Ácida" to TipoPokemon.VENENO, "Impresionar" to TipoPokemon.FANTASMA, "Disparo Espejo" to TipoPokemon.ACERO),
            habilidad = "Gélido"
        )

        val segundo = PokemonRival(
            especie = especieSegundo,
            nivel = 37,
            movimientos = mapOf("Rayo Aurora" to TipoPokemon.HIELO, "Vaho Gélido" to TipoPokemon.HIELO, "Reflejo" to TipoPokemon.PSIQUICO, "Giro Rápido" to TipoPokemon.NORMAL),
            habilidad = "Levitacióm"
        )
        val tercero = PokemonRival(
            especie = especieTercero,
            nivel = 39,
            movimientos = mapOf("Contoneo" to TipoPokemon.NORMAL, "Salmuera" to TipoPokemon.AGUA, "Cuchillada" to TipoPokemon.NORMAL, "Chuzos" to TipoPokemon.HIELO),
            habilidad = "Manto Níveo"
        )

        return CombateImportante(
            21,
            "Junco",
            "Ciudad Teja",
            TipoCombate.GIMNASIO,
            mutableListOf(primero, segundo, tercero),
            imagenRival = R.drawable.junco,
            Rutas.PANTANO_TEJA
        )
    }

    private fun configurarCombate22(inicial: String?): CombateImportante {
        return when (inicial) {
            PokemonInicial.SNIVY.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(14)
                val especieSegundo = Pokedex.getPokemonById(24)
                val especieTercero = Pokedex.getPokemonById(20)
                val especieCuarto = Pokedex.getPokemonById(9)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 38,
                    movimientos = mapOf("Avivar" to TipoPokemon.NORMAL, "Triturar" to TipoPokemon.SINIESTRO, "Rugido" to TipoPokemon.NORMAL, "Represalia" to TipoPokemon.NORMAL),
                    habilidad = "Intimidación"
                )
                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 38,
                    movimientos = mapOf("Rizo Defensa" to TipoPokemon.NORMAL, "Conjuro" to TipoPokemon.NORMAL, "Psicorrayo" to TipoPokemon.PSIQUICO, "Hipnosis" to TipoPokemon.PSIQUICO),
                    habilidad = "ALerta"
                )
                val tercero = PokemonRival(
                    especie = especieTercero,
                    nivel = 38,
                    movimientos = mapOf("Malicioso" to TipoPokemon.NORMAL, "Lengüetazo" to TipoPokemon.FANTASMA, "Golpes Furia" to TipoPokemon.NORMAL, "Pirotecnia" to TipoPokemon.FUEGO),
                    habilidad = "Gula"
                )
                val cuarto = PokemonRival(
                    especie = especieCuarto,
                    nivel = 40,
                    movimientos = mapOf("Desquite" to TipoPokemon.LUCHA, "Acua Jet" to TipoPokemon.AGUA, "Cuchillada" to TipoPokemon.NORMAL, "Otra Vez" to TipoPokemon.NORMAL),
                    habilidad = "Torrente"
                )

                CombateImportante(
                    22,
                    "Bel",
                    "Ruta 8",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo, tercero, cuarto),
                    R.drawable.bel,
                    Rutas.PANTANO_TEJA
                )
            }
            PokemonInicial.TEPIG.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(14)
                val especieSegundo = Pokedex.getPokemonById(24)
                val especieTercero = Pokedex.getPokemonById(22)
                val especieCuarto = Pokedex.getPokemonById(3)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 38,
                    movimientos = mapOf("Avivar" to TipoPokemon.NORMAL, "Triturar" to TipoPokemon.SINIESTRO, "Rugido" to TipoPokemon.NORMAL, "Represalia" to TipoPokemon.NORMAL),
                    habilidad = "Intimidación"
                )
                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 38,
                    movimientos = mapOf("Rizo Defensa" to TipoPokemon.NORMAL, "Conjuro" to TipoPokemon.NORMAL, "Psicorrayo" to TipoPokemon.PSIQUICO, "Hipnosis" to TipoPokemon.PSIQUICO),
                    habilidad = "ALerta"
                )
                val tercero = PokemonRival(
                    especie = especieTercero,
                    nivel = 38,
                    movimientos = mapOf("Malicioso" to TipoPokemon.NORMAL, "Lengüetazo" to TipoPokemon.FANTASMA, "Golpes Furia" to TipoPokemon.NORMAL, "Escaldar" to TipoPokemon.AGUA),
                    habilidad = "Gula"
                )
                val cuarto = PokemonRival(
                    especie = especieCuarto,
                    nivel = 40,
                    movimientos = mapOf("Megaagotar" to TipoPokemon.PLANTA, "Atizar" to TipoPokemon.NORMAL, "Hoja Aguda" to TipoPokemon.PLANTA, "Enrosque" to TipoPokemon.VENENO),
                    habilidad = "Espesura"
                )

                CombateImportante(
                    22,
                    "Bel",
                    "Ruta 8",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo, tercero, cuarto),
                    R.drawable.bel,
                    Rutas.PANTANO_TEJA
                )
            }
            PokemonInicial.OSHAWOTT.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(14)
                val especieSegundo = Pokedex.getPokemonById(24)
                val especieTercero = Pokedex.getPokemonById(18)
                val especieCuarto = Pokedex.getPokemonById(6)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 38,
                    movimientos = mapOf("Avivar" to TipoPokemon.NORMAL, "Triturar" to TipoPokemon.SINIESTRO, "Rugido" to TipoPokemon.NORMAL, "Represalia" to TipoPokemon.NORMAL),
                    habilidad = "Intimidación"
                )
                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 38,
                    movimientos = mapOf("Rizo Defensa" to TipoPokemon.NORMAL, "Conjuro" to TipoPokemon.NORMAL, "Psicorrayo" to TipoPokemon.PSIQUICO, "Hipnosis" to TipoPokemon.PSIQUICO),
                    habilidad = "ALerta"
                )
                val tercero = PokemonRival(
                    especie = especieTercero,
                    nivel = 38,
                    movimientos = mapOf("Malicioso" to TipoPokemon.NORMAL, "Lengüetazo" to TipoPokemon.FANTASMA, "Golpes Furia" to TipoPokemon.NORMAL, "Bomba Germen" to TipoPokemon.PLANTA),
                    habilidad = "Gula"
                )
                val cuarto = PokemonRival(
                    especie = especieCuarto,
                    nivel = 40,
                    movimientos = mapOf("Desenrollar" to TipoPokemon.ROCA, "Derribo" to TipoPokemon.NORMAL, "Golpe Calor" to TipoPokemon.FUEGO, "Buena Baza" to TipoPokemon.SINIESTRO),
                    habilidad = "Mar Llamas"
                )

                CombateImportante(
                    22,
                    "Bel",
                    "Ruta 8",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo, tercero, cuarto),
                    R.drawable.bel,
                    Rutas.PANTANO_TEJA
                )
            }
            else -> {
                CombateImportante()
            }
        }
    }

    private fun configurarCombate23(version: String?): CombateImportante {
        return when (version) {
            VersionJuego.NEGRO.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(117)
                val especieSegundo = Pokedex.getPokemonById(127)
                val especieTercero = Pokedex.getPokemonById(118)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    41,
                    movimientos = mapOf("Danza Dragón" to TipoPokemon.DRAGON, "Furia Dragón" to TipoPokemon.DRAGON, "Cola Dragón" to TipoPokemon.DRAGON, "Buena Baza" to TipoPokemon.SINIESTRO),
                    habilidad = "Rivalidad"
                )
                val segundo = PokemonRival(
                    especie = especieSegundo,
                    41,
                    movimientos = mapOf("Desquite" to TipoPokemon.LUCHA, "Guardia Baja" to TipoPokemon.NORMAL, "Cola Dragón" to TipoPokemon.DRAGON, "Tajo Umbrío" to TipoPokemon.SINIESTRO),
                    habilidad = "Piel Tosca"
                )
                val tercero = PokemonRival(
                    especie = especieTercero,
                    43,
                    movimientos = mapOf("Danza Dragón" to TipoPokemon.DRAGON, "Buena Baza" to TipoPokemon.SINIESTRO, "Cola Dragón" to TipoPokemon.DRAGON, "Cuchillada" to TipoPokemon.NORMAL),
                    habilidad = "Rivalidad"
                )

                return CombateImportante(
                    23,
                    "Lirio",
                    "Ciudad Caolín",
                    TipoCombate.GIMNASIO,
                    mutableListOf(primero, segundo, tercero),
                    imagenRival = R.drawable.lirio,
                    Rutas.RUTA_9
                )
            }
            VersionJuego.BLANCO.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(117)
                val especieSegundo = Pokedex.getPokemonById(127)
                val especieTercero = Pokedex.getPokemonById(118)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 41,
                    movimientos = mapOf("Danza Dragón" to TipoPokemon.DRAGON, "Buena Baza" to TipoPokemon.SINIESTRO, "Cola Dragón" to TipoPokemon.DRAGON, "Furia Dragón" to TipoPokemon.DRAGON),
                    habilidad = "Rivalidad"
                )
                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 41,
                    movimientos = mapOf("Desquite" to TipoPokemon.LUCHA, "Guardia Baja" to TipoPokemon.NORMAL, "Cola Dragón" to TipoPokemon.DRAGON, "Tajo Umbrío" to TipoPokemon.SINIESTRO),
                    habilidad = "Potencia Bruta"
                )
                val tercero = PokemonRival(
                    especie = especieTercero,
                    nivel = 43,
                    movimientos = mapOf("Danza Dragón" to TipoPokemon.DRAGON, "Cuchillada" to TipoPokemon.NORMAL, "Cola Dragón" to TipoPokemon.DRAGON, "Buena Baza" to TipoPokemon.SINIESTRO),
                    habilidad = "Rompemoldes"
                )

                CombateImportante(
                    23,
                    "Iris",
                    "Ciudad Caolín",
                    TipoCombate.GIMNASIO,
                    mutableListOf(primero, segundo, tercero),
                    imagenRival = R.drawable.iris,
                    Rutas.RUTA_9
                )
            }
            else -> {
                CombateImportante()
            }
        }
    }

    private fun configurarCombate24(inicial: String?): CombateImportante {
        return when (inicial) {
            PokemonInicial.SNIVY.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(27)
                val especieSegundo = Pokedex.getPokemonById(18)
                val especieTercero = Pokedex.getPokemonById(16)
                val especieCuarto = Pokedex.getPokemonById(6)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 43,
                    movimientos = mapOf("Viento Cortante" to TipoPokemon.NORMAL, "Tajo Aéreo" to TipoPokemon.VOLADOR, "Mofa" to TipoPokemon.SINIESTRO, "Detección" to TipoPokemon.LUCHA),
                    habilidad = "Afortunado",
                    objeto = "Periscopio"
                )
                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 43,
                    movimientos = mapOf("Bomba Germen" to TipoPokemon.PLANTA, "Lengüetazo" to TipoPokemon.FANTASMA, "Golpes Furia" to TipoPokemon.NORMAL, "Malicioso" to TipoPokemon.NORMAL),
                    habilidad = "Gula",
                    objeto = "Semilla Milagro"
                )
                val tercero = PokemonRival(
                    especie = especieTercero,
                    nivel = 43,
                    movimientos = mapOf("Tajo Umbrío" to TipoPokemon.SINIESTRO, "Mofa" to TipoPokemon.SINIESTRO, "Cuchillada" to TipoPokemon.NORMAL, "Buena Baza" to TipoPokemon.SINIESTRO),
                    habilidad = "Liviano",
                    objeto = "Baya Zidra"
                )
                val cuarto = PokemonRival(
                    especie = especieCuarto,
                    nivel = 45,
                    movimientos = mapOf("Lanzallamas" to TipoPokemon.FUEGO, "Buena Baza" to TipoPokemon.SINIESTRO, "Golpe Calor" to TipoPokemon.FUEGO, "Derribo" to TipoPokemon.NORMAL),
                    habilidad = "Mar Llamas",
                    objeto = "Restos"
                )

                CombateImportante(
                    24,
                    "Cheren",
                    "Ruta 10",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo, tercero, cuarto),
                    R.drawable.cheren,
                    Rutas.RUTA_10
                )
            }
            PokemonInicial.TEPIG.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(27)
                val especieSegundo = Pokedex.getPokemonById(20)
                val especieTercero = Pokedex.getPokemonById(16)
                val especieCuarto = Pokedex.getPokemonById(9)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 43,
                    movimientos = mapOf("Viento Cortante" to TipoPokemon.NORMAL, "Tajo Aéreo" to TipoPokemon.VOLADOR, "Mofa" to TipoPokemon.SINIESTRO, "Detección" to TipoPokemon.LUCHA),
                    habilidad = "Afortunado",
                    objeto = "Periscopio"
                )
                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 43,
                    movimientos = mapOf("Pirotecnia" to TipoPokemon.FUEGO, "Lengüetazo" to TipoPokemon.FANTASMA, "Golpes Furia" to TipoPokemon.NORMAL, "Malicioso" to TipoPokemon.NORMAL),
                    habilidad = "Gula",
                    objeto = "Carbón"
                )
                val tercero = PokemonRival(
                    especie = especieTercero,
                    nivel = 43,
                    movimientos = mapOf("Tajo Umbrío" to TipoPokemon.SINIESTRO, "Mofa" to TipoPokemon.SINIESTRO, "Cuchillada" to TipoPokemon.NORMAL, "Buena Baza" to TipoPokemon.SINIESTRO),
                    habilidad = "Liviano",
                    objeto = "Baya Zidra"
                )
                val cuarto = PokemonRival(
                    especie = especieCuarto,
                    nivel = 45,
                    movimientos = mapOf("Acua Cola" to TipoPokemon.AGUA, "Otra Vez" to TipoPokemon.NORMAL, "Cuchillada" to TipoPokemon.NORMAL, "Acua Jet" to TipoPokemon.AGUA),
                    habilidad = "Torrente",
                    objeto = "Restos"
                )

                CombateImportante(
                    24,
                    "Cheren",
                    "Ruta 10",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo, tercero, cuarto),
                    R.drawable.cheren,
                    Rutas.RUTA_10
                )
            }
            PokemonInicial.OSHAWOTT.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(27)
                val especieSegundo = Pokedex.getPokemonById(22)
                val especieTercero = Pokedex.getPokemonById(16)
                val especieCuarto = Pokedex.getPokemonById(3)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    nivel = 43,
                    movimientos = mapOf("Viento Cortante" to TipoPokemon.NORMAL, "Tajo Aéreo" to TipoPokemon.VOLADOR, "Mofa" to TipoPokemon.SINIESTRO, "Detección" to TipoPokemon.LUCHA),
                    habilidad = "Afortunado",
                    objeto = "Periscopio"
                )
                val segundo = PokemonRival(
                    especie = especieSegundo,
                    nivel = 43,
                    movimientos = mapOf("Escaldar" to TipoPokemon.AGUA, "Lengüetazo" to TipoPokemon.FANTASMA, "Golpes Furia" to TipoPokemon.NORMAL, "Malicioso" to TipoPokemon.NORMAL),
                    habilidad = "Gula",
                    objeto = "Agua Mística"
                )
                val tercero = PokemonRival(
                    especie = especieTercero,
                    nivel = 43,
                    movimientos = mapOf("Tajo Umbrío" to TipoPokemon.SINIESTRO, "Mofa" to TipoPokemon.SINIESTRO, "Cuchillada" to TipoPokemon.NORMAL, "Buena Baza" to TipoPokemon.SINIESTRO),
                    habilidad = "Liviano",
                    objeto = "Baya Zidra"
                )
                val cuarto = PokemonRival(
                    especie = especieCuarto,
                    nivel = 45,
                    movimientos = mapOf("Gigadrenado" to TipoPokemon.PLANTA, "Enrosque" to TipoPokemon.VENENO, "Hoja Aguda" to TipoPokemon.PLANTA, "Atizar" to TipoPokemon.NORMAL),
                    habilidad = "Espesura",
                    objeto = "Restos"
                )

                CombateImportante(
                    24,
                    "Cheren",
                    "Ruta 10",
                    TipoCombate.RIVAL,
                    mutableListOf(primero, segundo, tercero, cuarto),
                    R.drawable.cheren,
                    Rutas.RUTA_10
                )
            }
            else -> {
                CombateImportante()
            }
        }
    }

    private fun configurarCombate25(): CombateImportante {
        val especiePrimero = Pokedex.getPokemonById(69)
        val especieSegundo = Pokedex.getPokemonById(99)
        val especieTercero = Pokedex.getPokemonById(129)
        val especieCuarto = Pokedex.getPokemonById(115)

        val primero = PokemonRival(
            especie = especiePrimero,
            nivel = 48,
            movimientos = mapOf("Fuego Fatuo" to TipoPokemon.FUEGO, "Bola Sombra" to TipoPokemon.FANTASMA, "Psíquico" to TipoPokemon.PSIQUICO, "Hierba Lazo" to TipoPokemon.PLANTA),
            habilidad = "Momia"
        )
        val segundo = PokemonRival(
            especie = especieSegundo,
            nivel = 48,
            movimientos = mapOf("Surf" to TipoPokemon.AGUA, "Salmuera" to TipoPokemon.AGUA, "Energibola" to TipoPokemon.PLANTA, "Bola Sombra" to TipoPokemon.FANTASMA),
            habilidad = "Cuerpo Maldito"
        )
        val tercero = PokemonRival(
            especie = especieTercero,
            nivel = 48,
            movimientos = mapOf("Demolición" to TipoPokemon.LUCHA, "Maldición" to TipoPokemon.FANTASMA, "Terremoto" to TipoPokemon.TIERRA, "Puño Sombra" to TipoPokemon.FANTASMA),
            habilidad = "Puño Férreo"
        )
        val cuarto = PokemonRival(
            especie = especieCuarto,
            nivel = 50,
            movimientos = mapOf("Llamarada" to TipoPokemon.FUEGO, "Bola Sombra" to TipoPokemon.FANTASMA, "Vendetta" to TipoPokemon.SINIESTRO, "Psíquico" to TipoPokemon.PSIQUICO),
            habilidad = "Cuerpo Llama"
        )

        return CombateImportante(
            25,
            "Anís",
            "Liga Pokémon",
            TipoCombate.LIGA_POKEMON,
            mutableListOf(primero, segundo, tercero, cuarto),
            imagenRival = R.drawable.anis,
            Rutas.CAMARA_PRUEBAS
        )
    }

    private fun configurarCombate26(): CombateImportante {
        val especiePrimero = Pokedex.getPokemonById(66)
        val especieSegundo = Pokedex.getPokemonById(59)
        val especieTercero = Pokedex.getPokemonById(16)
        val especieCuarto = Pokedex.getPokemonById(131)

        val primero = PokemonRival(
            especie = especiePrimero,
            nivel = 48,
            movimientos = mapOf("Demolición" to TipoPokemon.LUCHA, "Triturar" to TipoPokemon.SINIESTRO, "Ataque Arena" to TipoPokemon.TIERRA, "Puya Nociva" to TipoPokemon.VENENO),
            habilidad = "Autoestima"
        )
        val segundo = PokemonRival(
            especie = especieSegundo,
            nivel = 48,
            movimientos = mapOf("Terremoto" to TipoPokemon.TIERRA, "Triturar" to TipoPokemon.SINIESTRO, "Garra Dragón" to TipoPokemon.DRAGON, "Juego Sucio" to TipoPokemon.SINIESTRO),
            habilidad = "Intimidación"
        )
        val tercero = PokemonRival(
            especie = especieTercero,
            nivel = 48,
            movimientos = mapOf("Tajo Umbrío" to TipoPokemon.SINIESTRO, "Sorpresa" to TipoPokemon.NORMAL, "Golpe Aéreo" to TipoPokemon.VOLADOR, "Atracción" to TipoPokemon.NORMAL),
            habilidad = "Flexibilidad"
        )
        val cuarto = PokemonRival(
            especie = especieCuarto,
            nivel = 50,
            movimientos = mapOf("Tajo Umbrío" to TipoPokemon.SINIESTRO, "Garra Metal" to TipoPokemon.ACERO, "Tijera X" to TipoPokemon.BICHO, "Golpe Aéreo" to TipoPokemon.VOLADOR),
            habilidad = "Competitivo"
        )

        return CombateImportante(
            26,
            "Aza",
            "Liga Pokémon",
            TipoCombate.LIGA_POKEMON,
            mutableListOf(primero, segundo, tercero, cuarto),
            imagenRival = R.drawable.aza,
            Rutas.CAMARA_PRUEBAS
        )
    }

    private fun configurarCombate27(): CombateImportante {
        val especiePrimero = Pokedex.getPokemonById(44)
        val especieSegundo = Pokedex.getPokemonById(45)
        val especieTercero = Pokedex.getPokemonById(40)
        val especieCuarto = Pokedex.getPokemonById(126)

        val primero = PokemonRival(
            especie = especiePrimero,
            nivel = 48,
            movimientos = mapOf("Roca Afilada" to TipoPokemon.ROCA, "Terratemblor" to TipoPokemon.TIERRA, "Vendetta" to TipoPokemon.SINIESTRO, "Llave Corsé" to TipoPokemon.LUCHA),
            habilidad = "Agallas"
        )
        val segundo = PokemonRival(
            especie = especieSegundo,
            nivel = 48,
            movimientos = mapOf("Roca Afilada" to TipoPokemon.ROCA, "Golpe Kárate" to TipoPokemon.LUCHA, "Represalia" to TipoPokemon.NORMAL, "Hierba Lazo" to TipoPokemon.PLANTA),
            habilidad = "Robustez"
        )
        val tercero = PokemonRival(
            especie = especieTercero,
            nivel = 48,
            movimientos = mapOf("Machada" to TipoPokemon.LUCHA, "Roca Afilada" to TipoPokemon.ROCA, "Hierba Lazo" to TipoPokemon.PLANTA, "Represalia" to TipoPokemon.NORMAL),
            habilidad = "Potencia Bruta"
        )
        val cuarto = PokemonRival(
            especie = especieCuarto,
            nivel = 50,
            movimientos = mapOf("Avalancha" to TipoPokemon.ROCA, "Ida y Vuelta" to TipoPokemon.BICHO, "Patada Salto" to TipoPokemon.LUCHA, "Represalia" to TipoPokemon.NORMAL),
            habilidad = "Foco Interno"
        )

        return CombateImportante(
            27,
            "Lotto",
            "Liga Pokémon",
            TipoCombate.LIGA_POKEMON,
            mutableListOf(primero, segundo, tercero, cuarto),
            imagenRival = R.drawable.lotto,
            Rutas.CAMARA_PRUEBAS
        )
    }

    private fun configurarCombate28(): CombateImportante {
        val especiePrimero = Pokedex.getPokemonById(85)
        val especieSegundo = Pokedex.getPokemonById(24)
        val especieTercero = Pokedex.getPokemonById(67)
        val especieCuarto = Pokedex.getPokemonById(82)

        val primero = PokemonRival(
            especie = especiePrimero,
            nivel = 48,
            movimientos = mapOf("Trueno" to TipoPokemon.ELECTRICO, "Onda Certera" to TipoPokemon.LUCHA, "Psíquico" to TipoPokemon.PSIQUICO, "Energibola" to TipoPokemon.PLANTA),
            habilidad = "Muro Mágico"
        )
        val segundo = PokemonRival(
            especie = especieSegundo,
            nivel = 48,
            movimientos = mapOf("Psíquico" to TipoPokemon.PSIQUICO, "Rayo Carga" to TipoPokemon.ELECTRICO, "Bola Sombra" to TipoPokemon.FANTASMA, "Reflejo" to TipoPokemon.PSIQUICO),
            habilidad = "Sincronía"
        )
        val tercero = PokemonRival(
            especie = especieTercero,
            nivel = 48,
            movimientos = mapOf("Psíquico" to TipoPokemon.PSIQUICO, "Rayo Hielo" to TipoPokemon.HIELO, "Tajo Aéreo" to TipoPokemon.VOLADOR, "Bola Sombra" to TipoPokemon.FANTASMA),
            habilidad = "Piel Milagro"
        )
        val cuarto = PokemonRival(
            especie = especieCuarto,
            nivel = 50,
            movimientos = mapOf("Psíquico" to TipoPokemon.PSIQUICO, "Rayo" to TipoPokemon.ELECTRICO, "Bola Sombra" to TipoPokemon.FANTASMA, "Paz Mental" to TipoPokemon.PSIQUICO),
            habilidad = "Cacheo"
        )

        return CombateImportante(
            28,
            "Catleya",
            "Liga Pokémon",
            TipoCombate.LIGA_POKEMON,
            mutableListOf(primero, segundo, tercero, cuarto),
            imagenRival = R.drawable.catleya,
            Rutas.CAMARA_PRUEBAS
        )
    }

    private fun configurarCombate29(version: String?): CombateImportante {
        return when (version) {
            VersionJuego.NEGRO.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(150)
                val especieSegundo = Pokedex.getPokemonById(90)
                val especieTercero = Pokedex.getPokemonById(71)
                val especieCuarto = Pokedex.getPokemonById(77)
                val especieQuinto = Pokedex.getPokemonById(73)
                val especieSexto = Pokedex.getPokemonById(107)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    52,
                    mapOf("Rayo Fusión" to TipoPokemon.ELECTRICO, "Cabezazo Zen" to TipoPokemon.PSIQUICO, "Gigaimpacto" to TipoPokemon.NORMAL, "Pantalla Luz" to TipoPokemon.PSIQUICO),
                    "Terravoltaje"
                )
                val segundo = PokemonRival(
                    especie = especieSegundo,
                    50,
                    mapOf("Foco Resplandor" to TipoPokemon.ACERO, "Granizo" to TipoPokemon.HIELO, "Vaho Gélido" to TipoPokemon.HIELO, "Ventisca" to TipoPokemon.HIELO),
                    "Gélido"
                )
                val tercero = PokemonRival(
                    especie = especieTercero,
                    50,
                    mapOf("Roca Afilada" to TipoPokemon.ROCA, "Acua Jet" to TipoPokemon.AGUA, "Cascada" to TipoPokemon.AGUA, "Triturar" to TipoPokemon.SINIESTRO),
                    "Robustez"
                )
                val cuarto = PokemonRival(
                    especie = especieCuarto,
                    50,
                    mapOf("Tajo Umbrío" to TipoPokemon.SINIESTRO, "Onda Certera" to TipoPokemon.LUCHA, "Lanzallamas" to TipoPokemon.FUEGO, "Represalia" to TipoPokemon.NORMAL),
                    "Ilusión"
                )
                val quinto = PokemonRival(
                    especie = especieQuinto,
                    50,
                    mapOf("Roca Afilada" to TipoPokemon.ROCA, "Triturar" to TipoPokemon.SINIESTRO, "Garra Dragón" to TipoPokemon.DRAGON, "Acróbata" to TipoPokemon.VOLADOR),
                    "Flaqueza"
                )
                val sexto = PokemonRival(
                    especie = especieSexto,
                    50,
                    mapOf("Foco Resplandor" to TipoPokemon.ACERO, "Hiperrayo" to TipoPokemon.NORMAL, "Eco Metálico" to TipoPokemon.ACERO, "Rayo" to TipoPokemon.ELECTRICO),
                    "Más"
                )

                CombateImportante(
                    29,
                    "N",
                    "Palacio N",
                    TipoCombate.LIGA_POKEMON,
                    mutableListOf(primero, segundo, tercero, cuarto, quinto, sexto),
                    imagenRival = R.drawable.n,
                    Rutas.PALACIO_DE_N
                )
            }
            VersionJuego.BLANCO.nombre -> {
                val especiePrimero = Pokedex.getPokemonById(149)
                val especieSegundo = Pokedex.getPokemonById(90)
                val especieTercero = Pokedex.getPokemonById(71)
                val especieCuarto = Pokedex.getPokemonById(77)
                val especieQuinto = Pokedex.getPokemonById(73)
                val especieSexto = Pokedex.getPokemonById(107)

                val primero = PokemonRival(
                    especie = especiePrimero,
                    52,
                    mapOf("Llama Fusión" to TipoPokemon.FUEGO, "Paranormal" to TipoPokemon.PSIQUICO, "Hiperrayo" to TipoPokemon.NORMAL, "Reflejo" to TipoPokemon.PSIQUICO),
                    "Turbollama"
                )
                val segundo = PokemonRival(
                    especie = especieSegundo,
                    50,
                    mapOf("Foco Resplandor" to TipoPokemon.ACERO, "Granizo" to TipoPokemon.HIELO, "Vaho Gélido" to TipoPokemon.HIELO, "Ventisca" to TipoPokemon.HIELO),
                    "Gélido"
                )
                val tercero = PokemonRival(
                    especie = especieTercero,
                    50,
                    mapOf("Roca Afilada" to TipoPokemon.ROCA, "Acua Jet" to TipoPokemon.AGUA, "Cascada" to TipoPokemon.AGUA, "Triturar" to TipoPokemon.SINIESTRO),
                    "Robustez"
                )
                val cuarto = PokemonRival(
                    especie = especieCuarto,
                    50,
                    mapOf("Tajo Umbrío" to TipoPokemon.SINIESTRO, "Onda Certera" to TipoPokemon.LUCHA, "Lanzallamas" to TipoPokemon.FUEGO, "Represalia" to TipoPokemon.NORMAL),
                    "Ilusión"
                )
                val quinto = PokemonRival(
                    especie = especieQuinto,
                    50,
                    mapOf("Roca Afilada" to TipoPokemon.ROCA, "Triturar" to TipoPokemon.SINIESTRO, "Garra Dragón" to TipoPokemon.DRAGON, "Acróbata" to TipoPokemon.VOLADOR),
                    "Flaqueza"
                )
                val sexto = PokemonRival(
                    especie = especieSexto,
                    50,
                    mapOf("Foco Resplandor" to TipoPokemon.ACERO, "Hiperrayo" to TipoPokemon.NORMAL, "Eco Metálico" to TipoPokemon.ACERO, "Rayo" to TipoPokemon.ELECTRICO),
                    "Más"
                )

                CombateImportante(
                    29,
                    "N",
                    "Palacio N",
                    TipoCombate.LIGA_POKEMON,
                    mutableListOf(primero, segundo, tercero, cuarto, quinto, sexto),
                    imagenRival = R.drawable.n,
                    Rutas.PALACIO_DE_N
                )
            }
            else -> {
                CombateImportante()
            }
        }
    }

    private fun configurarCombate30(): CombateImportante {
        val especiePrimero = Pokedex.getPokemonById(69)
        val especieSegundo = Pokedex.getPokemonById(132)
        val especieTercero = Pokedex.getPokemonById(110)
        val especieCuarto = Pokedex.getPokemonById(43)
        val especieQuinto = Pokedex.getPokemonById(131)
        val especieSexto = Pokedex.getPokemonById(141)

        val primero = PokemonRival(
            especie = especiePrimero,
            nivel = 52,
            movimientos = mapOf("Tóxico" to TipoPokemon.VENENO, "Protección" to TipoPokemon.NORMAL, "Bola Sombra" to TipoPokemon.FANTASMA, "Psíquico" to TipoPokemon.PSIQUICO),
            habilidad = "Momia"
        )
        val segundo = PokemonRival(
            especie = especieSegundo,
            nivel = 52,
            movimientos = mapOf("Terremoto" to TipoPokemon.TIERRA, "Ariete" to TipoPokemon.NORMAL, "Voltio Cruel" to TipoPokemon.ELECTRICO, "Puya Nociva" to TipoPokemon.VENENO),
            habilidad = "Audaz"
        )
        val tercero = PokemonRival(
            especie = especieTercero,
            nivel = 52,
            movimientos = mapOf("Lanzallamas" to TipoPokemon.FUEGO, "Triturar" to TipoPokemon.SINIESTRO, "Acróbata" to TipoPokemon.VOLADOR, "Voltio Cruel" to TipoPokemon.ELECTRICO),
            habilidad = "Levitación"
        )
        val cuarto = PokemonRival(
            especie = especieCuarto,
            nivel = 52,
            movimientos = mapOf("Danza Lluvia" to TipoPokemon.AGUA, "Terremoto" to TipoPokemon.TIERRA, "Agua Lodosa" to TipoPokemon.AGUA, "Onda Tóxica" to TipoPokemon.VENENO),
            habilidad = "Nado Rápido"
        )
        val quinto = PokemonRival(
            especie = especieQuinto,
            nivel = 52,
            movimientos = mapOf("Roca Afilada" to TipoPokemon.ROCA, "Tijera X" to TipoPokemon.BICHO, "Tajo Umbrío" to TipoPokemon.SINIESTRO, "Represión Metal" to TipoPokemon.ACERO),
            habilidad = "Competitivo"
        )
        val sexto = PokemonRival(
            especie = especieSexto,
            nivel = 54,
            movimientos = mapOf("Pulso Dragón" to TipoPokemon.DRAGON, "Onda Certera" to TipoPokemon.LUCHA, "Surf" to TipoPokemon.AGUA, "Llamarada" to TipoPokemon.FUEGO),
            habilidad = "Levitación"
        )

        return CombateImportante(
            30,
            "Ghechis",
            "Palacio N",
            TipoCombate.LIGA_POKEMON,
            mutableListOf(primero, segundo, tercero, cuarto, quinto, sexto),
            imagenRival = R.drawable.ghechis,
            Rutas.PALACIO_DE_N
        )
    }
}