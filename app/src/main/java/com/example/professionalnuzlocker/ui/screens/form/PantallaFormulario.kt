package com.example.professionalnuzlocker.ui.screens.form

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.professionalnuzlocker.R
import com.example.professionalnuzlocker.ui.components.CuadroDialogoComponente
import com.example.professionalnuzlocker.ui.components.ImagenEnRecuadro
import com.example.professionalnuzlocker.ui.theme.ColorClaro
import com.example.professionalnuzlocker.ui.theme.ColorOscuro
import com.example.professionalnuzlocker.ui.theme.ColorRojo
import com.example.professionalnuzlocker.ui.theme.ColorRojoVibrante
import com.example.professionalnuzlocker.ui.theme.ColorSnivy
import com.example.professionalnuzlocker.ui.theme.ColorTepig
import com.example.professionalnuzlocker.ui.theme.ColorOshawott
import com.example.professionalnuzlocker.data.model.Partida
import com.example.professionalnuzlocker.data.model.enum_classes.PokemonInicial
import com.example.professionalnuzlocker.data.model.enum_classes.VersionJuego
import com.example.professionalnuzlocker.ui.utils.AudioManager
import com.example.professionalnuzlocker.ui.utils.GameSound
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Pantalla de configuración inicial de la partida Nuzlocke, presentada como diálogo
 * introductorio con la Prof. Encina.
 *
 * El jugador selecciona secuencialmente: versión del juego, sexo, nombre e inicial.
 * Al confirmar el último paso, guarda la [Partida] en Firestore mediante
 * [PantallaFormularioViewModel] e invoca [empezarRegistro] para navegar a [PantallaRegistro].
 */
