package com.example.professionalnuzlocker.ui.screens.home

import androidx.compose.animation.AnimatedContent
import com.google.firebase.auth.EmailAuthProvider
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.example.professionalnuzlocker.R
import com.example.professionalnuzlocker.ui.theme.ColorClaro
import com.example.professionalnuzlocker.ui.theme.ColorGrisaceo
import com.example.professionalnuzlocker.ui.theme.ColorOscuro
import com.example.professionalnuzlocker.ui.theme.ColorOshawott
import com.example.professionalnuzlocker.ui.theme.ColorRojo
import com.example.professionalnuzlocker.ui.theme.ColorRojoVibrante
import com.example.professionalnuzlocker.ui.theme.ColorSnivy
import com.example.professionalnuzlocker.ui.theme.ColorTepig
import com.example.professionalnuzlocker.ui.utils.AudioManager
import com.example.professionalnuzlocker.ui.utils.GameSound

/**
 * Pantalla de inicio con carrusel de tres acciones: continuar partida, nueva partida y guía.
 *
 * Comprueba la existencia de una partida guardada en Firestore antes de navegar para
 * mostrar el diálogo de confirmación de sobreescritura si ya existe una. Incluye un
 * botón de pausa/reanudación de la música y un diálogo de gestión de cuenta
 * (cambio de contraseña / eliminar cuenta).
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PantallaInicio(
    continuarPartida: () -> Unit,
    mostrarGuia: () -> Unit,
    irFormulario: () -> Unit,
    cerrarSesion: () -> Unit,
    audioManager: AudioManager
){
    val fondoDesvanecido = Brush.verticalGradient(
        colors = listOf(ColorOscuro, ColorOscuro, ColorRojo)
    )

    val viewModel: PantallaInicioViewModel = viewModel()
    var mostrarDialogo by remember { mutableStateOf(false) }
    var accionActual by remember { mutableStateOf<String?>(null) }
    var mostrarDialogoSinPartida by remember { mutableStateOf(false) }
    var isPaused by remember { mutableStateOf(audioManager.isMusicPaused()) }
    var mostrarDialogoCuenta by remember { mutableStateOf(false) }
    var cuentaPassword by remember { mutableStateOf("") }
    var cuentaPasswordActual by remember { mutableStateOf("") }
    var cuentaConfirmarPassword by remember { mutableStateOf("") }
    var cuentaMensajeError by remember { mutableStateOf("") }
    var cuentaMensajeExito by remember { mutableStateOf("") }


    LaunchedEffect(viewModel.hayPartida) {
        viewModel.hayPartida?.let { existe ->

            when (accionActual) {

                "CONTINUAR" -> {
                    if (existe) {
                        continuarPartida()
                    } else {
                        mostrarDialogoSinPartida = true
                    }
                }

                "NUEVA" -> {
                    if (existe) {
                        mostrarDialogo = true
                    } else {
                        irFormulario()
                    }
                }
            }

            accionActual = null
            viewModel.limpiarEstado()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoDesvanecido)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo de Professional Nuzlocker",
                modifier = Modifier.size(270.dp)
            )
            Box(
                modifier = Modifier
                    .height(490.dp)
                    .width(320.dp),
                contentAlignment = Alignment.Center
            ) {
                //Esta Box es para hacer que los bordes no solapen a los botones, poniendo ambos elementos al mismo nivel
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                        .border(2.dp, ColorRojoVibrante)
                )

                var currentIndex by remember { mutableIntStateOf(0) }
                var lastDirection by remember { mutableIntStateOf(1) }

                AnimatedContent(
                    targetState = currentIndex,
                    transitionSpec = {
                        val direction = lastDirection
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> direction * fullWidth },
                            animationSpec = tween(durationMillis = 400)
                        ) + fadeIn(animationSpec = tween(400)) togetherWith
                                slideOutHorizontally(
                                    targetOffsetX = { fullWidth -> -direction * fullWidth },
                                    animationSpec = tween(durationMillis = 400)
                                ) + fadeOut(animationSpec = tween(400))
                    }
                ) { target ->
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        when (target) {
                            0 -> {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Top
                                ) {
                                    Text(
                                        text = "CONTINUAR",
                                        style = TextStyle(
                                            fontWeight = FontWeight.Black,
                                            letterSpacing = (1.5).sp,
                                            fontSize = 33.sp,
                                            lineHeight = 10.sp
                                        ),
                                        modifier = Modifier.padding(top = 40.dp),
                                        color = ColorSnivy
                                    )
                                    Spacer(Modifier.height(20.dp))
                                    Text(
                                        text = "Sigue disfrutando de la región de Teselia con todos tus Pokémon desde donde lo dejaste.",
                                        color = Color.White,
                                        fontSize = 19.sp,
                                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 7.dp),
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(Modifier.weight(1f))
                                    Image(
                                        painter = painterResource(id = R.drawable.snivy),
                                        contentDescription = "Imagen de Snivy",
                                        modifier = Modifier.size(165.dp)
                                    )
                                    HorizontalDivider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 50.dp),
                                        color = Color.White,
                                    )
                                    Spacer(Modifier.height(14.dp))
                                    Button(
                                        onClick = {
                                            audioManager.playSound(GameSound.CLICK)
                                            accionActual = "CONTINUAR"
                                            viewModel.comprobarPartida()
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = ColorSnivy)
                                    ) {
                                        Text("¡Adelante!")
                                    }
                                    Spacer(Modifier.height(18.dp))
                                }
                            }
                            1 -> {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Top
                                ) {
                                    Text(
                                        text = "NUEVA PARTIDA",
                                        style = TextStyle(
                                            fontWeight = FontWeight.Black,
                                            letterSpacing = (1.5).sp,
                                            fontSize = 33.sp,
                                            lineHeight = 10.sp
                                        ),
                                        modifier = Modifier.padding(top = 40.dp),
                                        color = ColorTepig
                                    )
                                    Spacer(Modifier.height(20.dp))
                                    Text(
                                        text = "Comienza una nueva aventura en la región de Teselia esta vez con distintos Pokémon.",
                                        color = Color.White,
                                        fontSize = 19.sp,
                                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 7.dp),
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(Modifier.weight(1f))
                                    Image(
                                        painter = painterResource(id = R.drawable.tepig),
                                        contentDescription = "Imagen de Tepig",
                                        modifier = Modifier.size(165.dp)
                                    )
                                    HorizontalDivider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 50.dp),
                                        color = Color.White,
                                    )
                                    Spacer(Modifier.height(14.dp))
                                    Button(
                                        onClick = {
                                            audioManager.playSound(GameSound.CLICK)
                                            accionActual = "NUEVA"
                                            viewModel.comprobarPartida()
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = ColorTepig)
                                    ) {
                                        Text("¡Adelante!")
                                    }
                                    Spacer(Modifier.height(18.dp))
                                }
                            }
                            2 -> {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Top
                                ) {
                                    Text(
                                        text = "GUÍA NUZLOCKE",
                                        style = TextStyle(
                                            fontWeight = FontWeight.Black,
                                            letterSpacing = (1.5).sp,
                                            fontSize = 33.sp,
                                            lineHeight = 10.sp
                                        ),
                                        modifier = Modifier.padding(top = 40.dp),
                                        color = ColorOshawott
                                    )
                                    Spacer(Modifier.height(20.dp))
                                    Text(
                                        text = "Consulta una guía detallada con las reglas más importantes de un Nuzlocke en Teselia.",
                                        color = Color.White,
                                        fontSize = 19.sp,
                                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 7.dp),
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(Modifier.weight(1f))
                                    Image(
                                        painter = painterResource(id = R.drawable.oshawott),
                                        contentDescription = "Imagen de Oshawott",
                                        modifier = Modifier.size(165.dp)
                                    )
                                    HorizontalDivider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 50.dp),
                                        color = Color.White,
                                    )
                                    Spacer(Modifier.height(14.dp))
                                    Button(
                                        onClick = { audioManager.playSound(GameSound.CLICK); mostrarGuia() },
                                        colors = ButtonDefaults.buttonColors(containerColor = ColorOshawott)
                                    ) {
                                        Text("¡Adelante!")
                                    }
                                    Spacer(Modifier.height(18.dp))
                                }
                            }
                        }
                    }
                }

                Button(
                    onClick = {
                        lastDirection = -1
                        currentIndex = (currentIndex - 1).floorMod(3)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2ECC71)),
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(50.dp)
                        .offset(x = (-25).dp)
                        .zIndex(2f),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(text = "<", color = ColorOscuro, fontSize = 26.sp)
                }

                Button(
                    onClick = {
                        lastDirection = 1
                        currentIndex = (currentIndex + 1) % 3
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2ECC71)),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(50.dp)
                        .offset(x = 25.dp)
                        .zIndex(2f),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(text = ">", color = ColorOscuro, fontSize = 26.sp)
                }
            }
        }

        IconButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 4.dp, start = 4.dp),
            onClick = {
                cuentaPassword = ""
                cuentaConfirmarPassword = ""
                cuentaMensajeError = ""
                cuentaMensajeExito = ""
                mostrarDialogoCuenta = true
            }
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Mi cuenta",
                tint = ColorClaro.copy(alpha = 0.5f),
                modifier = Modifier.size(24.dp)
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = {
                    audioManager.toggleAmbientMusic()
                    isPaused = audioManager.isMusicPaused()
                }
            ) {
                Icon(
                    imageVector = if (isPaused)
                        Icons.AutoMirrored.Filled.VolumeOff
                    else
                        Icons.AutoMirrored.Filled.VolumeUp,
                    contentDescription = "Audio",
                    tint = ColorClaro.copy(alpha = 0.5f),
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = cerrarSesion
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "Cerrar sesión",
                    tint = ColorClaro.copy(alpha = 0.5f),
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        if (mostrarDialogoCuenta) {

            val user = FirebaseAuth.getInstance().currentUser

            Dialog(
                onDismissRequest = { mostrarDialogoCuenta = false }
            ) {

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = ColorGrisaceo,
                    border = BorderStroke(
                        1.dp,
                        ColorRojoVibrante.copy(alpha = 0.5f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        Text(
                            text = "Mi cuenta",
                            color = ColorClaro,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                        // EMAIL
                        OutlinedTextField(
                            value = user?.email ?: "",
                            onValueChange = {},
                            label = { Text("Correo electrónico", fontSize = 12.sp) },
                            enabled = false,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledTextColor = ColorClaro.copy(alpha = 0.6f),
                                disabledBorderColor = ColorClaro.copy(alpha = 0.2f),
                                disabledLabelColor = ColorClaro.copy(alpha = 0.35f),
                                disabledContainerColor = Color.Transparent
                            )
                        )

                        HorizontalDivider(color = ColorClaro.copy(alpha = 0.1f))

                        Text(
                            text = "Cambiar contraseña",
                            color = ColorClaro.copy(alpha = 0.55f),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        // CONTRASEÑA ACTUAL
                        OutlinedTextField(
                            value = cuentaPasswordActual,
                            onValueChange = {
                                cuentaPasswordActual = it
                                cuentaMensajeError = ""
                                cuentaMensajeExito = ""
                            },
                            label = { Text("Contraseña actual", fontSize = 12.sp) },
                            visualTransformation = PasswordVisualTransformation(),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
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

                        // NUEVA CONTRASEÑA
                        OutlinedTextField(
                            value = cuentaPassword,
                            onValueChange = {
                                cuentaPassword = it
                                cuentaMensajeError = ""
                                cuentaMensajeExito = ""
                            },
                            label = { Text("Nueva contraseña", fontSize = 12.sp) },
                            visualTransformation = PasswordVisualTransformation(),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
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

                        // CONFIRMAR CONTRASEÑA
                        OutlinedTextField(
                            value = cuentaConfirmarPassword,
                            onValueChange = {
                                cuentaConfirmarPassword = it
                                cuentaMensajeError = ""
                                cuentaMensajeExito = ""
                            },
                            label = { Text("Confirmar contraseña", fontSize = 12.sp) },
                            visualTransformation = PasswordVisualTransformation(),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
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

                        // MENSAJES
                        if (cuentaMensajeError.isNotBlank()) {
                            Text(cuentaMensajeError, color = ColorRojo, fontSize = 12.sp)
                        }

                        if (cuentaMensajeExito.isNotBlank()) {
                            Text(cuentaMensajeExito, color = Color(0xFF4CAF50), fontSize = 12.sp)
                        }

                        // BOTONES
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            OutlinedButton(
                                onClick = { mostrarDialogoCuenta = false },
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

                                    when {

                                        cuentaPasswordActual.isBlank() ->
                                            cuentaMensajeError = "Introduce tu contraseña actual"

                                        cuentaPassword.length < 6 ->
                                            cuentaMensajeError = "Mínimo 6 caracteres"

                                        cuentaPassword != cuentaConfirmarPassword ->
                                            cuentaMensajeError = "Las contraseñas no coinciden"

                                        else -> {

                                            val credential = EmailAuthProvider.getCredential(
                                                user?.email ?: "",
                                                cuentaPasswordActual
                                            )

                                            user?.reauthenticate(credential)
                                                ?.addOnSuccessListener {

                                                    user.updatePassword(cuentaPassword)
                                                        .addOnSuccessListener {

                                                            cuentaMensajeExito = "Contraseña actualizada"

                                                            cuentaPasswordActual = ""
                                                            cuentaPassword = ""
                                                            cuentaConfirmarPassword = ""

                                                        }
                                                        .addOnFailureListener {

                                                            cuentaMensajeError =
                                                                "No se pudo actualizar la contraseña"
                                                        }

                                                }
                                                ?.addOnFailureListener {

                                                    cuentaMensajeError =
                                                        "La contraseña actual es incorrecta"
                                                }
                                        }
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = ColorRojoVibrante
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "Guardar",
                                    color = ColorClaro,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }

        if (mostrarDialogo) {
            AlertDialog(
                onDismissRequest = { mostrarDialogo = false },
                containerColor = ColorGrisaceo,
                shape = RoundedCornerShape(16.dp),
                tonalElevation = 8.dp,
                title = {
                    Text(
                        text = "Partida existente",
                        color = ColorClaro,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                text = {
                    Text(
                        text = "Ya hay una partida guardada. ¿Quieres borrarla y empezar una nueva?",
                        color = ColorClaro.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            mostrarDialogo = false
                            viewModel.borrarPartidas {
                                irFormulario()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ColorRojoVibrante,
                            contentColor = ColorClaro
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Sí")
                    }
                },
                dismissButton = {
                    OutlinedButton(
                        onClick = {
                            mostrarDialogo = false
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = ColorClaro
                        ),
                        border = BorderStroke(1.5.dp, ColorRojoVibrante),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(text = "No")
                    }
                },
                modifier = Modifier
                    .border(
                        BorderStroke(2.dp, ColorRojoVibrante),
                        RoundedCornerShape(16.dp)
                    )
            )
        }

        if (mostrarDialogoSinPartida) {
            AlertDialog(
                onDismissRequest = { mostrarDialogoSinPartida = false },
                containerColor = ColorGrisaceo,
                shape = RoundedCornerShape(16.dp),
                title = {
                    Text(
                        text = "Sin partida",
                        color = ColorClaro
                    )
                },
                text = {
                    Text(
                        text = "No tienes ninguna partida guardada. Tendrás que crear una nueva.",
                        color = ColorClaro.copy(alpha = 0.8f)
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            mostrarDialogoSinPartida = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ColorRojoVibrante
                        )
                    ) {
                        Text("Aceptar", color = ColorClaro)
                    }
                },
                modifier = Modifier.border(
                    BorderStroke(2.dp, ColorRojoVibrante),
                    RoundedCornerShape(16.dp)
                )
            )
        }
    }
}

/** Módulo siempre positivo, necesario para el cálculo de dirección del carrusel con índices negativos. */
private fun Int.floorMod(other: Int): Int {
    val r = this % other
    return if (r < 0) r + other else r
}
