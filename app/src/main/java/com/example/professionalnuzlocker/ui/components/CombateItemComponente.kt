package com.example.professionalnuzlocker.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.professionalnuzlocker.R
import com.example.professionalnuzlocker.data.model.CombateImportante
import com.example.professionalnuzlocker.data.model.Partida
import com.example.professionalnuzlocker.data.model.Pokemon
import com.example.professionalnuzlocker.data.model.PokemonCapturado
import com.example.professionalnuzlocker.data.model.PokemonRival
import com.example.professionalnuzlocker.data.model.ResultadoCombate
import com.example.professionalnuzlocker.data.model.enum_classes.TipoCombate
import com.example.professionalnuzlocker.data.model.enum_classes.TipoEntrenador
import com.example.professionalnuzlocker.data.model.enum_classes.TipoPokemon
import com.example.professionalnuzlocker.data.repository.Pokedex
import com.example.professionalnuzlocker.ui.theme.ColorClaro
import com.example.professionalnuzlocker.ui.theme.ColorGrisaceo
import com.example.professionalnuzlocker.ui.theme.ColorOscuro
import com.example.professionalnuzlocker.ui.theme.ColorOshawott
import com.example.professionalnuzlocker.ui.theme.ColorRojo
import com.example.professionalnuzlocker.ui.theme.ColorRojoVibrante
import com.example.professionalnuzlocker.ui.theme.ColorSnivy
import com.example.professionalnuzlocker.ui.theme.ColorTepig
import com.example.professionalnuzlocker.ui.utils.AudioManager

private val fondoCombate = Brush.verticalGradient(colors = listOf(ColorOscuro, Color(0xFF1A0A0E), ColorRojo))

