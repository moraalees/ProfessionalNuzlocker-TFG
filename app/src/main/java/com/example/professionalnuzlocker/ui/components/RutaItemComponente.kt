package com.example.professionalnuzlocker.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.professionalnuzlocker.data.model.EstadoRutaRegistro
import com.example.professionalnuzlocker.data.model.Pokemon
import com.example.professionalnuzlocker.data.model.enum_classes.Rutas
import com.example.professionalnuzlocker.data.repository.RutasRepository
import com.example.professionalnuzlocker.ui.theme.ColorClaro
import com.example.professionalnuzlocker.ui.theme.ColorGrisaceo
import com.example.professionalnuzlocker.ui.theme.ColorOscuro
import com.example.professionalnuzlocker.ui.theme.ColorRojo
import com.example.professionalnuzlocker.ui.theme.ColorRojoVibrante
import com.example.professionalnuzlocker.ui.theme.ColorSnivy
import com.example.professionalnuzlocker.ui.utils.AudioManager
import com.example.professionalnuzlocker.ui.utils.GameSound

@Composable
fun RutaItemComponente(
    ruta: Rutas,
    estado: EstadoRutaRegistro,
    esActiva: Boolean,
    onSeleccionarPokemon: (Pokemon) -> Unit,
    onConfirmarCaptura: (String, String) -> Unit,
    onConfirmarDebilitado: () -> Unit,
    onRutaBloqueadaClick: () -> Unit,
    audioManager: AudioManager? = null
) {
    val bloqueada = estado is EstadoRutaRegistro.PokemonCapturado || estado is EstadoRutaRegistro.PokemonDebilitado
    var expandido by rememberSaveable(key = ruta.name) { mutableStateOf(false) }

    LaunchedEffect(estado) {
        when {
            bloqueada -> expandido = false
            estado is EstadoRutaRegistro.PokemonElegido && estado.esInicial -> expandido = true
        }
    }
    val borderColor = when (estado) {
        is EstadoRutaRegistro.PokemonCapturado -> ColorSnivy
        is EstadoRutaRegistro.PokemonDebilitado -> ColorRojoVibrante.copy(alpha = 0.4f)
        is EstadoRutaRegistro.PokemonElegido -> ColorRojoVibrante
        is EstadoRutaRegistro.RutaLibre -> if (expandido) ColorRojoVibrante else ColorRojo
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = ColorGrisaceo,
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandido = !expandido }
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    if (!expandido) {
                        when (estado) {
                            is EstadoRutaRegistro.PokemonCapturado -> Image(
                                painter = painterResource(id = estado.pokemon.imagen),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
                            )
                            is EstadoRutaRegistro.PokemonDebilitado -> Image(
                                painter = painterResource(id = estado.pokemon.imagen),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp),
                                colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
                            )
                            else -> {}
                        }
                    }
                    Text(
                        text = ruta.nombreRuta,
                        color = if (estado is EstadoRutaRegistro.PokemonDebilitado) ColorClaro.copy(alpha = 0.45f) else ColorClaro,
                        style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    )
                }
                when {
                    estado is EstadoRutaRegistro.PokemonCapturado ->
                        Icon(Icons.Default.Lock, "Candado", tint = ColorSnivy, modifier = Modifier.size(20.dp))
                    estado is EstadoRutaRegistro.PokemonDebilitado ->
                        Icon(Icons.Default.Lock, "Candado", tint = ColorRojoVibrante.copy(alpha = 0.45f), modifier = Modifier.size(20.dp))
                    expandido ->
                        Icon(Icons.Default.KeyboardArrowUp, "Flecha Arriba", tint = ColorRojoVibrante)
                    else ->
                        Icon(Icons.Default.KeyboardArrowDown, "Flecha Abajo", tint = ColorClaro)
                }
            }
            if (!expandido) {
                when (estado) {
                    is EstadoRutaRegistro.PokemonCapturado -> Text(
                        text = "${estado.mote.ifBlank { estado.pokemon.nombre }}  ·  Lv. ${estado.nivel}",
                        color = ColorSnivy.copy(alpha = 0.8f),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 16.dp, bottom = 10.dp)
                    )
                    is EstadoRutaRegistro.PokemonDebilitado -> Text(
                        text = "${estado.pokemon.nombre}  ·  Debilitado",
                        color = ColorRojoVibrante.copy(alpha = 0.55f),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 16.dp, bottom = 10.dp)
                    )
                    else -> {}
                }
            }
            AnimatedVisibility(visible = expandido, enter = expandVertically(), exit = shrinkVertically()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(ColorOscuro)
                ) {
                    when (estado) {
                        is EstadoRutaRegistro.RutaLibre -> PokemonsDisponibles(
                            ruta = ruta,
                            esActiva = esActiva,
                            onSeleccionarPokemon = onSeleccionarPokemon,
                            onRutaBloqueadaClick = onRutaBloqueadaClick,
                            audioManager = audioManager
                        )
                        is EstadoRutaRegistro.PokemonElegido -> SelectorSituacion(
                            pokemon = estado.pokemon,
                            esInicial = estado.esInicial,
                            onConfirmarCaptura = onConfirmarCaptura,
                            onConfirmarDebilitado = onConfirmarDebilitado,
                            audioManager = audioManager
                        )
                        is EstadoRutaRegistro.PokemonCapturado -> InfoCapturado(estado)
                        is EstadoRutaRegistro.PokemonDebilitado -> InfoDebilitado(estado)
                    }
                }
            }
        }
    }
}

