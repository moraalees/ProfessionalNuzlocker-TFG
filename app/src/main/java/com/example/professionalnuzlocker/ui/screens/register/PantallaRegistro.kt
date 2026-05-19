package com.example.professionalnuzlocker.ui.screens.register

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.professionalnuzlocker.data.model.EstadoRutaRegistro
import com.example.professionalnuzlocker.data.model.Pokemon
import com.example.professionalnuzlocker.data.model.PokemonCapturado
import com.example.professionalnuzlocker.data.model.enum_classes.Rutas
import com.example.professionalnuzlocker.data.model.enum_classes.TipoEntrenador
import com.example.professionalnuzlocker.data.model.enum_classes.TipoPokemon
import com.example.professionalnuzlocker.data.repository.Pokedex
import com.example.professionalnuzlocker.ui.components.CombateItemComponente
import com.example.professionalnuzlocker.ui.components.RutaItemComponente
import com.example.professionalnuzlocker.ui.theme.ColorClaro
import com.example.professionalnuzlocker.ui.theme.ColorGrisaceo
import com.example.professionalnuzlocker.ui.theme.ColorOscuro
import com.example.professionalnuzlocker.ui.theme.ColorRojo
import com.example.professionalnuzlocker.ui.theme.ColorRojoVibrante
import com.example.professionalnuzlocker.ui.utils.GameSound
import kotlinx.coroutines.launch

/**
 * Pantalla principal de registro de la aventura Nuzlocke.
 *
 * Muestra una lista combinada de rutas y combates importantes. En cada ruta el jugador
 * puede seleccionar el Pokémon encontrado, capturarlo (con mote y nivel) o marcarlo como
 * debilitado. En cada combate puede registrar el resultado y el equipo usado. Cuando un
 * Pokémon del equipo muere, aparece el diálogo de causa de muerte. Al detectar el fin del
 * Nuzlocke ([MotivoFin]), navega a [PantallaEstadisticas] via [onIrEstadisticas].
 */
