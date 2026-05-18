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
