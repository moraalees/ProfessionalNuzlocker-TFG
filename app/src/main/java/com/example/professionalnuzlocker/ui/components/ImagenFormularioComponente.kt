package com.example.professionalnuzlocker.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.professionalnuzlocker.ui.theme.ColorRojo

/**
 * Imagen centrada sobre fondo negro dentro de un recuadro con bordes redondeados.
 *
 * La imagen ocupa el 85% del área interior. Útil para mostrar sprites de Pokémon,
 * versiones del juego u otras imágenes en el formulario de nueva partida.
 *
 * @param resId DrawableRes de la imagen a mostrar.
 * @param descripcionImagen Descripción para accesibilidad (contentDescription).
 * @param tamano Tamaño total del recuadro (por defecto 150dp).
 * @param colorBorde Color del borde (por defecto [ColorRojo]).
 * @param anchuraBorde Grosor del borde (por defecto 2dp).
 * @param cornerRadius Radio de las esquinas (por defecto 12dp).
 */
@Composable
fun ImagenEnRecuadro(
    resId: Int,
    descripcionImagen: String?,
    modifier: Modifier = Modifier,
    tamano: Dp = 150.dp,
    colorBorde: Color = ColorRojo,
    anchuraBorde: Dp = 2.dp,
    cornerRadius: Dp = 12.dp
) {
    val shape = RoundedCornerShape(cornerRadius)

    Box(
        modifier = modifier
            .size(tamano)
            .background(color = Color.Black, shape = shape)
            .border(anchuraBorde, colorBorde, shape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = descripcionImagen,
            modifier = Modifier
                .fillMaxSize(0.85f)
        )
    }
}
