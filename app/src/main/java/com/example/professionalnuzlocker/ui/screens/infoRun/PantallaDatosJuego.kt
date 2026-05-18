package com.example.professionalnuzlocker.ui.screens.infoRun

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.professionalnuzlocker.R
import com.example.professionalnuzlocker.data.model.Partida
import com.example.professionalnuzlocker.data.model.Pokemon
import com.example.professionalnuzlocker.data.model.PokemonCapturado
import com.example.professionalnuzlocker.data.model.enum_classes.MetodoEvolutivo
import com.example.professionalnuzlocker.data.model.enum_classes.Rutas
import com.example.professionalnuzlocker.data.model.enum_classes.TipoPokemon
import com.example.professionalnuzlocker.data.repository.Pokedex
import com.example.professionalnuzlocker.ui.theme.ColorClaro
import com.example.professionalnuzlocker.ui.theme.ColorGrisaceo
import com.example.professionalnuzlocker.ui.theme.ColorOscuro
import com.example.professionalnuzlocker.ui.theme.ColorRojo
import com.example.professionalnuzlocker.ui.theme.ColorRojoVibrante
import com.example.professionalnuzlocker.ui.theme.ColorCementerio
import com.example.professionalnuzlocker.ui.utils.AudioManager
import com.example.professionalnuzlocker.ui.utils.GameSound
import com.example.professionalnuzlocker.data.model.enum_classes.TipoEntrenador
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun PantallaDatosJuego(audioManager: AudioManager? = null) {
    val viewModel: PantallaDatosJuegoViewModel = viewModel()
    val partida = viewModel.partida
    val pokedex = remember { Pokedex.getPokemon() }

    var tabSeleccionado by remember { mutableIntStateOf(0) }
    var pokemonDetalle by remember { mutableStateOf<PokemonCapturado?>(null) }
    var pokemonPCDetalle by remember { mutableStateOf<PokemonCapturado?>(null) }
    var mostrarDialogSustituir by remember { mutableStateOf(false) }

    val fondoDesvanecido = Brush.verticalGradient(colors = listOf(ColorOscuro, ColorOscuro, ColorRojo))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoDesvanecido)
    ) {
        if (viewModel.cargando) {
            CircularProgressIndicator(
                color = ColorRojo,
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (partida == null) {
            Text(
                text = "No hay ninguna partida activa.",
                color = ColorClaro.copy(alpha = 0.45f),
                fontSize = 15.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "MI PARTIDA",
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.5.sp,
                    fontSize = 32.sp,
                    color = ColorRojo,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 14.dp, bottom = 4.dp)
                )

                TabSelector(
                    tabs = listOf("Equipo", "PC", "Muertos"),
                    seleccionado = tabSeleccionado,
                    onSeleccionar = { tabSeleccionado = it }
                )

                when (tabSeleccionado) {
                    0 -> EquipoContent(
                        partida = partida,
                        pokedex = pokedex,
                        audioManager = audioManager,
                        onPokemonClick = { pokemonDetalle = it }
                    )
                    1 -> PokemonEnPC(
                        partida = partida,
                        pokedex = pokedex,
                        audioManager = audioManager,
                        onPokemonClick = { pokemonPCDetalle = it }
                    )
                    else -> CementerioContent(muertos = partida.muertos, pokedex = pokedex)
                }
            }
        }

        pokemonDetalle?.let { cap ->
            val especie = pokedex.find { it.idPokedex == cap.especieId }
            if (especie != null) {
                DialogDetallePokemon(
                    capturado = cap,
                    especie = especie,
                    pokedex = pokedex,
                    audioManager = audioManager,
                    onDismiss = { pokemonDetalle = null },
                    onGuardarCambios = { mote, nivel, habilidad ->
                        viewModel.actualizarDatosPokemon(cap.id, mote, nivel, habilidad)
                        pokemonDetalle = cap.copy(mote = mote, nivel = nivel, habilidad = habilidad)
                    },
                    onEvolucionar = { nuevaEspecieId, mote, nivel, habilidad ->
                        viewModel.evolucionarPokemon(cap.id, nuevaEspecieId, mote, nivel, habilidad)
                        pokemonDetalle = null
                    }
                )
            }
        }

        pokemonPCDetalle?.let { cap ->
            val especiePC = pokedex.find { it.idPokedex == cap.especieId }
            if (especiePC != null) {
                if (!mostrarDialogSustituir) {
                    DialogDetallePCPokemon(
                        capturado = cap,
                        especie = especiePC,
                        audioManager = audioManager,
                        onDismiss = {
                            pokemonPCDetalle = null
                            mostrarDialogSustituir = false
                        },
                        onAnadirAlEquipo = {
                            if ((partida?.equipo?.size ?: 0) < 6) {
                                audioManager?.playSound(GameSound.CAPTURE)
                                viewModel.moverPCaEquipo(cap.id)
                                pokemonPCDetalle = null
                            } else {
                                mostrarDialogSustituir = true
                            }
                        }
                    )
                } else {
                    DialogSustituirEquipo(
                        equipo = partida?.equipo ?: emptyList(),
                        pokemonPC = cap,
                        especiePC = especiePC,
                        pokedex = pokedex,
                        audioManager = audioManager,
                        onDismiss = { mostrarDialogSustituir = false },
                        onSustituir = { equipoId ->
                            viewModel.intercambiarConEquipo(cap.id, equipoId)
                            mostrarDialogSustituir = false
                            pokemonPCDetalle = null
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun TabSelector(tabs: List<String>, seleccionado: Int, onSeleccionar: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tabs.forEachIndexed { i, label ->
            val activo = i == seleccionado
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = if (activo) ColorRojoVibrante else Color.Transparent,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = if (activo) ColorRojoVibrante else ColorClaro.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable { onSeleccionar(i) }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = label,
                    color = if (activo) ColorClaro else ColorClaro.copy(alpha = 0.45f),
                    fontSize = 13.sp,
                    fontWeight = if (activo) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
private fun EquipoContent(
    partida: Partida,
    pokedex: List<Pokemon>,
    audioManager: AudioManager? = null,
    onPokemonClick: (PokemonCapturado) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item { TarjetaJugador(partida) }

        item {
            Text(
                text = "EQUIPO",
                color = ColorRojo,
                fontWeight = FontWeight.Black,
                fontSize = 11.sp,
                letterSpacing = 1.2.sp,
                modifier = Modifier.padding(top = 8.dp, bottom = 2.dp)
            )
        }

        if (partida.equipo.isEmpty()) {
            item {
                Text(
                    text = "Sin Pokémon en el equipo.",
                    color = ColorClaro.copy(alpha = 0.35f),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp)
                )
            }
        } else {
            items(partida.equipo) { cap ->
                val especie = pokedex.find { it.idPokedex == cap.especieId }
                if (especie != null) {
                    TarjetaPokemonEquipo(cap = cap, especie = especie, audioManager = audioManager, onClick = { onPokemonClick(cap) })
                }
            }
        }

        item { Spacer(Modifier.height(16.dp)) }
    }
}

@Composable
private fun TarjetaJugador(partida: Partida) {
    val imagenJugador = when (partida.sexoJugador?.lowercase()) {
        "chica" -> R.drawable.liza
        else -> R.drawable.lucho
    }
    val imagenVersion = when (partida.versionJuego?.lowercase()) {
        "blanco" -> R.drawable.blanco
        "negro" -> R.drawable.negro
        else -> null
    }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = ColorGrisaceo,
        border = BorderStroke(1.dp, ColorRojoVibrante.copy(alpha = 0.3f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = imagenJugador),
                contentDescription = partida.nombreJugador,
                modifier = Modifier.size(72.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = partida.nombreJugador,
                    color = ColorClaro,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                if (imagenVersion != null) {
                    Spacer(Modifier.height(6.dp))
                    Image(
                        painter = painterResource(id = imagenVersion),
                        contentDescription = partida.versionJuego,
                        modifier = Modifier
                            .width(56.dp)
                            .height(28.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}

@Composable
private fun TarjetaPokemonEquipo(
    cap: PokemonCapturado,
    especie: Pokemon,
    audioManager: AudioManager? = null,
    onClick: () -> Unit
) {
    val tipo1 = especie.tipo1
    val tipo2 = especie.tipo2
    val circuloBrush = tipoBrush(tipo1, tipo2, alpha = 0.45f)

    Surface(
        shape = RoundedCornerShape(14.dp),
        color = ColorGrisaceo,
        border = BorderStroke(1.dp, ColorClaro.copy(alpha = 0.07f)),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { audioManager?.playSound(GameSound.CLICK); onClick() }
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(circuloBrush),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = especie.imagen),
                    contentDescription = especie.nombre,
                    modifier = Modifier.size(44.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = especie.nombre,
                        color = ColorClaro,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (!cap.mote.isNullOrBlank() && cap.mote != especie.nombre) {
                        Text(
                            text = " · ${cap.mote}",
                            color = ColorClaro.copy(alpha = 0.45f),
                            fontSize = 13.sp,
                            fontStyle = FontStyle.Italic,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Spacer(Modifier.height(5.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Nv. ${cap.nivel}",
                        color = ColorClaro.copy(alpha = 0.5f),
                        fontSize = 12.sp
                    )
                    Spacer(Modifier.weight(1f))
                    TipoBadgeSmall(tipo1)
                    tipo2?.let { TipoBadgeSmall(it) }
                }
            }

            Text(
                text = "›",
                color = ColorClaro.copy(alpha = 0.25f),
                fontSize = 22.sp
            )
        }
    }
}

@Composable
private fun CementerioContent(
    muertos: List<PokemonCapturado>,
    pokedex: List<Pokemon>
) {
    val fondo = Brush.verticalGradient(colors = listOf(ColorOscuro, ColorOscuro, ColorCementerio))

    if (muertos.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(fondo),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Ningún caído hasta ahora.",
                color = ColorClaro.copy(alpha = 0.25f),
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(fondo)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(vertical = 14.dp)
        ) {
            items(muertos) { cap ->
                val especie = pokedex.find { it.idPokedex == cap.especieId }
                if (especie != null) {
                    TarjetaMuerto(cap = cap, especie = especie, pokedex = pokedex)
                }
            }
        }
    }
}

@Composable
private fun TarjetaMuerto(
    cap: PokemonCapturado,
    especie: Pokemon,
    pokedex: List<Pokemon>
) {
    val cm = cap.causaMuerte
    val pokemonAsesino = remember(cm?.idPokemonAsesino) {
        val aId = cm?.idPokemonAsesino ?: 0
        if (aId == 0) null else pokedex.find { it.idPokedex == aId }
    }
    val tipoDisplay = remember(cm?.tipoEntrenador) {
        cm?.tipoEntrenador?.let { nombre ->
            TipoEntrenador.entries.find { it.name == nombre }?.displayName ?: nombre
        }
    }

    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color(0xFF1E1B24),
        border = BorderStroke(1.dp, ColorClaro.copy(alpha = 0.06f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(ColorClaro.copy(alpha = 0.04f)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = especie.imagen),
                        contentDescription = especie.nombre,
                        modifier = Modifier.size(42.dp),
                        colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
                    )
                }
                Column {
                    Text(
                        text = if (!cap.mote.isNullOrBlank() && cap.mote != especie.nombre)
                            "${especie.nombre}  ·  ${cap.mote}" else especie.nombre,
                        color = ColorClaro.copy(alpha = 0.6f),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                    cm?.fechaMuerte?.let { ts ->
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = formatearFecha(ts),
                            color = ColorClaro.copy(alpha = 0.3f),
                            fontSize = 10.sp,
                            fontStyle = FontStyle.Italic
                        )
                    }
                    if (tipoDisplay != null) {
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = tipoDisplay,
                            color = ColorClaro.copy(alpha = 0.25f),
                            fontSize = 10.sp
                        )
                    }
                }
            }

            if (pokemonAsesino != null || cm?.ataque != null) {
                Spacer(Modifier.height(10.dp))
                HorizontalDivider(color = ColorClaro.copy(alpha = 0.05f))
                Spacer(Modifier.height(10.dp))

                if (pokemonAsesino != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = pokemonAsesino.imagen),
                            contentDescription = pokemonAsesino.nombre,
                            modifier = Modifier.size(28.dp),
                            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
                        )
                        Text(
                            text = pokemonAsesino.nombre,
                            color = ColorClaro.copy(alpha = 0.45f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                cm?.ataque?.let { ataque ->
                    Spacer(Modifier.height(5.dp))
                    Text(
                        text = "\"$ataque\"",
                        color = ColorClaro.copy(alpha = 0.3f),
                        fontSize = 11.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}

@Composable
private fun PokemonEnPC(
    partida: Partida,
    pokedex: List<Pokemon>,
    audioManager: AudioManager? = null,
    onPokemonClick: (PokemonCapturado) -> Unit
) {
    var busqueda by remember { mutableStateOf("") }
    var tipoFiltrado by remember { mutableStateOf<TipoPokemon?>(null) }

    val tiposDisponibles = remember(partida.pc) {
        partida.pc.mapNotNull { cap ->
            val especie = pokedex.find { it.idPokedex == cap.especieId } ?: return@mapNotNull null
            listOfNotNull(especie.tipo1, especie.tipo2)
        }.flatten().distinct().sortedBy { it.name }
    }

    val pcFiltrado = remember(partida.pc, busqueda, tipoFiltrado) {
        partida.pc.filter { cap ->
            val especie = pokedex.find { it.idPokedex == cap.especieId } ?: return@filter false
            val nombreOk = busqueda.isBlank() ||
                especie.nombre.contains(busqueda, ignoreCase = true) ||
                (!cap.mote.isNullOrBlank() && cap.mote!!.contains(busqueda, ignoreCase = true))
            val tipoOk = tipoFiltrado == null ||
                especie.tipo1 == tipoFiltrado || especie.tipo2 == tipoFiltrado
            nombreOk && tipoOk
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = busqueda,
            onValueChange = { busqueda = it },
            label = { Text("Buscar en el PC", fontSize = 12.sp) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = ColorClaro,
                unfocusedTextColor = ColorClaro,
                focusedBorderColor = ColorRojoVibrante,
                unfocusedBorderColor = ColorClaro.copy(alpha = 0.2f),
                focusedLabelColor = ColorRojoVibrante,
                unfocusedLabelColor = ColorClaro.copy(alpha = 0.45f),
                cursorColor = ColorRojoVibrante,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )

        if (tiposDisponibles.isNotEmpty()) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp)
            ) {
                item {
                    val activo = tipoFiltrado == null
                    Box(
                        modifier = Modifier
                            .background(
                                if (activo) ColorRojoVibrante.copy(alpha = 0.2f) else ColorClaro.copy(alpha = 0.06f),
                                RoundedCornerShape(20.dp)
                            )
                            .border(
                                1.dp,
                                if (activo) ColorRojoVibrante.copy(alpha = 0.7f) else ColorClaro.copy(alpha = 0.15f),
                                RoundedCornerShape(20.dp)
                            )
                            .clickable { tipoFiltrado = null }
                            .padding(horizontal = 12.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = "Todos",
                            color = if (activo) ColorClaro else ColorClaro.copy(alpha = 0.5f),
                            fontSize = 11.sp,
                            fontWeight = if (activo) FontWeight.SemiBold else FontWeight.Normal
                        )
                    }
                }
                items(tiposDisponibles) { tipo ->
                    val activo = tipoFiltrado == tipo
                    val textColor = if (tipo.color.luminance() > 0.35f) Color(0xFF111111) else ColorClaro
                    Box(
                        modifier = Modifier
                            .background(
                                if (activo) tipo.color else tipo.color.copy(alpha = 0.2f),
                                RoundedCornerShape(20.dp)
                            )
                            .border(
                                1.dp,
                                tipo.color.copy(alpha = if (activo) 1f else 0.4f),
                                RoundedCornerShape(20.dp)
                            )
                            .clickable { tipoFiltrado = if (activo) null else tipo }
                            .padding(horizontal = 12.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = tipo.name.lowercase().replaceFirstChar { it.uppercase() },
                            color = if (activo) textColor else ColorClaro.copy(alpha = 0.7f),
                            fontSize = 11.sp,
                            fontWeight = if (activo) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(4.dp))

        if (pcFiltrado.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = if (partida.pc.isEmpty()) "La caja está vacía." else "Sin resultados.",
                    color = ColorClaro.copy(alpha = 0.35f),
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(vertical = 10.dp)
            ) {
                items(pcFiltrado) { cap ->
                    val especie = pokedex.find { it.idPokedex == cap.especieId }
                    if (especie != null) {
                        TarjetaPokemonPC(
                            cap = cap,
                            especie = especie,
                            audioManager = audioManager,
                            onClick = { onPokemonClick(cap) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TarjetaPokemonPC(
    cap: PokemonCapturado,
    especie: Pokemon,
    audioManager: AudioManager? = null,
    onClick: () -> Unit
) {
    val tipo1 = especie.tipo1
    val tipo2 = especie.tipo2

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .border(1.dp, tipo1.color.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
            .clickable { audioManager?.playSound(GameSound.CLICK); onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(tipoBrush(tipo1, tipo2, alpha = 0.55f)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = especie.imagen),
                contentDescription = especie.nombre,
                modifier = Modifier.size(86.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(ColorGrisaceo)
                .padding(horizontal = 10.dp, vertical = 8.dp)
        ) {
            Text(
                text = especie.nombre,
                color = ColorClaro,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (!cap.mote.isNullOrBlank() && cap.mote != especie.nombre) {
                Text(
                    text = cap.mote!!,
                    color = ColorClaro.copy(alpha = 0.5f),
                    fontSize = 10.sp,
                    fontStyle = FontStyle.Italic,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(Modifier.height(5.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                TipoBadgeSmall(tipo1)
                tipo2?.let { TipoBadgeSmall(it) }
            }
        }
    }
}

@Composable
private fun DialogDetallePCPokemon(
    capturado: PokemonCapturado,
    especie: Pokemon,
    audioManager: AudioManager? = null,
    onDismiss: () -> Unit,
    onAnadirAlEquipo: () -> Unit
) {
    val ruta = remember(capturado.rutaId) { Rutas.entries.find { it.nombreRuta == capturado.rutaId } }
    var mostrarRutaGrande by remember { mutableStateOf(false) }
    val tipo1 = especie.tipo1
    val tipo2 = especie.tipo2
    val headerBrush = tipoBrush(tipo1, tipo2, alpha = 0.55f)

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = ColorGrisaceo,
            border = BorderStroke(1.dp, ColorClaro.copy(alpha = 0.1f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .background(headerBrush),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = especie.imagen),
                        contentDescription = especie.nombre,
                        modifier = Modifier.size(110.dp)
                    )
                }

                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {

                    Text(especie.nombre, color = ColorClaro, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    if (!capturado.mote.isNullOrBlank() && capturado.mote != especie.nombre) {
                        Text(
                            text = "\"${capturado.mote}\"",
                            color = ColorClaro.copy(alpha = 0.5f),
                            fontSize = 14.sp,
                            fontStyle = FontStyle.Italic
                        )
                    }

                    Spacer(Modifier.height(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TipoBadge(tipo1)
                        tipo2?.let { TipoBadge(it) }
                        Spacer(Modifier.weight(1f))
                        Box(
                            modifier = Modifier
                                .background(ColorClaro.copy(alpha = 0.08f), RoundedCornerShape(8.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "Nv. ${capturado.nivel}",
                                color = ColorClaro,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(Modifier.height(14.dp))
                    HorizontalDivider(color = ColorClaro.copy(alpha = 0.08f))
                    Spacer(Modifier.height(14.dp))

                    SeccionLabel("Habilidad")
                    Spacer(Modifier.height(6.dp))
                    val tieneHabilidad = capturado.habilidad.isNotBlank()
                    Text(
                        text = if (tieneHabilidad) capturado.habilidad else "No establecida",
                        color = if (tieneHabilidad) ColorClaro else ColorClaro.copy(alpha = 0.35f),
                        fontSize = 14.sp,
                        fontStyle = if (tieneHabilidad) FontStyle.Normal else FontStyle.Italic
                    )

                    Spacer(Modifier.height(14.dp))
                    HorizontalDivider(color = ColorClaro.copy(alpha = 0.08f))
                    Spacer(Modifier.height(14.dp))

                    SeccionLabel("Ruta de captura")
                    Spacer(Modifier.height(8.dp))
                    if (ruta != null) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Image(
                                painter = painterResource(id = ruta.imagen),
                                contentDescription = ruta.nombreRuta,
                                modifier = Modifier
                                    .width(80.dp)
                                    .height(50.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        audioManager?.playSound(GameSound.CLICK)
                                        mostrarRutaGrande = true
                                    },
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = ruta.nombreRuta,
                                color = ColorClaro,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    } else {
                        Text(
                            text = capturado.rutaId.ifBlank { "Desconocida" },
                            color = ColorClaro.copy(alpha = 0.5f),
                            fontSize = 14.sp
                        )
                    }

                    if (!capturado.objeto.isNullOrBlank()) {
                        Spacer(Modifier.height(14.dp))
                        HorizontalDivider(color = ColorClaro.copy(alpha = 0.08f))
                        Spacer(Modifier.height(14.dp))
                        SeccionLabel("Objeto")
                        Spacer(Modifier.height(6.dp))
                        Text(capturado.objeto!!, color = ColorClaro, fontSize = 14.sp)
                    }

                    capturado.fechaCaptura?.let { ts ->
                        Spacer(Modifier.height(14.dp))
                        HorizontalDivider(color = ColorClaro.copy(alpha = 0.08f))
                        Spacer(Modifier.height(14.dp))
                        SeccionLabel("Capturado el")
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = formatearFecha(ts),
                            color = ColorClaro.copy(alpha = 0.55f),
                            fontSize = 13.sp
                        )
                    }

                    Spacer(Modifier.height(20.dp))

                    Button(
                        onClick = {
                            audioManager?.playSound(GameSound.CLICK)
                            onAnadirAlEquipo()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = ColorRojoVibrante),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Añadir al equipo", color = ColorClaro, fontWeight = FontWeight.SemiBold)
                    }
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }

    if (mostrarRutaGrande && ruta != null) {
        DialogImagenRutaGrande(ruta = ruta, onDismiss = { mostrarRutaGrande = false })
    }
}

@Composable
private fun DialogSustituirEquipo(
    equipo: List<PokemonCapturado>,
    pokemonPC: PokemonCapturado,
    especiePC: Pokemon,
    pokedex: List<Pokemon>,
    audioManager: AudioManager? = null,
    onDismiss: () -> Unit,
    onSustituir: (pokemonEquipoId: String) -> Unit
) {
    val nombrePC = if (!pokemonPC.mote.isNullOrBlank() && pokemonPC.mote != especiePC.nombre)
        "${especiePC.nombre} (${pokemonPC.mote})" else especiePC.nombre

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = ColorGrisaceo,
            border = BorderStroke(1.dp, ColorClaro.copy(alpha = 0.1f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                Text(
                    text = "¿A quién quieres sustituir?",
                    color = ColorClaro,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "$nombrePC entrará al equipo",
                    color = ColorClaro.copy(alpha = 0.45f),
                    fontSize = 12.sp
                )
                Spacer(Modifier.height(16.dp))

                equipo.forEach { cap ->
                    val especie = pokedex.find { it.idPokedex == cap.especieId } ?: return@forEach
                    val circuloBrush = tipoBrush(especie.tipo1, especie.tipo2, alpha = 0.45f)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(ColorClaro.copy(alpha = 0.05f))
                            .border(1.dp, ColorClaro.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                            .clickable {
                                audioManager?.playSound(GameSound.CLICK)
                                onSustituir(cap.id)
                            }
                            .padding(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(circuloBrush),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = especie.imagen),
                                    contentDescription = especie.nombre,
                                    modifier = Modifier.size(36.dp)
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = especie.nombre,
                                    color = ColorClaro,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp
                                )
                                if (!cap.mote.isNullOrBlank() && cap.mote != especie.nombre) {
                                    Text(
                                        text = cap.mote!!,
                                        color = ColorClaro.copy(alpha = 0.45f),
                                        fontSize = 11.sp,
                                        fontStyle = FontStyle.Italic
                                    )
                                }
                            }
                            Text(
                                text = "Nv. ${cap.nivel}",
                                color = ColorClaro.copy(alpha = 0.4f),
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                Spacer(Modifier.height(14.dp))
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, ColorClaro.copy(alpha = 0.2f)),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = ColorClaro.copy(alpha = 0.6f))
                ) {
                    Text("Cancelar")
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DialogDetallePokemon(
    capturado: PokemonCapturado,
    especie: Pokemon,
    pokedex: List<Pokemon>,
    audioManager: AudioManager? = null,
    onDismiss: () -> Unit,
    onGuardarCambios: (mote: String?, nivel: Int, habilidad: String) -> Unit,
    onEvolucionar: (nuevaEspecieId: Int, mote: String?, nivel: Int, habilidad: String) -> Unit
) {
    var moteEditado by remember { mutableStateOf(capturado.mote ?: "") }
    var nivelEditado by remember { mutableIntStateOf(capturado.nivel) }
    var habilidadActual by remember { mutableStateOf(capturado.habilidad) }
    var mostrarDialogEvo by remember { mutableStateOf(false) }
    var mostrarRutaGrande by remember { mutableStateOf(false) }

    val ruta = remember(capturado.rutaId) { Rutas.entries.find { it.nombreRuta == capturado.rutaId } }
    val tipo1 = especie.tipo1
    val tipo2 = especie.tipo2
    val headerBrush = tipoBrush(tipo1, tipo2, alpha = 0.55f)
    val cadenaEvo = remember(especie.lineaEvolutiva) {
        especie.lineaEvolutiva.mapNotNull { id -> pokedex.find { it.idPokedex == id } }
    }
    val siguienteEspecie = remember(especie) {
        val idx = especie.lineaEvolutiva.indexOf(especie.idPokedex)
        if (idx >= 0 && idx + 1 < especie.lineaEvolutiva.size)
            pokedex.find { it.idPokedex == especie.lineaEvolutiva[idx + 1] }
        else null
    }
    val nivelRequerido = remember(especie) {
        especie.metodoEvolutivo[MetodoEvolutivo.NIVEL]?.filter { it.isDigit() }?.toIntOrNull()
    }
    val nivelValido = nivelEditado > 0
    val bloqueadoPorNivel = nivelRequerido != null && nivelEditado < nivelRequerido

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = ColorGrisaceo,
            border = BorderStroke(1.dp, ColorClaro.copy(alpha = 0.1f)),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 540.dp)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .background(headerBrush),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = especie.imagen),
                        contentDescription = especie.nombre,
                        modifier = Modifier.size(90.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = especie.nombre,
                        color = ColorClaro,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(12.dp))

                    SeccionLabel("Mote")
                    Spacer(Modifier.height(6.dp))
                    CampoTexto(
                        value = moteEditado,
                        onValueChange = { moteEditado = it },
                        label = "Mote (opcional)",
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(12.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TipoBadge(tipo1)
                        tipo2?.let { TipoBadge(it) }
                        Spacer(Modifier.weight(1f))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(ColorClaro.copy(alpha = 0.08f), RoundedCornerShape(8.dp))
                                    .border(1.dp, ColorClaro.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                                    .clickable { if (nivelEditado > 1) nivelEditado-- },
                                contentAlignment = Alignment.Center
                            ) {
                                Text("−", color = ColorClaro, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            }
                            Text(
                                text = "$nivelEditado",
                                color = ColorClaro,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.width(32.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(ColorRojoVibrante.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                                    .border(1.dp, ColorRojoVibrante.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                                    .clickable { if (nivelEditado < 100) nivelEditado++ },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "+",
                                    color = ColorRojoVibrante,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))
                    HorizontalDivider(color = ColorClaro.copy(alpha = 0.08f))
                    Spacer(Modifier.height(14.dp))

                    SeccionLabel("Ruta de captura")
                    Spacer(Modifier.height(8.dp))
                    if (ruta != null) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Image(
                                painter = painterResource(id = ruta.imagen),
                                contentDescription = ruta.nombreRuta,
                                modifier = Modifier
                                    .width(80.dp)
                                    .height(50.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        audioManager?.playSound(GameSound.CLICK)
                                        mostrarRutaGrande = true
                                    },
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = ruta.nombreRuta,
                                color = ColorClaro,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    } else {
                        Text(
                            text = capturado.rutaId.ifBlank { "Desconocida" },
                            color = ColorClaro.copy(alpha = 0.5f),
                            fontSize = 14.sp
                        )
                    }

                    Spacer(Modifier.height(14.dp))
                    HorizontalDivider(color = ColorClaro.copy(alpha = 0.08f))
                    Spacer(Modifier.height(14.dp))

                    SeccionLabel("Objeto")
                    Spacer(Modifier.height(8.dp))
                    val tieneObjeto = !capturado.objeto.isNullOrBlank()
                    Text(
                        text = if (tieneObjeto) capturado.objeto!! else "Sin objeto",
                        color = if (tieneObjeto) ColorClaro else ColorClaro.copy(alpha = 0.35f),
                        fontSize = 14.sp,
                        fontStyle = if (tieneObjeto) FontStyle.Normal else FontStyle.Italic
                    )

                    Spacer(Modifier.height(14.dp))
                    HorizontalDivider(color = ColorClaro.copy(alpha = 0.08f))
                    Spacer(Modifier.height(14.dp))

                    SeccionLabel("Habilidad")
                    Spacer(Modifier.height(8.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        especie.habilidades.forEach { hab ->
                            val activa = hab == habilidadActual
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = if (activa) ColorRojoVibrante.copy(alpha = 0.18f)
                                        else ColorClaro.copy(alpha = 0.05f),
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = if (activa) ColorRojoVibrante.copy(alpha = 0.7f)
                                        else ColorClaro.copy(alpha = 0.15f),
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .clickable { habilidadActual = hab }
                                    .padding(horizontal = 14.dp, vertical = 7.dp)
                            ) {
                                Text(
                                    text = hab,
                                    color = if (activa) ColorClaro else ColorClaro.copy(alpha = 0.45f),
                                    fontSize = 13.sp,
                                    fontWeight = if (activa) FontWeight.SemiBold else FontWeight.Normal
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))
                    HorizontalDivider(color = ColorClaro.copy(alpha = 0.08f))
                    Spacer(Modifier.height(14.dp))

                    Button(
                        onClick = {
                            audioManager?.playSound(GameSound.LEVEL_UP)
                            onGuardarCambios(moteEditado.ifBlank { null }, nivelEditado, habilidadActual)
                            onDismiss()
                        },
                        enabled = nivelValido,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = ColorRojoVibrante),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Guardar cambios", color = ColorClaro, fontWeight = FontWeight.SemiBold)
                    }

                    if (siguienteEspecie != null) {
                        Spacer(Modifier.height(8.dp))
                        if (bloqueadoPorNivel && nivelRequerido != null) {
                            Text(
                                text = "Debe ser nivel $nivelRequerido para evolucionar",
                                color = ColorRojo.copy(alpha = 0.75f),
                                fontSize = 11.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 4.dp)
                            )
                        }
                        OutlinedButton(
                            onClick = { mostrarDialogEvo = true },
                            enabled = !bloqueadoPorNivel && nivelValido,
                            modifier = Modifier.fillMaxWidth(),
                            border = BorderStroke(1.dp, ColorClaro.copy(alpha = 0.25f)),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = ColorClaro,
                                disabledContentColor = ColorClaro.copy(alpha = 0.3f)
                            )
                        ) {
                            Text(
                                text = "Evolucionar a ${siguienteEspecie.nombre}",
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    if (cadenaEvo.size > 1) {
                        Spacer(Modifier.height(14.dp))
                        HorizontalDivider(color = ColorClaro.copy(alpha = 0.08f))
                        Spacer(Modifier.height(14.dp))
                        SeccionLabel("Cadena evolutiva")
                        Spacer(Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            cadenaEvo.forEachIndexed { index, pok ->
                                val esActual = pok.idPokedex == especie.idPokedex
                                EvoSlot(pok = pok, esActual = esActual)

                                if (index < cadenaEvo.lastIndex) {
                                    val label = formatearMetodoEvolutivo(cadenaEvo[index].metodoEvolutivo)
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.padding(horizontal = 2.dp)
                                    ) {
                                        Text("→", color = ColorClaro.copy(alpha = 0.3f), fontSize = 20.sp)
                                        if (label.isNotBlank()) {
                                            Text(
                                                text = label,
                                                color = ColorClaro.copy(alpha = 0.4f),
                                                fontSize = 9.sp,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }

    if (mostrarRutaGrande && ruta != null) {
        DialogImagenRutaGrande(ruta = ruta, onDismiss = { mostrarRutaGrande = false })
    }

    if (mostrarDialogEvo && siguienteEspecie != null) {
        DialogEvolucion(
            especieActual = especie,
            especieEvolucion = siguienteEspecie,
            moteInicial = moteEditado.ifBlank { null },
            nivelInicial = nivelEditado,
            onDismiss = { mostrarDialogEvo = false },
            onConfirmar = { mote, nivel, habilidad ->
                onEvolucionar(siguienteEspecie.idPokedex, mote, nivel, habilidad)
                mostrarDialogEvo = false
            }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DialogEvolucion(
    especieActual: Pokemon,
    especieEvolucion: Pokemon,
    moteInicial: String?,
    nivelInicial: Int,
    onDismiss: () -> Unit,
    onConfirmar: (mote: String?, nivel: Int, habilidad: String) -> Unit
) {
    var mote by remember { mutableStateOf(moteInicial ?: "") }
    var nivelStr by remember { mutableStateOf(nivelInicial.toString()) }
    var habilidadEvo by remember { mutableStateOf("") }

    val tipo1 = especieEvolucion.tipo1
    val tipo2 = especieEvolucion.tipo2
    val headerBrush = tipoBrush(tipo1, tipo2, alpha = 0.55f)
    val nivelValido = nivelStr.toIntOrNull()?.let { it > 0 } ?: false
    val puedeConfirmar = habilidadEvo.isNotBlank() && nivelValido

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = ColorGrisaceo,
            border = BorderStroke(1.dp, ColorClaro.copy(alpha = 0.1f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .background(headerBrush),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = especieEvolucion.imagen),
                        contentDescription = especieEvolucion.nombre,
                        modifier = Modifier.size(110.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = "${especieActual.nombre}  →  ${especieEvolucion.nombre}",
                        color = ColorClaro.copy(alpha = 0.45f),
                        fontSize = 12.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = especieEvolucion.nombre,
                        color = ColorClaro,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        TipoBadge(tipo1)
                        tipo2?.let { TipoBadge(it) }
                    }

                    Spacer(Modifier.height(14.dp))
                    HorizontalDivider(color = ColorClaro.copy(alpha = 0.08f))
                    Spacer(Modifier.height(14.dp))

                    SeccionLabel("Mote")
                    Spacer(Modifier.height(6.dp))
                    CampoTexto(
                        value = mote,
                        onValueChange = { mote = it },
                        label = "Mote (opcional)",
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(12.dp))

                    SeccionLabel("Nivel")
                    Spacer(Modifier.height(6.dp))
                    CampoTexto(
                        value = nivelStr,
                        onValueChange = { v ->
                            if (v.isEmpty() || (v.length <= 3 && v.all { it.isDigit() }))
                                nivelStr = v
                        },
                        label = "Nivel",
                        modifier = Modifier.width(120.dp),
                        keyboardType = KeyboardType.Number,
                        isError = !nivelValido && nivelStr.isNotEmpty()
                    )

                    Spacer(Modifier.height(14.dp))
                    HorizontalDivider(color = ColorClaro.copy(alpha = 0.08f))
                    Spacer(Modifier.height(14.dp))

                    SeccionLabel("Habilidad *")
                    Spacer(Modifier.height(6.dp))
                    if (habilidadEvo.isBlank()) {
                        Text(
                            text = "Selecciona una habilidad para continuar",
                            color = ColorRojo.copy(alpha = 0.7f),
                            fontSize = 11.sp,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        especieEvolucion.habilidades.forEach { hab ->
                            val activa = hab == habilidadEvo
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = if (activa) ColorRojoVibrante.copy(alpha = 0.18f)
                                        else ColorClaro.copy(alpha = 0.05f),
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = if (activa) ColorRojoVibrante.copy(alpha = 0.7f)
                                        else ColorClaro.copy(alpha = 0.15f),
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .clickable { habilidadEvo = hab }
                                    .padding(horizontal = 14.dp, vertical = 7.dp)
                            ) {
                                Text(
                                    text = hab,
                                    color = if (activa) ColorClaro else ColorClaro.copy(alpha = 0.45f),
                                    fontSize = 13.sp,
                                    fontWeight = if (activa) FontWeight.SemiBold else FontWeight.Normal
                                )
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
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = ColorClaro.copy(alpha = 0.6f)
                            )
                        ) {
                            Text("Cancelar")
                        }
                        Button(
                            onClick = {
                                val nivel = nivelStr.toIntOrNull() ?: return@Button
                                onConfirmar(mote.ifBlank { null }, nivel, habilidadEvo)
                            },
                            enabled = puedeConfirmar,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = ColorRojoVibrante),
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
private fun EvoSlot(pok: Pokemon, esActual: Boolean) {
    val circuloBrush = tipoBrush(pok.tipo1, pok.tipo2, alpha = if (esActual) 0.45f else 0.12f)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(60.dp)
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(circuloBrush)
                .then(
                    if (esActual) Modifier.border(1.5.dp, pok.tipo1.color.copy(alpha = 0.7f), CircleShape)
                    else Modifier
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = pok.imagen),
                contentDescription = pok.nombre,
                modifier = Modifier.size(42.dp)
            )
        }
        Spacer(Modifier.height(3.dp))
        Text(
            text = pok.nombre,
            color = if (esActual) ColorClaro else ColorClaro.copy(alpha = 0.45f),
            fontSize = 9.sp,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun DialogImagenRutaGrande(ruta: Rutas, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = ColorGrisaceo,
            border = BorderStroke(1.dp, ColorRojoVibrante.copy(alpha = 0.5f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = ruta.nombreRuta,
                    color = ColorClaro,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Image(
                    painter = painterResource(id = ruta.imagen),
                    contentDescription = ruta.nombreRuta,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
private fun CampoTexto(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 12.sp) },
        modifier = modifier,
        singleLine = true,
        isError = isError,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = ColorClaro,
            unfocusedTextColor = ColorClaro,
            focusedBorderColor = ColorRojoVibrante,
            unfocusedBorderColor = ColorClaro.copy(alpha = 0.2f),
            errorBorderColor = ColorRojo,
            focusedLabelColor = ColorRojoVibrante,
            unfocusedLabelColor = ColorClaro.copy(alpha = 0.45f),
            errorLabelColor = ColorRojo,
            cursorColor = ColorRojoVibrante,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent
        )
    )
}

@Composable
private fun SeccionLabel(texto: String) {
    Text(
        text = texto.uppercase(),
        color = ColorClaro.copy(alpha = 0.4f),
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
    )
}

@Composable
private fun TipoBadge(tipo: TipoPokemon) {
    val textColor = if (tipo.color.luminance() > 0.35f) Color(0xFF111111) else ColorClaro
    Box(
        modifier = Modifier
            .background(tipo.color, RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 5.dp)
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
private fun TipoBadgeSmall(tipo: TipoPokemon) {
    val textColor = if (tipo.color.luminance() > 0.35f) Color(0xFF111111) else ColorClaro
    Box(
        modifier = Modifier
            .background(tipo.color, RoundedCornerShape(20.dp))
            .padding(horizontal = 7.dp, vertical = 3.dp)
    ) {
        Text(
            text = tipo.name.lowercase().replaceFirstChar { it.uppercase() },
            color = textColor,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
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
        Brush.verticalGradient(
            colors = listOf(tipo1.color.copy(alpha = alpha), tipo1.color.copy(alpha = alpha))
        )
    }

private fun formatearFecha(timestamp: com.google.firebase.Timestamp): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy · HH:mm", Locale.getDefault())
    return sdf.format(timestamp.toDate())
}

private fun formatearMetodoEvolutivo(metodo: Map<MetodoEvolutivo, String>): String {
    if (metodo.isEmpty()) return ""
    val (tipo, valor) = metodo.entries.first()
    return when (tipo) {
        MetodoEvolutivo.NIVEL -> valor
        MetodoEvolutivo.PIEDRA -> valor
        MetodoEvolutivo.INTERCAMBIO -> "Intercambio"
        MetodoEvolutivo.AMISTAD -> "Amistad"
        MetodoEvolutivo.NINGUNO -> ""
    }
}
