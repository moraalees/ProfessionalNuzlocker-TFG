package com.example.professionalnuzlocker.ui.screens.chat

import android.view.WindowManager
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.professionalnuzlocker.ui.theme.ColorClaro
import com.example.professionalnuzlocker.ui.theme.ColorGrisaceo
import com.example.professionalnuzlocker.ui.theme.ColorOscuro
import com.example.professionalnuzlocker.ui.theme.ColorRojo
import com.example.professionalnuzlocker.ui.theme.ColorRojoVibrante

@Composable
fun PantallaChat() {
    val viewModel: PantallaChatViewModel = viewModel()
    var inputTexto by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val fondo = Brush.verticalGradient(listOf(ColorOscuro, ColorOscuro, ColorRojo))
    val ventanaMovil = (LocalContext.current as? android.app.Activity)?.window

    DisposableEffect(Unit) {
        ventanaMovil?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        onDispose {
            ventanaMovil?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        }
    }

    LaunchedEffect(viewModel.mensajes.size, viewModel.cargando) {
        val total = viewModel.mensajes.size + if (viewModel.cargando) 1 else 0
        if (total > 0) listState.animateScrollToItem(total - 1)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .imePadding()
    ) {
        when {
            viewModel.cargandoPartida -> {
                CircularProgressIndicator(
                    color = ColorRojo,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            viewModel.partidaId == null -> {
                Text(
                    text = "No hay ninguna partida activa.",
                    color = ColorClaro.copy(alpha = 0.45f),
                    fontSize = 15.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)
                    ) {
                        Text(
                            text = "NUZBOT",
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.5.sp,
                            fontSize = 32.sp,
                            color = ColorRojo
                        )
                        Text(
                            text = "Asistente IA para tu Nuzlocke",
                            color = ColorClaro.copy(alpha = 0.45f),
                            fontSize = 13.sp
                        )
                    }

                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        if (viewModel.mensajes.isEmpty()) {
                            item {
                                BurbujaMensajeBot("¡Hola! Soy NuzBot. Pregúntame lo que quieras sobre tu partida Nuzlocke.")
                            }
                        }
                        items(viewModel.mensajes) { msg ->
                            if (msg.esUsuario) {
                                BurbujaMensajeUsuario(msg.texto)
                            } else {
                                BurbujaMensajeBot(msg.texto)
                            }
                        }
                        if (viewModel.cargando) {
                            item { NuzBotPensando() }
                        }
                    }

                    viewModel.error?.let {
                        Text(
                            text = it,
                            color = ColorRojo,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(ColorGrisaceo)
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = inputTexto,
                            onValueChange = { inputTexto = it },
                            modifier = Modifier.weight(1f),
                            placeholder = {
                                Text(
                                    text = "Pregunta sobre tu partida...",
                                    color = ColorClaro.copy(alpha = 0.35f),
                                    fontSize = 14.sp
                                )
                            },
                            maxLines = 3,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = ColorClaro,
                                unfocusedTextColor = ColorClaro,
                                focusedBorderColor = ColorRojoVibrante,
                                unfocusedBorderColor = ColorClaro.copy(alpha = 0.2f),
                                cursorColor = ColorRojoVibrante,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            )
                        )
                        Button(
                            onClick = {
                                val texto = inputTexto.trim()
                                if (texto.isNotBlank()) {
                                    viewModel.enviarMensaje(texto)
                                    inputTexto = ""
                                }
                            },
                            enabled = !viewModel.cargando && inputTexto.isNotBlank(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ColorRojoVibrante,
                                disabledContainerColor = ColorRojo.copy(alpha = 0.4f)
                            ),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
                        ) {
                            Text(
                                text = "Enviar",
                                color = ColorClaro,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BurbujaMensajeUsuario(texto: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Surface(
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 4.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
            color = ColorRojoVibrante.copy(alpha = 0.85f),
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Text(
                text = texto,
                color = ColorClaro,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
            )
        }
    }
}

@Composable
private fun BurbujaMensajeBot(texto: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(ColorRojo),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "N",
                color = ColorClaro,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp)
        }
        Spacer(Modifier.width(8.dp))
        Surface(
            shape = RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
            color = ColorGrisaceo,
            border = BorderStroke(1.dp, ColorClaro.copy(alpha = 0.08f)),
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Text(
                text = texto,
                color = ColorClaro,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
            )
        }
    }
}

@Composable
private fun NuzBotPensando() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(ColorRojo),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "N",
                color = ColorClaro,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
        Spacer(Modifier.width(8.dp))
        Surface(
            shape = RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
            color = ColorGrisaceo,
            border = BorderStroke(1.dp, ColorClaro.copy(alpha = 0.08f))
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CircularProgressIndicator(
                    color = ColorRojo,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = "Conectando con la IA...",
                    color = ColorClaro.copy(alpha = 0.45f),
                    fontSize = 13.sp,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}