@Composable
fun PantallaRegistro(
    onIrEstadisticas: () -> Unit = {},
    viewModel: PantallaRegistroViewModel = viewModel(),
    audioManager: com.example.professionalnuzlocker.ui.utils.AudioManager? = null
) {
    val fondoDesvanecido = Brush.verticalGradient(
        colors = listOf(ColorOscuro, ColorOscuro, ColorRojo)
    )
    val lazyListState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val estados = viewModel.estados
    val rutaActiva = viewModel.getRutaActiva()
    val listaItems = viewModel.listaItems
    val pokedex = remember { Pokedex.getPokemon() }

    var pokemonParaMatar by remember { mutableStateOf<PokemonCapturado?>(null) }
    var mostrarFormMuerte by remember { mutableStateOf(false) }
    var mostrarAnimacionFin by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.motivoFinDeJuego) {
        if (viewModel.motivoFinDeJuego != null && !viewModel.cargando) {
            mostrarAnimacionFin = true
            delay(3000)
            mostrarAnimacionFin = false
            delay(600)
            val total = lazyListState.layoutInfo.totalItemsCount
            if (total > 0) lazyListState.animateScrollToItem(total - 1)
        }
    }

    LaunchedEffect(viewModel.cargando) {
        if (!viewModel.cargando && viewModel.motivoFinDeJuego != null) {
            delay(400)
            val total = lazyListState.layoutInfo.totalItemsCount
            if (total > 0) lazyListState.animateScrollToItem(total - 1)
        }
    }

    val onRutaBloqueadaClick: () -> Unit = {
        coroutineScope.launch {
            if (viewModel.motivoFinDeJuego != null) {
                val msg = when (viewModel.motivoFinDeJuego) {
                    is MotivoFin.SinVidas -> "La partida ha terminado: te quedaste sin vidas"
                    is MotivoFin.CombatePerdido -> "La partida ha terminado: perdiste un combate"
                    is MotivoFin.Victoria -> "La partida ha terminado: ¡Locke completado!"
                    null -> ""
                }
                snackbarHostState.showSnackbar(msg, duration = SnackbarDuration.Short)
            } else {
                val activa = viewModel.getRutaActiva()
                val index = listaItems.indexOfFirst { it is RegistroItem.RutaItem && it.ruta == activa }
                if (index >= 0) lazyListState.animateScrollToItem(index + 1)
                snackbarHostState.showSnackbar(
                    message = "Primero resuelve: ${activa?.nombreRuta ?: "la ruta pendiente"}",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoDesvanecido)
    ) {
        Scaffold(
            topBar = {
                EquipoTopBar(
                    equipo = viewModel.equipoActual,
                    vidas = viewModel.vidas,
                    motivoFin = viewModel.motivoFinDeJuego,
                    onPokemonClick = { cap ->
                        audioManager?.playSound(GameSound.CLICK)
                        if (viewModel.motivoFinDeJuego == null) pokemonParaMatar = cap
                    }
                )
            },
            snackbarHost = {
                SnackbarHost(snackbarHostState) { data ->
                    Snackbar(
                        snackbarData = data,
                        containerColor = ColorGrisaceo,
                        contentColor = ColorClaro,
                        actionColor = ColorRojoVibrante
                    )
                }
            },
            containerColor = Color.Transparent,
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            if (viewModel.cargando) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = ColorRojo)
                }
            } else {
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Text(
                                text = "REGISTRO",
                                style = TextStyle(
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.5.sp,
                                    fontSize = 38.sp,
                                    lineHeight = 10.sp
                                ),
                                color = ColorRojo,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }

                    items(
                        items = listaItems,
                        key = { item ->
                            when (item) {
                                is RegistroItem.RutaItem -> item.ruta.name
                                is RegistroItem.CombateItem -> "combate_${item.combate.id}_${item.combate.nombreRival}"
                            }
                        }
                    ) { item ->
                        when (item) {
                            is RegistroItem.RutaItem -> {
                                val estado = estados[item.ruta] ?: EstadoRutaRegistro.RutaLibre
                                RutaItemComponente(
                                    ruta = item.ruta,
                                    estado = estado,
                                    esActiva = item.ruta == rutaActiva && viewModel.motivoFinDeJuego == null,
                                    onSeleccionarPokemon = { pokemon -> viewModel.seleccionarPokemon(item.ruta, pokemon) },
                                    onConfirmarCaptura = { mote, nivel -> viewModel.confirmarCaptura(item.ruta, mote, nivel) },
                                    onConfirmarDebilitado = { viewModel.confirmarDebilitado(item.ruta) },
                                    onRutaBloqueadaClick = onRutaBloqueadaClick,
                                    audioManager = audioManager
                                )
                            }
                            is RegistroItem.CombateItem -> CombateItemComponente(
                                combate = item.combate,
                                resultados = viewModel.resultadosCombates,
                                combatesOrdenados = viewModel.combatesOrdenados,
                                equipo = viewModel.equipoActual,
                                partida = viewModel.partidaInfo,
                                bloqueado = viewModel.motivoFinDeJuego != null,
                                audioManager = audioManager,
                                onBatallaGanada = { combateId, equipoUsadoIds ->
                                    viewModel.registrarBatalla(combateId, ganada = true, equipoUsadoIds = equipoUsadoIds)
                                },
                                onBatallaPerdida = { combateId ->
                                    viewModel.registrarBatalla(combateId, ganada = false, equipoUsadoIds = emptyList())
                                },
                                onPokemonMuerto = { pokemonId, tipoEntrenador, ataque, idPokemonAsesino ->
                                    viewModel.matarPokemon(pokemonId, tipoEntrenador, ataque, idPokemonAsesino)
                                }
                            )
                        }
                    }

                    item {
                        viewModel.motivoFinDeJuego?.let { motivo ->
                            BotonFinDeLocke(motivo = motivo, onIrEstadisticas = onIrEstadisticas)
                        }
                    }

                    item { Spacer(modifier = Modifier.height(16.dp)) }
                }
            }
        }

        AnimatedVisibility(
            visible = mostrarAnimacionFin,
            enter = fadeIn(animationSpec = tween(600)),
            exit = fadeOut(animationSpec = tween(600)),
            modifier = Modifier.fillMaxSize()
        ) {
            val (titulo, descripcion) = when (viewModel.motivoFinDeJuego) {
                is MotivoFin.SinVidas -> "FIN DEL LOCKE" to "Te has quedado sin vidas."
                is MotivoFin.CombatePerdido -> "FIN DEL LOCKE" to "Has perdido un combate importante."
                is MotivoFin.Victoria -> "¡LOCKE COMPLETADO!" to "Has derrotado a todos los entrenadores."
                null -> "FIN DEL LOCKE" to ""
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ColorOscuro.copy(alpha = 0.92f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(32.dp)
                ) {
                    Text(
                        text = titulo,
                        color = ColorRojo,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp,
                        textAlign = TextAlign.Center
                    )
                    if (descripcion.isNotEmpty()) {
                        Text(
                            text = descripcion,
                            color = ColorClaro.copy(alpha = 0.85f),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

    pokemonParaMatar?.let { cap ->
        val especie = pokedex.find { it.idPokedex == cap.especieId }
        if (especie != null) {
            if (!mostrarFormMuerte) {
                DialogDetalleEquipoPokemon(
                    capturado = cap,
                    especie = especie,
                    onDismiss = { pokemonParaMatar = null },
                    onAnadirAlPC = {
                        viewModel.moverEquipoAPC(cap.id)
                        pokemonParaMatar = null
                    },
                    onConfirmarMuerte = { mostrarFormMuerte = true },
                    audioManager = audioManager
                )
            } else {
                DialogFormMuerte(
                    capturado = cap,
                    especie = especie,
                    onDismiss = {
                        mostrarFormMuerte = false
                        pokemonParaMatar = null
                    },
                    onConfirmar = { tipoEntrenador, ataque, idPokemonAsesino ->
                        viewModel.matarPokemon(cap.id, tipoEntrenador, ataque, idPokemonAsesino)
                        mostrarFormMuerte = false
                        pokemonParaMatar = null
                    },
                    audioManager = audioManager
                )
            }
        }
    }
}

/** Barra superior fija con los seis slots del equipo activo y el contador de vidas (o "FIN DE LOCKE" si ya terminó). */
@Composable
private fun EquipoTopBar(
    equipo: List<PokemonCapturado>,
    vidas: Int,
    motivoFin: MotivoFin?,
    onPokemonClick: (PokemonCapturado) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(ColorGrisaceo)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(60.dp)
            ) {
                if (vidas < 0 || motivoFin is MotivoFin.CombatePerdido) {
                    Text(
                        text = "FIN DE\nLOCKE",
                        color = ColorRojo,
                        fontWeight = FontWeight.Black,
                        fontSize = 9.sp,
                        letterSpacing = 0.5.sp,
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(
                        text = "VIDAS",
                        color = ColorRojo,
                        fontWeight = FontWeight.Black,
                        fontSize = 9.sp,
                        letterSpacing = 0.8.sp
                    )
                    Text(
                        text = "$vidas/10",
                        color = ColorClaro.copy(alpha = 0.45f),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(6) { index ->
                    SlotEquipo(
                        pokemonCapturado = equipo.getOrNull(index),
                        onClick = onPokemonClick
                    )
                }
            }
        }
        HorizontalDivider(color = ColorRojo.copy(alpha = 0.4f), thickness = 1.dp)
    }
}

/** Slot circular para un miembro del equipo; vacío si no hay Pokémon en esa posición. El fondo usa el gradiente de tipos del Pokémon. */
@Composable
private fun SlotEquipo(
    pokemonCapturado: PokemonCapturado?,
    onClick: (PokemonCapturado) -> Unit
) {
    val pokemon = remember(pokemonCapturado?.especieId) {
        pokemonCapturado?.let { cap -> Pokedex.getPokemon().find { it.idPokedex == cap.especieId } }
    }

    val tipo1 = pokemon?.tipo1
    val tipo2 = pokemon?.tipo2

    val backgroundBrush: Brush = if (pokemon != null && tipo1 != null) {
        if (tipo2 != null) {
            Brush.verticalGradient(
                colorStops = arrayOf(
                    0f to tipo1.color.copy(alpha = 0.45f),
                    0.5f to tipo1.color.copy(alpha = 0.45f),
                    0.5f to tipo2.color.copy(alpha = 0.45f),
                    1f to tipo2.color.copy(alpha = 0.45f)
                )
            )
        } else {
            Brush.verticalGradient(
                colors = listOf(tipo1.color.copy(alpha = 0.45f), tipo1.color.copy(alpha = 0.45f))
            )
        }
    } else {
        Brush.verticalGradient(
            colors = listOf(ColorClaro.copy(alpha = 0.04f), ColorClaro.copy(alpha = 0.04f))
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(46.dp)
            .then(
                if (pokemonCapturado != null) Modifier.clickable { onClick(pokemonCapturado) }
                else Modifier
            )
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(backgroundBrush)
                .border(
                    width = 1.dp,
                    color = if (pokemon != null) ColorRojoVibrante.copy(alpha = 0.6f) else ColorClaro.copy(alpha = 0.12f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (pokemon != null) {
                Image(
                    painter = painterResource(id = pokemon.imagen),
                    contentDescription = pokemonCapturado?.mote ?: pokemon.nombre,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = when {
                pokemonCapturado?.mote?.isNotBlank() == true -> pokemonCapturado.mote!!
                pokemon != null -> pokemon.nombre
                else -> ""
            },
            color = if (pokemon != null) ColorClaro else ColorClaro.copy(alpha = 0.15f),
            fontSize = 8.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/** Diálogo de detalle de un Pokémon del equipo activo: muestra especie, mote, nivel, tipos, habilidad y ruta de captura; permite enviarlo al PC o marcarlo como caído. */
@Composable
private fun DialogDetalleEquipoPokemon(
    capturado: PokemonCapturado,
    especie: Pokemon,
    onDismiss: () -> Unit,
    onAnadirAlPC: () -> Unit,
    onConfirmarMuerte: () -> Unit,
    audioManager: com.example.professionalnuzlocker.ui.utils.AudioManager? = null
) {
    val ruta = remember(capturado.rutaId) { Rutas.entries.find { it.nombreRuta == capturado.rutaId } }
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
                                    .clip(RoundedCornerShape(8.dp)),
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

                    Spacer(Modifier.height(20.dp))

                    Button(
                        onClick = { audioManager?.playSound(GameSound.CLICK); onAnadirAlPC() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = ColorClaro.copy(alpha = 0.12f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Añadir al PC", color = ColorClaro, fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(Modifier.height(8.dp))

                    Button(
                        onClick = { audioManager?.playSound(GameSound.CLICK); onConfirmarMuerte() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = ColorRojo),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("†  El Pokémon ha caído", color = ColorClaro, fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}

/** Formulario de registro de muerte: el jugador selecciona el tipo de entrenador, busca el Pokémon asesino y opcionalmente escribe el ataque que mató al Pokémon caído. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DialogFormMuerte(
    capturado: PokemonCapturado,
    especie: Pokemon,
    onDismiss: () -> Unit,
    onConfirmar: (tipoEntrenador: String, ataque: String?, idPokemonAsesino: Int) -> Unit,
    audioManager: com.example.professionalnuzlocker.ui.utils.AudioManager? = null
) {
    var tipoSeleccionado by remember { mutableStateOf<TipoEntrenador?>(null) }
    var ataqueStr by remember { mutableStateOf("") }
    var busqueda by remember { mutableStateOf("") }
    var pokemonAsesino by remember { mutableStateOf<Pokemon?>(null) }

    val resultadosBusqueda = remember(busqueda, pokemonAsesino) {
        if (pokemonAsesino == null && busqueda.length >= 2)
            Pokedex.getPokemon().filter { it.nombre.contains(busqueda, ignoreCase = true) }.take(5)
        else emptyList()
    }

    val puedeConfirmar = tipoSeleccionado != null && pokemonAsesino != null

    val headerBrush = tipoBrush(especie.tipo1, especie.tipo2, alpha = 0.35f)

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = ColorGrisaceo,
            border = BorderStroke(1.dp, ColorRojo.copy(alpha = 0.4f)),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 580.dp)
        ) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

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

                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {

                    Text(
                        text = "†  ${if (!capturado.mote.isNullOrBlank()) capturado.mote!! else especie.nombre}",
                        color = ColorClaro.copy(alpha = 0.7f),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(16.dp))
                    HorizontalDivider(color = ColorClaro.copy(alpha = 0.08f))
                    Spacer(Modifier.height(14.dp))

                    SeccionLabel("Tipo de entrenador *")
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

                    SeccionLabel("Pokémon que lo mató *")
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
                                        Text(
                                            text = pok.nombre,
                                            color = ColorClaro,
                                            fontSize = 13.sp
                                        )
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
                                    .clickable {
                                        pokemonAsesino = null
                                        busqueda = ""
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "×",
                                    color = ColorClaro.copy(alpha = 0.7f),
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(14.dp))
                    HorizontalDivider(color = ColorClaro.copy(alpha = 0.08f))
                    Spacer(Modifier.height(14.dp))

                    SeccionLabel("Ataque definitivo (opcional)")
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = ataqueStr,
                        onValueChange = { ataqueStr = it },
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
                            onClick = { audioManager?.playSound(GameSound.CLICK); onDismiss() },
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
                                audioManager?.playSound(GameSound.CLICK)
                                onConfirmar(
                                    tipoSeleccionado!!.name,
                                    ataqueStr.ifBlank { null },
                                    pokemonAsesino!!.idPokedex
                                )
                            },
                            enabled = puedeConfirmar,
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

                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}

/** Banner con el motivo del fin del Nuzlocke ([MotivoFin]) y botón para navegar a las estadísticas. */
@Composable
private fun BotonFinDeLocke(
    motivo: MotivoFin,
    onIrEstadisticas: () -> Unit
) {
    val (titulo, descripcion) = when (motivo) {
        MotivoFin.SinVidas -> "FIN DEL LOCKE" to "Te has quedado sin vidas. Tu aventura ha terminado."
        MotivoFin.CombatePerdido -> "FIN DEL LOCKE" to "Has perdido un combate importante. Tu aventura ha terminado."
        MotivoFin.Victoria -> "¡LOCKE COMPLETADO!" to "Has derrotado a todos los entrenadores. ¡Enhorabuena!"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(ColorRojo.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
                .border(2.dp, ColorRojo.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = titulo,
                    color = ColorRojo,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = descripcion,
                    color = ColorClaro.copy(alpha = 0.8f),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Button(
            onClick = onIrEstadisticas,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = ColorRojo),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(
                text = "Ver estadísticas",
                color = ColorClaro,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

/** Texto de etiqueta de sección en mayúsculas, tamaño pequeño y baja opacidad. */
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

/** Píldora de color para mostrar el tipo de un Pokémon. */
@Composable
private fun TipoBadge(tipo: TipoPokemon) {
    Box(
        modifier = Modifier
            .background(tipo.color, RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 5.dp)
    ) {
        Text(
            text = tipo.name.lowercase().replaceFirstChar { it.uppercase() },
            color = ColorClaro,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/** Devuelve un [Brush] vertical con los colores de [tipo1] y [tipo2] (o monocromo si no hay segundo tipo) al [alpha] indicado. */
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