@Composable
private fun PokemonsDisponibles(
    ruta: Rutas,
    esActiva: Boolean,
    onSeleccionarPokemon: (Pokemon) -> Unit,
    onRutaBloqueadaClick: () -> Unit,
    audioManager: AudioManager? = null
) {
    val pokemonEnRuta = remember(ruta) { RutasRepository.getPokemonPorRuta(ruta) }

    Column(
        modifier = Modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (!esActiva) {
            Text(
                text = "Solo visualización — completa la ruta activa primero",
                color = ColorRojoVibrante.copy(alpha = 0.8f),
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        }
        if (pokemonEnRuta.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Sin encuentros disponibles",
                    color = ColorClaro.copy(alpha = 0.35f),
                    fontSize = 13.sp
                )
            }
        } else {
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                pokemonEnRuta.forEach { pokemon ->
                    PokemonSeleccionableItem(pokemon = pokemon, esActiva = esActiva) {
                        if (esActiva) {
                            audioManager?.playSound(GameSound.CLICK)
                            onSeleccionarPokemon(pokemon)
                        } else {
                            onRutaBloqueadaClick()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PokemonSeleccionableItem(pokemon: Pokemon, esActiva: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(
                if (esActiva) ColorRojoVibrante.copy(alpha = 0.1f) else Color.Gray.copy(alpha = 0.07f),
                RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = pokemon.imagen),
            contentDescription = pokemon.nombre,
            modifier = Modifier.size(64.dp),
            alpha = if (esActiva) 1f else 0.45f
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = pokemon.nombre,
            color = if (esActiva) ColorClaro else ColorClaro.copy(alpha = 0.35f),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun SelectorSituacion(
    pokemon: Pokemon,
    esInicial: Boolean,
    onConfirmarCaptura: (String, String) -> Unit,
    onConfirmarDebilitado: () -> Unit,
    audioManager: AudioManager? = null
) {
    var chipSeleccionado by rememberSaveable { mutableStateOf(if (esInicial) "capturado" else null as String?) }
    var mote by rememberSaveable { mutableStateOf("") }
    var nivel by rememberSaveable { mutableStateOf(if (esInicial) "5" else "") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = pokemon.imagen),
                contentDescription = pokemon.nombre,
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = pokemon.nombre,
                color = ColorClaro,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 22.sp)
            )
        }

        Text(
            text = if (esInicial) "¡Tu Pokémon inicial! Ponle un mote." else "¿Qué ocurrió en este encuentro?",
            color = ColorClaro.copy(alpha = 0.55f),
            fontSize = 13.sp
        )

        if (!esInicial) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                ChipSituacion(
                    texto = "Capturado",
                    seleccionado = chipSeleccionado == "capturado",
                    color = ColorSnivy
                ) {
                    audioManager?.playSound(GameSound.CLICK)
                    chipSeleccionado = if (chipSeleccionado == "capturado") null else "capturado"
                }

                ChipSituacion(
                    texto = "Debilitado",
                    seleccionado = chipSeleccionado == "debilitado",
                    color = ColorRojoVibrante
                ) {
                    audioManager?.playSound(GameSound.CLICK)
                    chipSeleccionado = if (chipSeleccionado == "debilitado") null else "debilitado"
                }
            }
        }

        AnimatedVisibility(visible = chipSeleccionado == "capturado", enter = expandVertically(), exit = shrinkVertically()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = mote,
                    onValueChange = { mote = it },
                    label = { Text("Mote", color = ColorClaro.copy(alpha = 0.6f)) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ColorRojoVibrante,
                        unfocusedBorderColor = ColorRojo,
                        focusedTextColor = ColorClaro,
                        unfocusedTextColor = ColorClaro,
                        cursorColor = ColorRojoVibrante
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = nivel,
                    onValueChange = { if (it.all { c -> c.isDigit() } && it.length <= 3) nivel = it },
                    label = { Text("Nivel", color = ColorClaro.copy(alpha = 0.6f)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ColorRojoVibrante,
                        unfocusedBorderColor = ColorRojo,
                        focusedTextColor = ColorClaro,
                        unfocusedTextColor = ColorClaro,
                        cursorColor = ColorRojoVibrante
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = {
                        if (mote.isNotBlank() && nivel.isNotBlank()) {
                            audioManager?.playSound(GameSound.CAPTURE)
                            onConfirmarCaptura(mote, nivel)
                        }
                    },
                    enabled = mote.isNotBlank() && nivel.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ColorSnivy,
                        disabledContainerColor = ColorSnivy.copy(alpha = 0.25f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Confirmar captura", color = ColorOscuro, fontWeight = FontWeight.Bold)
                }
            }
        }

        AnimatedVisibility(visible = chipSeleccionado == "debilitado", enter = expandVertically(), exit = shrinkVertically()) {
            Button(
                onClick = {
                    audioManager?.playSound(GameSound.CLICK)
                    onConfirmarDebilitado()
                },
                colors = ButtonDefaults.buttonColors(containerColor = ColorRojoVibrante),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirmar pérdida", color = ColorClaro, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun ChipSituacion(texto: String, seleccionado: Boolean, color: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(if (seleccionado) color else color.copy(alpha = 0.12f), RoundedCornerShape(20.dp))
            .border(1.dp, color, RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(horizontal = 18.dp, vertical = 9.dp)
    ) {
        Text(
            text = texto,
            color = if (seleccionado) ColorOscuro else color,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun InfoCapturado(estado: EstadoRutaRegistro.PokemonCapturado) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(id = estado.pokemon.imagen),
            contentDescription = estado.pokemon.nombre,
            modifier = Modifier.size(72.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = estado.mote.ifBlank { estado.pokemon.nombre },
                color = ColorClaro,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = "Lv. ${estado.nivel}  ·  ${estado.pokemon.nombre}",
                color = ColorSnivy.copy(alpha = 0.75f),
                fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun InfoDebilitado(estado: EstadoRutaRegistro.PokemonDebilitado) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(id = estado.pokemon.imagen),
            contentDescription = estado.pokemon.nombre,
            modifier = Modifier.size(72.dp),
            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = estado.pokemon.nombre,
                color = ColorClaro.copy(alpha = 0.5f),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = "Debilitado",
                color = ColorRojoVibrante.copy(alpha = 0.65f),
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