@Composable
fun CombateItemComponente(
    combate: CombateImportante,
    resultados: List<ResultadoCombate> = emptyList(),
    combatesOrdenados: List<CombateImportante> = emptyList(),
    equipo: List<PokemonCapturado> = emptyList(),
    partida: Partida? = null,
    bloqueado: Boolean = false,
    audioManager: AudioManager? = null,
    onBatallaGanada: (combateId: Int, equipoUsadoIds: List<String>) -> Unit = { _, _ -> },
    onBatallaPerdida: (combateId: Int) -> Unit = { _ -> },
    onPokemonMuerto: (pokemonId: String, tipoEntrenador: String, ataque: String?, idPokemonAsesino: Int) -> Unit = { _, _, _, _ -> }
) {
    val nivelMax = combate.equipoRival.maxOfOrNull { it.nivel } ?: 0

    val bordeColorCombate = when (combate.tipoCombate) {
        TipoCombate.RIVAL -> ColorRojoVibrante
        TipoCombate.GIMNASIO -> ColorSnivy
        TipoCombate.EQUIPO_PLASMA -> ColorTepig
        TipoCombate.LIGA_POKEMON -> ColorOshawott
    }

    var pokemonSeleccionado by remember { mutableStateOf<PokemonRival?>(null) }
    var mostrarPantallaCombate by remember { mutableStateOf(false) }

    val yaGanado = resultados.any { it.combateId == combate.id && it.derrotado }
    val yaPerdido = resultados.any { it.combateId == combate.id && !it.derrotado }

    val combatesAntes = combatesOrdenados.filter { it.id < combate.id }

    val desbloqueado = combatesAntes.isEmpty() || combatesAntes.all { ant -> resultados.any { it.combateId == ant.id && it.derrotado } }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = ColorGrisaceo,
        border = BorderStroke(
            1.dp,
            when {
                yaGanado -> ColorSnivy.copy(alpha = 0.5f)
                yaPerdido -> ColorRojo.copy(alpha = 0.5f)
                !desbloqueado -> ColorClaro.copy(alpha = 0.15f)
                else -> bordeColorCombate.copy(alpha = 0.45f)
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(bordeColorCombate.copy(alpha = if (yaGanado || yaPerdido || !desbloqueado) 0.03f else 0.07f))
                    .padding(horizontal = 14.dp, vertical = 7.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CajaTipoCombateImportante(combate.tipoCombate, bordeColorCombate)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (yaGanado) {
                        Box(
                            modifier = Modifier
                                .background(ColorSnivy.copy(alpha = 0.15f), RoundedCornerShape(20.dp))
                                .border(1.dp, ColorSnivy.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "✓ Victoria",
                                color = ColorSnivy,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold)
                        }
                    }
                    if (yaPerdido) {
                        Box(
                            modifier = Modifier
                                .background(ColorRojo.copy(alpha = 0.15f), RoundedCornerShape(20.dp))
                                .border(1.dp, ColorRojo.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "✗ Derrota",
                                color = ColorRojo,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    Text(
                        text = "Nivel máx: $nivelMax",
                        color = ColorClaro.copy(alpha = 0.65f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 14.dp, end = 14.dp, top = 5.dp, bottom = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .background(bordeColorCombate.copy(alpha = 0.6f), CircleShape)
                )
                Text(
                    text = combate.lugar,
                    color = bordeColorCombate.copy(alpha = 0.75f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Italic
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(68.dp)
                ) {
                    Image(
                        painter = painterResource(id = combate.imagenRival),
                        contentDescription = combate.nombreRival,
                        modifier = Modifier.size(68.dp),
                        colorFilter =
                            if (!desbloqueado)
                                ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
                            else
                                null
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = combate.nombreRival,
                        color =
                            if (desbloqueado)
                                ColorClaro
                            else
                                ColorClaro.copy(alpha = 0.4f),
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val casillasPokemon = (0 until 6).map { i -> combate.equipoRival.getOrNull(i) }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        casillasPokemon.take(3).forEach { rival ->
                            CasillaPokemonRival(
                                rival,
                                desbloqueado,
                                onClick = {
                                    pokemonSeleccionado = rival
                                }
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        casillasPokemon.drop(3).forEach { rival ->
                            CasillaPokemonRival(
                                rival,
                                desbloqueado,
                                onClick = {
                                    pokemonSeleccionado = rival
                                }
                            )
                        }
                    }
                }
            }

            if (!yaGanado && !bloqueado) {
                HorizontalDivider(color = bordeColorCombate.copy(alpha = 0.15f), thickness = 1.dp)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (desbloqueado && equipo.isNotEmpty()) {
                        Button(
                            onClick = {
                                audioManager?.playSound(com.example.professionalnuzlocker.ui.utils.GameSound.CLICK)
                                audioManager?.crossfadeToBattle()
                                mostrarPantallaCombate = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = bordeColorCombate),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "⚔  Combatir",
                                color = ColorClaro,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    } else if (desbloqueado && equipo.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(ColorClaro.copy(alpha = 0.04f), RoundedCornerShape(10.dp))
                                .border(1.dp, ColorRojo.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "⚠  Necesitas Pokémon en el equipo",
                                color = ColorRojo.copy(alpha = 0.6f),
                                fontSize = 12.sp,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(ColorClaro.copy(alpha = 0.04f), RoundedCornerShape(10.dp))
                                .border(1.dp, ColorClaro.copy(alpha = 0.1f), RoundedCornerShape(10.dp))
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "🔒  Derrota al anterior entrenador primero",
                                color = ColorClaro.copy(alpha = 0.3f),
                                fontSize = 12.sp,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                }
            }
        }
    }

    pokemonSeleccionado?.let { pokemon ->
        if (pokemon.especie != null) {
            DialogDetallePokemonRival(
                pokemonRival = pokemon,
                onDismiss = { pokemonSeleccionado = null }
            )
        }
    }

    if (mostrarPantallaCombate && partida != null) {
        PantallaCombateDialog(
            combate = combate,
            partida = partida,
            equipo = equipo,
            colorBorde = bordeColorCombate,
            audioManager = audioManager,
            onDismiss = {
                audioManager?.crossfadeToAmbient()
                mostrarPantallaCombate = false
            },
            onBatallaGanada = { equipoUsadoIds ->
                audioManager?.crossfadeToAmbient()
                onBatallaGanada(combate.id, equipoUsadoIds)
                mostrarPantallaCombate = false
            },
            onBatallaPerdida = {
                audioManager?.crossfadeToAmbient()
                onBatallaPerdida(combate.id)
                mostrarPantallaCombate = false
            },
            onPokemonMuerto = onPokemonMuerto
        )
    }
}

@Composable
private fun PantallaCombateDialog(
    combate: CombateImportante,
    partida: Partida,
    equipo: List<PokemonCapturado>,
    colorBorde: Color,
    audioManager: AudioManager? = null,
    onDismiss: () -> Unit,
    onBatallaGanada: (equipoUsadoIds: List<String>) -> Unit,
    onBatallaPerdida: () -> Unit,
    onPokemonMuerto: (pokemonId: String, tipoEntrenador: String, ataque: String?, idPokemonAsesino: Int) -> Unit
) {
    val pokedex = remember { Pokedex.getPokemon() }

    var mostrarDialogResultado by remember { mutableStateOf(false) }
    var pokemonRivalDetalle by remember { mutableStateOf<PokemonRival?>(null) }

    var pokemonsMuertosSeleccionados by remember { mutableStateOf<List<PokemonCapturado>>(emptyList()) }
    var indiceMuerteActual by remember { mutableStateOf(-1) }
    var muertesProcesadas by remember { mutableStateOf(0) }
    var equipoUsadoAlGanar by remember { mutableStateOf<List<String>>(emptyList()) }
    var esPerdida by remember { mutableStateOf(false) }

    val imagenJugador = when (partida.sexoJugador?.lowercase()) {
        "chica" -> R.drawable.liza
        else -> R.drawable.lucho
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false, dismissOnBackPress = true)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(fondoCombate)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(ColorClaro.copy(alpha = 0.08f), CircleShape)
                        .clickable { onDismiss() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "×",
                        color = ColorClaro,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "TU EQUIPO",
                    color = ColorClaro.copy(alpha = 0.4f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp
                )
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = ColorGrisaceo.copy(alpha = 0.85f),
                    border = BorderStroke(1.dp, ColorClaro.copy(alpha = 0.12f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(14.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Image(
                                painter = painterResource(id = imagenJugador),
                                contentDescription = partida.nombreJugador,
                                modifier = Modifier.size(56.dp)
                            )
                            Text(
                                text = partida.nombreJugador,
                                color = ColorClaro,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                        Spacer(Modifier.height(12.dp))
                        val casillasPokemonJugador = (0 until 6).map { i -> equipo.getOrNull(i) }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            casillasPokemonJugador.take(3).forEach { cap ->
                                CasillasPokemonEntrenador(cap, pokedex)
                            }
                        }
                        Spacer(Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            casillasPokemonJugador.drop(3).forEach { cap ->
                                CasillasPokemonEntrenador(cap, pokedex)
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            audioManager?.playSound(com.example.professionalnuzlocker.ui.utils.GameSound.CLICK)
                            mostrarDialogResultado = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorBorde),
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .width(120.dp)
                            .height(52.dp)
                    ) {
                        Text(
                            text = "VS",
                            color = ColorClaro,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 2.sp
                        )
                    }
                }
                Text(
                    text = "RIVAL",
                    color = ColorClaro.copy(alpha = 0.4f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp
                )
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = ColorGrisaceo.copy(alpha = 0.85f),
                    border = BorderStroke(1.dp, colorBorde.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Image(
                                painter = painterResource(id = combate.imagenRival),
                                contentDescription = combate.nombreRival,
                                modifier = Modifier.size(56.dp)
                            )
                            Column {
                                Text(
                                    text = combate.nombreRival,
                                    color = ColorClaro,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = combate.lugar,
                                    color = ColorClaro.copy(alpha = 0.45f),
                                    fontSize = 12.sp
                                )
                            }
                        }
                        Spacer(Modifier.height(12.dp))
                        val casillasPokemonRival = (0 until 6).map { i -> combate.equipoRival.getOrNull(i) }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            casillasPokemonRival.take(3).forEach { rival ->
                                CasillaPokemonRival(rival, true, onClick = { pokemonRivalDetalle = rival })
                            }
                        }
                        Spacer(Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            casillasPokemonRival.drop(3).forEach { rival ->
                                CasillaPokemonRival(rival, true, onClick = { pokemonRivalDetalle = rival })
                            }
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }

    pokemonRivalDetalle?.let { pokemonRival ->
        if (pokemonRival.especie != null) {
            DialogDetallePokemonRival(
                pokemonRival = pokemonRival,
                onDismiss = {
                    pokemonRivalDetalle = null
                }
            )
        }
    }

    if (mostrarDialogResultado) {
        DialogResultadoCombate(
            equipo = equipo,
            pokedex = pokedex,
            audioManager = audioManager,
            onDismiss = { mostrarDialogResultado = false },
            onGanado = {
                mostrarDialogResultado = false
                onBatallaGanada(equipo.map { it.id })
            },
            onGanadoConBajas = { muertos ->
                mostrarDialogResultado = false
                pokemonsMuertosSeleccionados = muertos
                muertesProcesadas = 0
                equipoUsadoAlGanar = equipo.map { it.id }
                indiceMuerteActual = if (muertos.isNotEmpty()) 0 else -1
            },
            onPerdido = {
                mostrarDialogResultado = false
                if (equipo.isNotEmpty()) {
                    pokemonsMuertosSeleccionados = equipo
                    esPerdida = true
                    muertesProcesadas = 0
                    equipoUsadoAlGanar = equipo.map { it.id }
                    indiceMuerteActual = 0
                } else {
                    onBatallaPerdida()
                }
            }
        )
    }

    if (indiceMuerteActual >= 0 && indiceMuerteActual < pokemonsMuertosSeleccionados.size) {
        val cap = pokemonsMuertosSeleccionados[indiceMuerteActual]
        val especie = pokedex.find { it.idPokedex == cap.especieId }
        if (especie != null) {
            DialogFormMuerteCombate(
                capturado = cap,
                especie = especie,
                audioManager = audioManager,
                onDismiss = { indiceMuerteActual = -1 },
                onConfirmar = { tipoEntrenador, ataque, idPokemonAsesino ->
                    onPokemonMuerto(cap.id, tipoEntrenador, ataque, idPokemonAsesino)
                    val siguiente = indiceMuerteActual + 1
                    if (siguiente >= pokemonsMuertosSeleccionados.size) {
                        indiceMuerteActual = -1
                        if (esPerdida) onBatallaPerdida() else onBatallaGanada(equipoUsadoAlGanar)
                    } else {
                        indiceMuerteActual = siguiente
                    }
                }
            )
        }
    }
}

@Composable
private fun DialogResultadoCombate(
    equipo: List<PokemonCapturado>,
    pokedex: List<Pokemon>,
    audioManager: AudioManager? = null,
    onDismiss: () -> Unit,
    onGanado: () -> Unit,
    onGanadoConBajas: (List<PokemonCapturado>) -> Unit,
    onPerdido: () -> Unit
) {
    var mostrarSeleccionMuertos by remember { mutableStateOf(false) }

    if (!mostrarSeleccionMuertos) {
        Dialog(
            onDismissRequest = onDismiss
        ) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = ColorGrisaceo,
                border = BorderStroke(1.dp, ColorClaro.copy(alpha = 0.12f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = "¿Cómo fue el combate?",
                        color = ColorClaro,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(20.dp))
                    Button(
                        onClick = {
                            audioManager?.playSound(com.example.professionalnuzlocker.ui.utils.GameSound.CLICK)
                            onGanado()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = ColorSnivy),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("✓  He ganado", color = ColorClaro, fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(Modifier.height(8.dp))

                    Button(
                        onClick = {
                            audioManager?.playSound(com.example.professionalnuzlocker.ui.utils.GameSound.CLICK)
                            mostrarSeleccionMuertos = true
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = ColorTepig),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("💀  He ganado con bajas", color = ColorClaro, fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(Modifier.height(8.dp))

                    Button(
                        onClick = {
                            audioManager?.playSound(com.example.professionalnuzlocker.ui.utils.GameSound.CLICK)
                            onPerdido()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = ColorRojo),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("✗  He perdido el combate", color = ColorClaro, fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.fillMaxWidth(),
                        border = BorderStroke(1.dp, ColorClaro.copy(alpha = 0.2f)),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ColorClaro.copy(alpha = 0.5f))
                    ) {
                        Text("Cancelar")
                    }
                }
            }
        }
    } else {
        DialogSeleccionMuertos(
            equipo = equipo,
            pokedex = pokedex,
            onDismiss = { mostrarSeleccionMuertos = false },
            onConfirmar = { seleccionados -> onGanadoConBajas(seleccionados) }
        )
    }
}

@Composable
private fun DialogSeleccionMuertos(
    equipo: List<PokemonCapturado>,
    pokedex: List<Pokemon>,
    onDismiss: () -> Unit,
    onConfirmar: (List<PokemonCapturado>) -> Unit
) {
    var seleccionados by remember { mutableStateOf(setOf<String>()) }

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = ColorGrisaceo,
            border = BorderStroke(1.dp, ColorRojo.copy(alpha = 0.4f)),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 520.dp)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "¿Qué Pokémon han caído?",
                        color = ColorClaro,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Selecciona todos los que murieron:",
                        color = ColorClaro.copy(alpha = 0.45f),
                        fontSize = 12.sp
                    )
                    Spacer(Modifier.height(16.dp))

                    equipo.forEach { cap ->
                        val especie = pokedex.find { it.idPokedex == cap.especieId } ?: return@forEach
                        val nombre = if (!cap.mote.isNullOrBlank() && cap.mote != especie.nombre)
                            "${especie.nombre} (${cap.mote})" else especie.nombre
                        val activo = cap.id in seleccionados
                        val tipo1 = especie.tipo1
                        val tipo2 = especie.tipo2

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .clip(shape = RoundedCornerShape(size = 12.dp))
                                .background(
                                    color =
                                        if (activo)
                                            ColorRojo.copy(alpha = 0.12f)
                                        else
                                            ColorClaro.copy(alpha = 0.04f)
                                )
                                .border(
                                    width = 1.dp,
                                    color =
                                        if (activo)
                                            ColorRojo.copy(alpha = 0.6f)
                                        else
                                            ColorClaro.copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable {
                                    seleccionados =
                                        if (activo)
                                            seleccionados - cap.id
                                        else
                                            seleccionados + cap.id
                                }
                                .padding(10.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                val circuloBrush = tipoBrush(tipo1, tipo2, 0.45f)
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(CircleShape)
                                        .background(circuloBrush),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = especie.imagen),
                                        contentDescription = especie.nombre,
                                        modifier = Modifier.size(34.dp),
                                        colorFilter = if (activo) ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) }) else null
                                    )
                                }
                                Text(
                                    text = nombre,
                                    color = if (activo) ColorClaro.copy(alpha = 0.7f) else ColorClaro,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.weight(1f)
                                )
                                if (activo) {
                                    Text(
                                        text = "†",
                                        color = ColorRojo,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f),
                            border = BorderStroke(1.dp, ColorClaro.copy(alpha = 0.2f)),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = ColorClaro.copy(alpha = 0.5f))
                        ) {
                            Text(
                                text = "Cancelar"
                            )
                        }
                        Button(
                            onClick = {
                                val lista = equipo.filter { it.id in seleccionados }
                                if (lista.isNotEmpty()) onConfirmar(lista)
                            },
                            enabled = seleccionados.isNotEmpty(),
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = ColorRojo),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Confirmar",
                                color = ColorClaro,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DialogFormMuerteCombate(
    capturado: PokemonCapturado,
    especie: Pokemon,
    audioManager: AudioManager? = null,
    onDismiss: () -> Unit,
    onConfirmar: (tipoEntrenador: String, ataque: String?, idPokemonAsesino: Int) -> Unit
) {
    var tipoSeleccionado by remember { mutableStateOf<TipoEntrenador?>(null) }
    var ataqueAsesino by remember { mutableStateOf("") }
    var busqueda by remember { mutableStateOf("") }
    var pokemonAsesino by remember { mutableStateOf<Pokemon?>(null) }

    val resultadosBusqueda = remember(busqueda, pokemonAsesino) {
        if (pokemonAsesino == null && busqueda.length >= 2) {
            Pokedex.getPokemon().filter { it.nombre.contains(busqueda, ignoreCase = true) }.take(5)
        } else {
            emptyList()
        }
    }
    val puedeConfirmar = tipoSeleccionado != null && pokemonAsesino != null
    val headerBrush = tipoBrush(especie.tipo1, especie.tipo2, alpha = 0.35f)

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = ColorGrisaceo,
            border = BorderStroke(1.dp, ColorRojo.copy(alpha = 0.4f)),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 580.dp)
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .background(headerBrush),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = especie.imagen),
                        contentDescription = especie.nombre,
                        modifier = Modifier.size(70.dp),
                        colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = "†  ${if (!capturado.mote.isNullOrBlank()) capturado.mote!! else especie.nombre}",
                        color = ColorClaro.copy(alpha = 0.7f),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(16.dp))
                    HorizontalDivider(color = ColorClaro.copy(alpha = 0.08f))
                    Spacer(Modifier.height(14.dp))
                    HelperSeccionTituloFormMuerte("Tipo de entrenador *")
                    Spacer(Modifier.height(8.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        TipoEntrenador.entries.forEach { tipo ->
                            val activo = tipo == tipoSeleccionado
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = if (activo) ColorRojo.copy(alpha = 0.5f) else ColorClaro.copy(alpha = 0.05f),
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = if (activo) ColorRojo.copy(alpha = 0.9f) else ColorClaro.copy(alpha = 0.15f),
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .clickable { tipoSeleccionado = tipo }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = tipo.displayName,
                                    color = if (activo) ColorClaro else ColorClaro.copy(alpha = 0.5f),
                                    fontSize = 11.sp,
                                    fontWeight = if (activo) FontWeight.SemiBold else FontWeight.Normal
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(14.dp))
                    HorizontalDivider(color = ColorClaro.copy(alpha = 0.08f))
                    Spacer(Modifier.height(14.dp))

                    HelperSeccionTituloFormMuerte("Pokémon que lo mató *")
                    Spacer(Modifier.height(8.dp))

                    if (pokemonAsesino == null) {
                        OutlinedTextField(
                            value = busqueda,
                            onValueChange = { busqueda = it },
                            label = { Text("Buscar Pokémon", fontSize = 12.sp) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = ColorClaro,
                                unfocusedTextColor = ColorClaro,
                                focusedBorderColor = ColorRojo,
                                unfocusedBorderColor = ColorClaro.copy(alpha = 0.2f),
                                focusedLabelColor = ColorRojo,
                                unfocusedLabelColor = ColorClaro.copy(alpha = 0.45f),
                                cursorColor = ColorRojo,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            )
                        )

                        if (resultadosBusqueda.isNotEmpty()) {
                            Spacer(Modifier.height(4.dp))
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(ColorOscuro, RoundedCornerShape(8.dp))
                                    .border(1.dp, ColorClaro.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                            ) {
                                resultadosBusqueda.forEachIndexed { index, pok ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                pokemonAsesino = pok
                                                busqueda = pok.nombre
                                            }
                                            .padding(horizontal = 12.dp, vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(id = pok.imagen),
                                            contentDescription = pok.nombre,
                                            modifier = Modifier.size(28.dp)
                                        )
                                        Text(text = pok.nombre, color = ColorClaro, fontSize = 13.sp)
                                    }
                                    if (index < resultadosBusqueda.lastIndex) {
                                        HorizontalDivider(color = ColorClaro.copy(alpha = 0.06f))
                                    }
                                }
                            }
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(ColorRojo.copy(alpha = 0.12f), RoundedCornerShape(10.dp))
                                .border(1.dp, ColorRojo.copy(alpha = 0.35f), RoundedCornerShape(10.dp))
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Image(
                                painter = painterResource(id = pokemonAsesino!!.imagen),
                                contentDescription = pokemonAsesino!!.nombre,
                                modifier = Modifier.size(32.dp)
                            )
                            Text(
                                text = pokemonAsesino!!.nombre,
                                color = ColorClaro,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.weight(1f)
                            )
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(ColorClaro.copy(alpha = 0.1f), CircleShape)
                                    .clickable { pokemonAsesino = null; busqueda = "" },
                                contentAlignment = Alignment.Center
                            ) {
                                Text("×", color = ColorClaro.copy(alpha = 0.7f), fontSize = 15.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                            }
                        }
                    }

                    Spacer(Modifier.height(14.dp))
                    HorizontalDivider(color = ColorClaro.copy(alpha = 0.08f))
                    Spacer(Modifier.height(14.dp))

                    HelperSeccionTituloFormMuerte("Ataque definitivo (opcional)")
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = ataqueAsesino,
                        onValueChange = { ataqueAsesino = it },
                        label = { Text("Nombre del ataque", fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = ColorClaro,
                            unfocusedTextColor = ColorClaro,
                            focusedBorderColor = ColorRojo,
                            unfocusedBorderColor = ColorClaro.copy(alpha = 0.2f),
                            focusedLabelColor = ColorRojo,
                            unfocusedLabelColor = ColorClaro.copy(alpha = 0.45f),
                            cursorColor = ColorRojo,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        )
                    )

                    Spacer(Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                audioManager?.playSound(com.example.professionalnuzlocker.ui.utils.GameSound.CLICK)
                                onDismiss()
                            },
                            modifier = Modifier.weight(1f),
                            border = BorderStroke(1.dp, ColorClaro.copy(alpha = 0.2f)),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = ColorClaro.copy(alpha = 0.6f))
                        ) {
                            Text("Cancelar")
                        }
                        Button(
                            onClick = {
                                if (!puedeConfirmar) return@Button
                                audioManager?.playSound(com.example.professionalnuzlocker.ui.utils.GameSound.CLICK)
                                onConfirmar(
                                    tipoSeleccionado!!.name,
                                    ataqueAsesino.ifBlank { null },
                                    pokemonAsesino!!.idPokedex
                                )
                            },
                            enabled = puedeConfirmar,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = ColorRojo),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Confirmar", color = ColorClaro, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun CasillasPokemonEntrenador(
    cap: PokemonCapturado?,
    pokedex: List<Pokemon>
) {
    val especie = remember(cap?.especieId) { cap?.let { c -> pokedex.find { it.idPokedex == c.especieId } } }
    val tipo1 = especie?.tipo1
    val tipo2 = especie?.tipo2

    val brush: Brush = if (especie != null && tipo1 != null) {
        tipoBrush(tipo1, tipo2, 0.45f)
    } else {
        Brush.verticalGradient(colors = listOf(ColorClaro.copy(alpha = 0.04f), ColorClaro.copy(alpha = 0.04f)))
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(54.dp)
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(brush)
                .border(
                    1.dp,
                    if (especie != null) ColorRojoVibrante.copy(alpha = 0.5f) else ColorClaro.copy(alpha = 0.1f),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (especie != null) {
                Image(
                    painter = painterResource(id = especie.imagen),
                    contentDescription = cap?.mote ?: especie.nombre,
                    modifier = Modifier.size(38.dp)
                )
            }
        }
        Spacer(Modifier.height(2.dp))
        Text(
            text = when {
                cap?.mote?.isNotBlank() == true -> cap.mote!!
                especie != null -> especie.nombre
                else -> ""
            },
            color = if (especie != null) ColorClaro else Color.Transparent,
            fontSize = 9.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
private fun CasillaPokemonRival(rival: PokemonRival?, desbloqueado: Boolean = true, onClick: () -> Unit) {
    val ocupado = rival?.especie != null
    val tipo1 = rival?.especie?.tipo1
    val tipo2 = rival?.especie?.tipo2

    val backgroundBrush: Brush = if (ocupado && tipo1 != null) {
        tipoBrush(tipo1, tipo2, 0.45f)
    } else {
        Brush.verticalGradient(colors = listOf(ColorClaro.copy(alpha = 0.03f), ColorClaro.copy(alpha = 0.03f)))
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(54.dp)
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(backgroundBrush)
                .then(if (ocupado && desbloqueado) Modifier.clickable(onClick = onClick) else Modifier),
            contentAlignment = Alignment.Center
        ) {
            if (ocupado) {
                Image(
                    painter = painterResource(id = rival.especie.imagen),
                    contentDescription = rival.especie.nombre,
                    modifier = Modifier.size(38.dp),
                    colorFilter = if (!desbloqueado) ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) }) else null
                )
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = rival?.especie?.nombre ?: "",
            color = ColorClaro.copy(alpha = if (ocupado && desbloqueado) 0.8f else 0f),
            fontSize = 9.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
private fun DialogDetallePokemonRival(
    pokemonRival: PokemonRival,
    onDismiss: () -> Unit
) {
    val especie = pokemonRival.especie ?: return

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = ColorGrisaceo,
            border = BorderStroke(1.dp, ColorClaro.copy(alpha = 0.12f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Image(
                    painter = painterResource(id = especie.imagen),
                    contentDescription = especie.nombre,
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = especie.nombre,
                    color = ColorClaro,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Nivel ${pokemonRival.nivel}",
                    color = ColorClaro.copy(alpha = 0.55f),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TipoPokemonMedalla(especie.tipo1)
                    especie.tipo2?.let { TipoPokemonMedalla(it) }
                }
                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = ColorClaro.copy(alpha = 0.08f))
                Spacer(modifier = Modifier.height(14.dp))

                val tieneObjeto = !pokemonRival.objeto.isNullOrBlank()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (tieneObjeto) {
                            "Objeto: "
                        } else {
                            "Sin objeto"
                        },
                        color = ColorClaro.copy(alpha = 0.5f),
                        fontSize = 13.sp
                    )
                    if (tieneObjeto) {
                        Text(
                            text = pokemonRival.objeto,
                            color = ColorClaro,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                if (pokemonRival.movimientos.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Movimientos",
                        color = ColorClaro.copy(alpha = 0.5f),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.8.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    val movimientosList = pokemonRival.movimientos.entries.toList()

                    movimientosList.chunked(2).forEach { par ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            par.forEach { (nombre, tipo) ->
                                MovimientoRivalMedalla(nombre = nombre, tipo = tipo, modifier = Modifier.weight(1f))
                            }
                            if (par.size < 2) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun HelperSeccionTituloFormMuerte(texto: String) {
    Text(
        text = texto.uppercase(),
        color = ColorClaro.copy(alpha = 0.4f),
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
    )
}

@Composable
private fun TipoPokemonMedalla(tipo: TipoPokemon) {
    val textColor = if (tipo.color.luminance() > 0.35f) Color(0xFF111111) else ColorClaro
    Box(
        modifier = Modifier
            .background(tipo.color, RoundedCornerShape(20.dp))
            .padding(horizontal = 14.dp, vertical = 5.dp)
    ) {
        Text(
            text = tipo.name.lowercase().replaceFirstChar { it.uppercase() },
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun MovimientoRivalMedalla(nombre: String, tipo: TipoPokemon, modifier: Modifier = Modifier) {
    val bgColor = tipo.color.copy(alpha = 0.82f)
    val textColor = if (tipo.color.luminance() > 0.35f) Color(0xFF111111) else ColorClaro
    Box(
        modifier = modifier
            .background(bgColor, RoundedCornerShape(10.dp))
            .padding(horizontal = 8.dp,
                vertical = 7.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = nombre,
            color = textColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun CajaTipoCombateImportante(tipo: TipoCombate, color: Color) {
    val etiqueta = when (tipo) {
        TipoCombate.RIVAL -> "Rival"
        TipoCombate.GIMNASIO -> "Gimnasio"
        TipoCombate.EQUIPO_PLASMA -> "Equipo Plasma"
        TipoCombate.LIGA_POKEMON -> "Liga Pokémon"
    }
    Box(
        modifier = Modifier
            .background(color.copy(alpha = 0.14f), RoundedCornerShape(20.dp))
            .border(1.dp, color.copy(alpha = 0.45f), RoundedCornerShape(20.dp))
            .padding(horizontal = 10.dp, vertical = 3.dp)
    ) {
        Text(
            text = etiqueta,
            color = color,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

private fun tipoBrush(tipo1: TipoPokemon, tipo2: TipoPokemon?, alpha: Float): Brush =
    if (tipo2 != null) {
        Brush.verticalGradient(
            colorStops = arrayOf(
                0f to tipo1.color.copy(alpha = alpha),
                0.5f to tipo1.color.copy(alpha = alpha),
                0.5f to tipo2.color.copy(alpha = alpha),
                1f to tipo2.color.copy(alpha = alpha)
            )
        )
    } else {
        Brush.verticalGradient(colors = listOf(tipo1.color.copy(alpha = alpha), tipo1.color.copy(alpha = alpha)))
    }
