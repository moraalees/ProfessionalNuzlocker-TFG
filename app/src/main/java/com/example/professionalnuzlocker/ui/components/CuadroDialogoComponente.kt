package com.example.professionalnuzlocker.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.professionalnuzlocker.ui.theme.ColorClaro
import com.example.professionalnuzlocker.ui.theme.ColorOscuro

/**
 * Cuadro de diálogo animado con entrada y salida en fade.
 *
 * Muestra [texto] en un recuadro redondeado con borde [ColorOscuro] y fondo [ColorClaro].
 * Es pulsable para disparar [onClick], útil para avanzar en diálogos narrativos o tutoriales.
 *
 * @param texto Contenido a mostrar dentro del cuadro.
 * @param visible Si es false el cuadro desaparece con fadeOut.
 * @param onClick Acción a ejecutar al pulsar el cuadro.
 */
@Composable
fun CuadroDialogoComponente(
    texto: String,
    visible: Boolean,
    onClick: () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(
                    color = ColorClaro,
                    shape = RoundedCornerShape(16.dp)
                )
                .border(2.dp, ColorOscuro, RoundedCornerShape(16.dp))
                .clickable { onClick() }
                .padding(16.dp)
        ) {
            Text(
                text = texto,
                color = ColorOscuro,
                fontSize = 18.sp
            )
        }
    }
}