@Composable
fun PantallaFormulario(
    empezarRegistro: () -> Unit,
    audioManager: AudioManager
) {

    val viewModel: PantallaFormularioViewModel = viewModel()

    val fondoDesvanecido = Brush.verticalGradient(
        colors = listOf(ColorOscuro, ColorOscuro, ColorRojo)
    )

    var indice by rememberSaveable { mutableIntStateOf(0) }
    var visible by rememberSaveable { mutableStateOf(true) }
    var bloqueado by rememberSaveable { mutableStateOf(false) }

    var mostrarOpciones by rememberSaveable { mutableStateOf(false) }
    var sexoJugador by rememberSaveable { mutableStateOf<String?>(null) }

    var mostrarVersion by rememberSaveable { mutableStateOf(false) }
    var versionJuego by rememberSaveable { mutableStateOf<String?>(null) }

    var mostrarInputNombre by rememberSaveable { mutableStateOf(false) }
    var nombreJugador by rememberSaveable { mutableStateOf("") }

    var pokemonMostrados by rememberSaveable { mutableIntStateOf(0) } // 0..3
    var pokemonSeleccionado by rememberSaveable { mutableStateOf<String?>(null) }

    var guardadoEjecutado by rememberSaveable { mutableStateOf(false) }

    val estadoGuardado by viewModel.estadoGuardado.collectAsState()

    val dialogos = listOf(
        "Te doy la bienvenida al maravilloso mundo de los Pokémon. Soy la profesora Encina, mucho gusto.",
        "Estás apunto de vivir una experiencia en la región de Teselia. Por tanto, necesito saber qué versión del juego vas a jugar.",
        "Vale, aquí es donde tu aventura comienza, pero antes de nada, necesito conocerte un poco mejor.",
        "Dime, ¿eres un chico o una chica?",
        "Perfecto, ¿y cómo te llamas?",
        "¡Encantada de conocerte! Espero que estés listo/a para comenzar tu nueva aventura.",
        "Pero antes, debes elegir tu Pokémon inicial.",
        "Primero, tenemos a Snivy, el Pokémon serpiente de tipo planta. Es ágil y astuto, perfecto para quienes buscan velocidad y estrategia.",
        "Luego está Tepig, el Pokémon cerdito de tipo fuego. Es fuerte y resistente, ideal para quienes prefieren un enfoque más directo y ofensivo.",
        "Y por último, Oshawott, el Pokémon nutria de tipo agua. Es equilibrado y versátil, una excelente opción para quienes quieren un compañero confiable en cualquier situación.",
        "Y bien, ¿por quién te decantas?",
        "¡Excelente elección! Estoy segura de que $pokemonSeleccionado va a servirte de mucha ayuda en tu Nuzlocke.",
        "Ahora sí, tu aventura está a punto de comenzar. ¡Buena suerte, $nombreJugador! Recuerda que el mundo Pokémon está lleno de desafíos.",
        "Perderás compañeros, descubrirás especies que no pensaste usar nunca, y, sobre todo, te divertirás como nunca.",
        "¡Ve y gana el Nuzlocke! ¡Mucha suerte!"
    )

    val scope = rememberCoroutineScope()

    LaunchedEffect(indice) {
        val ultimo = dialogos.lastIndex
        if (indice >= ultimo && !guardadoEjecutado) {
            guardadoEjecutado = true

            val partida = Partida(
                versionJuego = versionJuego,
                nombreJugador = nombreJugador,
                sexoJugador = sexoJugador,
                pokemonInicial = pokemonSeleccionado,
                medallas = mutableListOf(),
                equipo = mutableListOf(),
                pc = mutableListOf(),
                muertos = mutableListOf(),
                resultadosCombates = mutableListOf(),
                encuentrosRutas = mutableListOf(),
                vidas = 10
            )

            viewModel.guardarPartida(partida)
        }
    }

    LaunchedEffect(estadoGuardado) {
        estadoGuardado?.let { res ->
            if (!res.isSuccess) {
                guardadoEjecutado = false
            }
        }
    }

    LaunchedEffect(indice) {
        when (indice) {
            1 -> {
                if (versionJuego == null) {
                    mostrarVersion = true
                    bloqueado = true
                } else {
                    mostrarVersion = false
                    bloqueado = false
                }
            }

            3 -> {
                if (sexoJugador == null) {
                    mostrarOpciones = true
                    mostrarInputNombre = false
                    bloqueado = true
                } else {
                    mostrarOpciones = false
                    mostrarInputNombre = false
                    bloqueado = false
                }
            }

            4 -> {
                mostrarOpciones = false
                mostrarInputNombre = true
                bloqueado = true
            }

            7 -> {
                pokemonMostrados = 1
                bloqueado = false
                mostrarInputNombre = false
                mostrarOpciones = false
            }

            8 -> {
                pokemonMostrados = 2
                bloqueado = false
            }

            9 -> {
                pokemonMostrados = 3
                bloqueado = false
            }

            10 -> {
                pokemonMostrados = 3
                bloqueado = (pokemonSeleccionado == null)
            }

            else -> {
                mostrarOpciones = false
                mostrarInputNombre = false
                mostrarVersion = false
                bloqueado = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoDesvanecido)
    ) {

        Image(
            painter = painterResource(id = R.drawable.encina),
            contentDescription = "Encina",
            modifier = Modifier
                .height(1100.dp)
                .fillMaxWidth(0.9f)
                .align(Alignment.CenterEnd)
                .offset(x = 100.dp, y = 300.dp)
        )

        if (mostrarVersion && indice == 1) {
            Row(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 160.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                ImagenEnRecuadro(
                    resId = R.drawable.blanco,
                    descripcionImagen = "Pokémon Blanco",
                    tamano = 150.dp,
                    modifier = Modifier.clickable {
                        audioManager.playSound(GameSound.CLICK)
                        versionJuego = VersionJuego.BLANCO.nombre
                        mostrarVersion = false

                        scope.launch {
                            visible = false
                            delay(300)
                            indice++
                            visible = true
                        }
                    }
                )

                ImagenEnRecuadro(
                    resId = R.drawable.negro,
                    descripcionImagen = "Pokémon Negro",
                    tamano = 150.dp,
                    modifier = Modifier.clickable {
                        audioManager.playSound(GameSound.CLICK)
                        versionJuego = VersionJuego.NEGRO.nombre
                        mostrarVersion = false

                        scope.launch {
                            visible = false
                            delay(300)
                            indice++
                            visible = true
                        }
                    }
                )
            }
        }

        if (mostrarOpciones && indice == 3) {
            Row(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 160.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                ImagenEnRecuadro(
                    resId = R.drawable.lucho,
                    descripcionImagen = "Chico",
                    tamano = 150.dp,
                    modifier = Modifier.clickable {
                        audioManager.playSound(GameSound.CLICK)
                        sexoJugador = "chico"
                        mostrarOpciones = false

                        scope.launch {
                            visible = false
                            delay(300)
                            indice++
                            visible = true
                        }
                    }
                )

                ImagenEnRecuadro(
                    resId = R.drawable.liza,
                    descripcionImagen = "Chica",
                    tamano = 150.dp,
                    modifier = Modifier.clickable {
                        audioManager.playSound(GameSound.CLICK)
                        sexoJugador = "chica"
                        mostrarOpciones = false

                        scope.launch {
                            visible = false
                            delay(300)
                            indice++
                            visible = true
                        }
                    }
                )
            }
        }

        if (mostrarInputNombre) {
            val focusRequester = FocusRequester()

            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }

            Card(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 160.dp)
                    .padding(12.dp)
                    .width(340.dp),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(2.dp, ColorRojoVibrante),
                colors = CardDefaults.cardColors(containerColor = ColorClaro)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "¿Cómo te llamas?", color = ColorOscuro)
                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = nombreJugador,
                        onValueChange = { nuevo ->
                            val filtrado = nuevo.replace("\n", "").take(12)
                            nombreJugador = filtrado
                        },
                        singleLine = true,
                        placeholder = { Text(text = "Introduce tu nombre") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        textStyle = TextStyle(textAlign = TextAlign.Center),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            if (nombreJugador.isNotBlank()) {
                                audioManager.playSound(GameSound.CLICK)
                                mostrarInputNombre = false

                                scope.launch {
                                    visible = false
                                    delay(300)
                                    indice++
                                    visible = true
                                }
                            }
                        })
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "${nombreJugador.length}/12", color = ColorOscuro)

                        val nombreValido = nombreJugador.isNotBlank()

                        Button(
                            onClick = {
                                if (!nombreValido) return@Button

                                audioManager.playSound(GameSound.CLICK)
                                mostrarInputNombre = false

                                scope.launch {
                                    visible = false
                                    delay(300)
                                    indice++
                                    visible = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (nombreValido) ColorRojoVibrante else ColorRojo.copy(alpha = 0.4f),
                                contentColor = ColorClaro
                            ),
                            enabled = nombreValido
                        ) {
                            Text(text = "Aceptar")
                        }
                    }
                }
            }
        }

        if (pokemonMostrados > 0) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 50.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(28.dp)
                ) {
                    if (pokemonMostrados >= 1) {
                        ImagenEnRecuadro(
                            resId = R.drawable.snivy,
                            descripcionImagen = "Snivy",
                            tamano = 160.dp,
                            colorBorde = ColorSnivy,
                            anchuraBorde = if (pokemonSeleccionado == PokemonInicial.SNIVY.nombre) 4.dp else 2.dp,
                            modifier = Modifier.clickable(enabled = (indice == 10)) {
                                audioManager.playSound(GameSound.CLICK)
                                pokemonSeleccionado = PokemonInicial.SNIVY.nombre
                                pokemonMostrados = 0

                                scope.launch {
                                    visible = false
                                    delay(300)
                                    indice++
                                    visible = true
                                }
                            }
                        )
                    }

                    if (pokemonMostrados >= 2) {
                        ImagenEnRecuadro(
                            resId = R.drawable.tepig,
                            descripcionImagen = "Tepig",
                            tamano = 160.dp,
                            colorBorde = ColorTepig,
                            anchuraBorde = if (pokemonSeleccionado == PokemonInicial.TEPIG.nombre) 4.dp else 2.dp,
                            modifier = Modifier.clickable(enabled = (indice == 10)) {
                                audioManager.playSound(GameSound.CLICK)
                                pokemonSeleccionado = PokemonInicial.TEPIG.nombre
                                pokemonMostrados = 0

                                scope.launch {
                                    visible = false
                                    delay(300)
                                    indice++
                                    visible = true
                                }
                            }
                        )
                    }
                }

                if (pokemonMostrados >= 3) {
                    ImagenEnRecuadro(
                        resId = R.drawable.oshawott,
                        descripcionImagen = "Oshawott",
                        tamano = 160.dp,
                        colorBorde = ColorOshawott,
                        anchuraBorde = if (pokemonSeleccionado == PokemonInicial.OSHAWOTT.nombre) 4.dp else 2.dp,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .clickable(enabled = (indice == 10)) {
                                audioManager.playSound(GameSound.CLICK)
                                pokemonSeleccionado = PokemonInicial.OSHAWOTT.nombre
                                pokemonMostrados = 0

                                scope.launch {
                                    visible = false
                                    delay(300)
                                    indice++
                                    visible = true
                                }
                            }
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-70).dp)
        ) {
            val textoDialogo = if (indice == 5) {
                val generoTexto = if (sexoJugador == "chico") "listo" else "lista"
                "¡Encantada de conocerte, $nombreJugador! Espero que estés $generoTexto para comenzar tu nueva aventura."
            } else {
                dialogos.getOrNull(indice) ?: ""
            }

            CuadroDialogoComponente(
                texto = textoDialogo,
                visible = visible,
                onClick = {
                    if (bloqueado) return@CuadroDialogoComponente

                    audioManager.playSound(GameSound.CLICK)

                    if (indice < dialogos.lastIndex) {
                        val siguiente = indice + 1

                        if (siguiente == 1 && versionJuego == null) {
                            mostrarVersion = true
                            bloqueado = true

                            scope.launch {
                                visible = false
                                delay(500)
                                indice = siguiente
                                visible = true
                            }
                            return@CuadroDialogoComponente
                        }

                        if (siguiente == 3 && sexoJugador == null) {
                            mostrarOpciones = true
                            bloqueado = true

                            scope.launch {
                                visible = false
                                delay(500)
                                indice = siguiente
                                visible = true
                            }
                            return@CuadroDialogoComponente
                        }

                        if (siguiente == 4) {
                            mostrarInputNombre = true
                            bloqueado = true

                            scope.launch {
                                visible = false
                                delay(500)
                                indice = siguiente
                                visible = true
                            }
                            return@CuadroDialogoComponente
                        }

                        if (siguiente == 7) {
                            pokemonSeleccionado = null
                            pokemonMostrados = 0

                            scope.launch {
                                visible = false
                                delay(500)
                                indice = siguiente
                                visible = true
                            }
                            return@CuadroDialogoComponente
                        }

                        scope.launch {
                            bloqueado = true
                            visible = false
                            delay(500)
                            indice = siguiente
                            visible = true
                            bloqueado = false
                        }
                    } else {
                        empezarRegistro()
                    }
                }
            )
        }

    }
}