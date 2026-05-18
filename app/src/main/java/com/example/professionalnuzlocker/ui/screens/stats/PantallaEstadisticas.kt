package com.example.professionalnuzlocker.ui.screens.stats

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.professionalnuzlocker.R
import com.example.professionalnuzlocker.data.model.enum_classes.TipoPokemon
import com.example.professionalnuzlocker.ui.components.CuadroDialogoComponente
import com.example.professionalnuzlocker.ui.theme.ColorBronce
import com.example.professionalnuzlocker.ui.theme.ColorClaro
import com.example.professionalnuzlocker.ui.theme.ColorOro
import com.example.professionalnuzlocker.ui.theme.ColorOscuro
import com.example.professionalnuzlocker.ui.theme.ColorPlata
import com.example.professionalnuzlocker.ui.theme.ColorRojo
import com.example.professionalnuzlocker.ui.theme.ColorRojoVibrante
import kotlin.math.roundToInt
import com.example.professionalnuzlocker.ui.utils.AudioManager
import com.example.professionalnuzlocker.ui.utils.GameSound
import com.example.professionalnuzlocker.ui.utils.generarPDF



@Composable
fun PantallaEstadisticas(
    viewModel: PantallaEstadisticasViewModel = viewModel(),
    audioManager: AudioManager? = null
) {
    val gradient = Brush.verticalGradient(colors = listOf(ColorOscuro, ColorOscuro, ColorRojo))
    var fase by remember { mutableIntStateOf(0) }
    var dialogo by remember { mutableIntStateOf(0) }
    val datos = viewModel.datos

    fun avanzar() {
        audioManager?.playSound(GameSound.CLICK)
        when {
            fase == 0 -> { fase = 1; dialogo = 0 }
            fase in 1..6 && dialogo == 0 -> dialogo = 1
            fase in 1..6 && dialogo == 1 -> { fase++; dialogo = 0 }
            fase == 7 && dialogo == 0 -> dialogo = 1
            fase == 7 && dialogo == 1 -> fase = 8
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        AnimatedVisibility(
            visible = fase == 0,
            exit = slideOutHorizontally(animationSpec = tween(600)) { it * 2 }
        ) {
            Image(
                painter = painterResource(id = R.drawable.encina),
                contentDescription = null,
                modifier = Modifier
                    .height(1100.dp)
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterEnd)
                    .offset(x = 100.dp, y = 300.dp)
            )
        }
        if (!viewModel.cargando && datos != null && fase in 1..7) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 170.dp, top = 16.dp)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                when (fase) {
                    1 -> SeccionResultado(datos)
                    2 -> SeccionCapturas(datos)
                    3 -> SeccionTopUsados(datos)
                    4 -> SeccionTopMortales(datos)
                    5 -> SeccionTopAsesinos(datos)
                    6 -> SeccionTipos(datos)
                    7 -> SeccionConsultasIA(datos)
                }
            }
        }

        if (!viewModel.cargando && datos != null && fase == 8) {
            ResumenCompleto(
                datos = datos,
                audioManager = audioManager,
                onDescargar = { context ->
                    generarPDF(context, datos)
                }
            )
        }

        if (viewModel.cargando) {
            CircularProgressIndicator(color = ColorRojo, modifier = Modifier.align(Alignment.Center))
        }

        if (fase < 8 && !viewModel.cargando) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
            ) {
                CuadroDialogoComponente(
                    texto = textoDialogo(fase, dialogo, datos),
                    visible = true,
                    onClick = { avanzar() }
                )
            }
        }
    }
}

private fun textoDialogo(fase: Int, dialogo: Int, datos: EstadisticasData?): String {
    if (datos == null) return "Cargando datos de tu aventura..."
    return when (fase) {
        0 -> "Bien... ha llegado el momento de hacer un repaso de tu aventura. Veamos cómo te ha ido."
        1 -> if (dialogo == 0)
            "Antes de nada, lo más importante: ¿cómo terminó tu aventura?"
        else if (datos.esVictoria)
            "¡Lo lograste, entrenador! Completaste el Locke con ${datos.vidas} ${if (datos.vidas == 1) "vida" else "vidas"} de sobra. ¡Una hazaña épica!"
        else if (datos.vidas < 0)
            "Tu aventura llegó a su fin. Te quedaste sin vidas disponibles... Una lucha dura y larga hasta el final."
        else
            "Tu aventura llegó a su fin. Un combate te superó. Pero lo diste todo, y eso merece respeto."
        2 -> if (dialogo == 0)
            "Veamos ahora tu rendimiento en los encuentros de las rutas."
        else {
            val pctCap = if (datos.totalRutas > 0) (datos.capturadas * 100f / datos.totalRutas).roundToInt() else 0
            val pctPer = if (datos.totalRutas > 0) (datos.perdidas * 100f / datos.totalRutas).roundToInt() else 0
            "Capturaste el $pctCap% de los Pokémon disponibles y perdiste el $pctPer% de los encuentros."
        }
        3 -> if (dialogo == 0)
            "¿Quiénes fueron tus Pokémon más fieles en los combates importantes?"
        else
            "Estos luchadores te acompañaron en los momentos más decisivos de tu aventura."
        4 -> if (dialogo == 0)
            "Veamos los combates que más se cobraron de los tuyos..."
        else if (datos.topCombatesMortales.isEmpty())
            "¡Sorprendente! Ningún combate importante te costó ningún Pokémon."
        else
            "Estos fueron los encuentros más duros y costosos para tu equipo."
        5 -> if (dialogo == 0)
            "Y los rivales que dejaron más huella en tu aventura..."
        else if (datos.topAsesinos.isEmpty())
            "¡Increíble! Ningún Pokémon rival pudo acabar con los tuyos."
        else
            "Estos Pokémon rivales fueron los más letales contra tu equipo."
        6 -> if (dialogo == 0)
            "Veamos la distribución de tipos de los Pokémon que capturaste."
        else
            "Una mezcla interesante. Cada entrenador tiene su propio estilo de equipo."
        7 -> if (dialogo == 0)
            "Y por último... ¿cuántas veces recurriste a NuzBot, el asistente IA, durante tu aventura?"
        else when {
            datos.consultasIA == 0 -> "¡Impresionante! No necesitaste consejo externo en ningún momento. Confiaste en tu instinto durante toda la aventura."
            datos.consultasIA == 1 -> "Pediste consejo a NuzBot exactamente 1 vez. La usaste en el momento preciso."
            datos.consultasIA < 10 -> "Pediste consejo a NuzBot ${datos.consultasIA} veces. Un uso moderado y estratégico de la IA."
            else -> "¡${datos.consultasIA} consultas a NuzBot! La IA fue tu aliada más fiel durante esta aventura."
        }
        else -> ""
    }
}

@Composable
private fun SeccionResultado(datos: EstadisticasData) {
    val color = if (datos.esVictoria) ColorOro else ColorRojo
    val titulo = if (datos.esVictoria) "VICTORIA" else "DERROTA"
    val vidasMostradas = datos.vidas.coerceIn(0, 10)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(28.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color.copy(alpha = 0.12f), RoundedCornerShape(20.dp))
                .border(2.dp, color.copy(alpha = 0.7f), RoundedCornerShape(20.dp))
                .padding(vertical = 28.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = titulo,
                color = color,
                fontSize = 52.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 5.sp
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "VIDAS RESTANTES",
                color = ColorClaro.copy(alpha = 0.45f),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                repeat(10) { i ->
                    val relleno = i < vidasMostradas
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(
                                if (relleno) color.copy(alpha = 0.85f) else ColorClaro.copy(alpha = 0.1f),
                                CircleShape
                            )
                            .border(1.dp, if (relleno) color else ColorClaro.copy(alpha = 0.2f), CircleShape)
                    )
                }
            }
            Text(
                text = if (datos.vidas < 0) "Sin vidas" else "${datos.vidas} / 10",
                color = color,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun SeccionCapturas(datos: EstadisticasData) {
    val pctCap = if (datos.totalRutas > 0) datos.capturadas * 100f / datos.totalRutas else 0f
    val pctPer = if (datos.totalRutas > 0) datos.perdidas * 100f / datos.totalRutas else 0f

    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "CAPTURAS EN RUTA",
            color = ColorClaro.copy(alpha = 0.45f),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        BarraPorcentaje(
            label = "Capturados",
            valor = datos.capturadas,
            total = datos.totalRutas,
            porcentaje = pctCap,
            color = Color(0xFF4CAF50)
        )
        BarraPorcentaje(
            label = "Perdidos",
            valor = datos.perdidas,
            total = datos.totalRutas,
            porcentaje = pctPer,
            color = ColorRojo
        )
        Text(
            text = "${datos.totalRutas} rutas disponibles en este juego",
            color = ColorClaro.copy(alpha = 0.3f),
            fontSize = 11.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun BarraPorcentaje(label: String, valor: Int, total: Int, porcentaje: Float, color: Color) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, color = ColorClaro, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Text(
                "$valor / $total  (${porcentaje.roundToInt()}%)",
                color = color,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .background(ColorClaro.copy(alpha = 0.08f), RoundedCornerShape(6.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = (porcentaje / 100f).coerceIn(0f, 1f))
                    .fillMaxHeight()
                    .background(color, RoundedCornerShape(6.dp))
            )
        }
    }
}


@Composable
private fun SeccionTopUsados(datos: EstadisticasData) {
    val lista = datos.topUsados

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "MÁS USADOS EN COMBATE",
            color = ColorClaro.copy(alpha = 0.45f),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        if (lista.isEmpty()) {
            TextoVacio("No hay datos de combates registrados.")
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                lista.getOrNull(0)?.let {
                    TarjetaUsado(
                        it,
                        1,
                        modifier = Modifier.fillMaxWidth(0.7f)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    lista.getOrNull(1)?.let { TarjetaUsado(it, 2, Modifier.weight(1f)) }
                    lista.getOrNull(2)?.let { TarjetaUsado(it, 3, Modifier.weight(1f)) }
                    if (lista.size == 2) Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun TarjetaUsado(entrada: EntradaUsado, rang: Int, modifier: Modifier = Modifier) {
    val borderColor = when (rang) { 1 -> ColorOro; 2 -> ColorPlata; else -> ColorBronce }
    val esPrimero = rang == 1
    Box(
        modifier = modifier
            .background(borderColor.copy(alpha = 0.08f), RoundedCornerShape(16.dp))
            .border(if (esPrimero) 2.dp else 1.5.dp, borderColor.copy(alpha = 0.7f), RoundedCornerShape(16.dp))
            .padding(if (esPrimero) 16.dp else 12.dp)
    ) {
        if (esPrimero) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                Image(painterResource(entrada.especie.imagen), null, Modifier.size(80.dp))
                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text("#1", color = borderColor, fontSize = 10.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                    Text(
                        entrada.pokemon.mote?.takeIf { it.isNotBlank() } ?: entrada.especie.nombre,
                        color = ColorClaro, fontSize = 17.sp, fontWeight = FontWeight.Bold
                    )
                    if (!entrada.pokemon.mote.isNullOrBlank() && entrada.pokemon.mote != entrada.especie.nombre) {
                        Text(entrada.especie.nombre, color = ColorClaro.copy(alpha = 0.5f), fontSize = 11.sp)
                    }
                    Spacer(Modifier.height(4.dp))
                    Text("${entrada.veces} combates", color = borderColor, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text("#$rang", color = borderColor, fontSize = 9.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                Image(painterResource(entrada.especie.imagen), null, Modifier.size(54.dp))
                Text(
                    entrada.pokemon.mote?.takeIf { it.isNotBlank() } ?: entrada.especie.nombre,
                    color = ColorClaro, fontSize = 12.sp, fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center, maxLines = 1, overflow = TextOverflow.Ellipsis
                )
                Text("${entrada.veces} combates", color = borderColor, fontSize = 11.sp)
            }
        }
    }
}



@Composable
private fun SeccionTopMortales(datos: EstadisticasData) {
    val lista = datos.topCombatesMortales

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "COMBATES MÁS MORTALES",
            color = ColorClaro.copy(alpha = 0.45f),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        if (lista.isEmpty()) {
            TextoVacio("No hubo ningún combate donde perdieses Pokémon.")
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                lista.getOrNull(0)?.let {
                    TarjetaMortal(it, 1, Modifier.fillMaxWidth(0.7f))
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    lista.getOrNull(1)?.let { TarjetaMortal(it, 2, Modifier.weight(1f)) }
                    lista.getOrNull(2)?.let { TarjetaMortal(it, 3, Modifier.weight(1f)) }
                    if (lista.size == 2) Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun TarjetaMortal(
    entrada: EntradaMortal,
    rang: Int,
    modifier: Modifier = Modifier
) {
    val borderColor = when (rang) {
        1 -> ColorOro
        2 -> ColorPlata
        else -> ColorBronce
    }

    val esPrimero = rang == 1

    Box(
        modifier = modifier
            .background(borderColor.copy(alpha = 0.08f), RoundedCornerShape(16.dp))
            .border(
                if (esPrimero) 2.dp else 1.5.dp,
                borderColor.copy(alpha = 0.7f),
                RoundedCornerShape(16.dp)
            )
            .padding(if (esPrimero) 16.dp else 12.dp)
    ) {
        if (esPrimero) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                ImagenEntrenador(entrada, borderColor, 72.dp)

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("#1", color = borderColor, fontSize = 10.sp, fontWeight = FontWeight.Black)
                    Text(entrada.combate.nombreRival, color = ColorClaro, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                    Text(entrada.combate.lugar, color = ColorClaro.copy(alpha = 0.5f), fontSize = 12.sp)
                    Text("${entrada.muertes} Pokémon caídos", color = borderColor, fontSize = 14.sp)
                }
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text("#$rang", color = borderColor, fontSize = 9.sp, fontWeight = FontWeight.Black)

                ImagenEntrenador(entrada, borderColor, 56.dp)

                Text(
                    entrada.combate.nombreRival,
                    color = ColorClaro,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    "${entrada.muertes} caídos",
                    color = borderColor,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
private fun ImagenEntrenador(
    entrada: EntradaMortal,
    borderColor: Color,
    size: Dp
) {
    Image(
        painter = painterResource(entrada.combate.imagenRival),
        contentDescription = null,
        modifier = Modifier
            .size(size)
            .background(ColorClaro.copy(0.1f), CircleShape)
            .border(2.dp, borderColor, CircleShape)
    )
}



@Composable
private fun SeccionTopAsesinos(datos: EstadisticasData) {
    val lista = datos.topAsesinos

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "RIVALES MÁS LETALES",
            color = ColorClaro.copy(alpha = 0.45f),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        if (lista.isEmpty()) {
            TextoVacio("Ningún Pokémon rival pudo acabar con los tuyos.")
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                lista.getOrNull(0)?.let {
                    TarjetaAsesino(it, 1, Modifier.fillMaxWidth(0.7f))
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    lista.getOrNull(1)?.let { TarjetaAsesino(it, 2, Modifier.weight(1f)) }
                    lista.getOrNull(2)?.let { TarjetaAsesino(it, 3, Modifier.weight(1f)) }
                    if (lista.size == 2) Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun TarjetaAsesino(entrada: EntradaAsesino, rang: Int, modifier: Modifier = Modifier) {
    val borderColor = when (rang) { 1 -> ColorOro; 2 -> ColorPlata; else -> ColorBronce }
    val esPrimero = rang == 1
    Box(
        modifier = modifier
            .background(borderColor.copy(alpha = 0.08f), RoundedCornerShape(16.dp))
            .border(if (esPrimero) 2.dp else 1.5.dp, borderColor.copy(alpha = 0.7f), RoundedCornerShape(16.dp))
            .padding(if (esPrimero) 16.dp else 12.dp)
    ) {
        if (esPrimero) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                Image(painterResource(entrada.especie.imagen), null, Modifier.size(80.dp))
                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text("#1", color = borderColor, fontSize = 10.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                    Text(entrada.especie.nombre, color = ColorClaro, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(2.dp))
                    Text("${entrada.victimas} víctimas", color = borderColor, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    if (entrada.ataques.isNotEmpty()) {
                        Text(
                            "Con: ${entrada.ataques.joinToString(", ")}",
                            color = ColorClaro.copy(alpha = 0.55f),
                            fontSize = 11.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text("#$rang", color = borderColor, fontSize = 9.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                Image(painterResource(entrada.especie.imagen), null, Modifier.size(54.dp))
                Text(
                    entrada.especie.nombre,
                    color = ColorClaro,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text("${entrada.victimas} víctimas", color = borderColor, fontSize = 11.sp)
                if (entrada.ataques.isNotEmpty()) {
                    Text(
                        entrada.ataques.take(2).joinToString(", "),
                        color = ColorClaro.copy(alpha = 0.5f),
                        fontSize = 9.sp,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}


@Composable
private fun SeccionTipos(datos: EstadisticasData) {

    val dist = datos.distribucionTipos

    val total = dist.values.sum()

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "DISTRIBUCIÓN DE TIPOS",
            color = ColorClaro.copy(alpha = 0.45f),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
        )

        if (total == 0) {
            TextoVacio("No hay Pokémon capturados.")
        } else {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                RuedaTipos(dist, Modifier.size(150.dp))
                LeyendaTipos(dist, total, Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun RuedaTipos(
    dist: Map<TipoPokemon, Int>,
    modifier: Modifier = Modifier
) {
    val total = dist.values.sum().toFloat().coerceAtLeast(1f)

    Canvas(modifier = modifier) {
        var startAngle = -90f

        dist.forEach { (tipo, count) ->
            val sweep = (count / total) * 360f

            drawArc(
                color = tipo.color,
                startAngle = startAngle,
                sweepAngle = sweep,
                useCenter = true,
                topLeft = Offset(0f, 0f),
                size = Size(size.width, size.height)
            )

            drawArc(
                color = Color(0x33000000),
                startAngle = startAngle,
                sweepAngle = sweep,
                useCenter = false,
                topLeft = Offset(0f, 0f),
                size = Size(size.width, size.height),
                style = Stroke(width = 1.5f)
            )

            startAngle += sweep
        }
    }
}

@Composable
private fun LeyendaTipos(
    dist: Map<TipoPokemon, Int>,
    total: Int,
    modifier: Modifier = Modifier
) {
    val tipos = TipoPokemon.entries.sortedByDescending { dist[it] ?: 0 }
    val mitad = (tipos.size + 1) / 2

    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(6.dp)) {

        listOf(tipos.take(mitad), tipos.drop(mitad)).forEach { grupo ->

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                grupo.forEach { tipo ->
                    val count = dist[tipo] ?: 0
                    val pct = if (total > 0) (count * 100f / total).roundToInt() else 0

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {

                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    tipo.color.copy(alpha = if (count > 0) 1f else 0.3f),
                                    CircleShape
                                )
                        )

                        Text(
                            text = tipo.name.lowercase().replaceFirstChar { it.uppercase() },
                            color = ColorClaro.copy(alpha = if (count > 0) 0.85f else 0.3f),
                            fontSize = 9.sp,
                            modifier = Modifier.weight(1f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        if (count > 0) {
                            Text(
                                text = "$pct%",
                                color = tipo.color,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SeccionConsultasIA(datos: EstadisticasData) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "CONSULTAS A NUZBOT IA",
            color = ColorClaro.copy(alpha = 0.45f),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
        )

        Box(
            modifier = Modifier
                .size(150.dp)
                .background(ColorRojo.copy(alpha = 0.12f), CircleShape)
                .border(2.dp, ColorRojo.copy(alpha = 0.7f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "${datos.consultasIA}",
                    color = ColorRojoVibrante,
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = if (datos.consultasIA == 1) "consulta" else "consultas",
                    color = ColorClaro.copy(alpha = 0.5f),
                    fontSize = 13.sp
                )
            }
        }

        Text(
            text = "veces recurriste a la IA durante tu aventura",
            color = ColorClaro.copy(alpha = 0.4f),
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
private fun ResumenCompleto(
    datos: EstadisticasData,
    audioManager: AudioManager? = null,
    onDescargar: (Context) -> Unit
){
    val gradient = Brush.verticalGradient(colors = listOf(ColorOscuro, ColorOscuro, ColorRojo))
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item { Spacer(Modifier.height(24.dp)) }

        item {
            Text(
                "RESUMEN DE TU AVENTURA",
                color = ColorRojo,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item { HorizontalDivider(color = ColorRojo.copy(alpha = 0.3f)) }

        item { SeccionResultado(datos) }

        item { HorizontalDivider(color = ColorClaro.copy(alpha = 0.08f)) }

        item { SeccionCapturas(datos) }

        item { HorizontalDivider(color = ColorClaro.copy(alpha = 0.08f)) }

        item {
            Text(
                "MÁS USADOS EN COMBATE",
                color = ColorClaro.copy(alpha = 0.45f),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )
        }
        item { SeccionTopUsados(datos) }

        item { HorizontalDivider(color = ColorClaro.copy(alpha = 0.08f)) }

        item {
            Text(
                "COMBATES MÁS MORTALES",
                color = ColorClaro.copy(alpha = 0.45f),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )
        }
        item { SeccionTopMortales(datos) }

        item { HorizontalDivider(color = ColorClaro.copy(alpha = 0.08f)) }

        item {
            Text(
                "RIVALES MÁS LETALES",
                color = ColorClaro.copy(alpha = 0.45f),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )
        }
        item { SeccionTopAsesinos(datos) }

        item { HorizontalDivider(color = ColorClaro.copy(alpha = 0.08f)) }

        item { SeccionTipos(datos) }

        item { HorizontalDivider(color = ColorClaro.copy(alpha = 0.08f)) }

        item { SeccionConsultasIA(datos) }

        item { HorizontalDivider(color = ColorClaro.copy(alpha = 0.08f)) }

        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { audioManager?.playSound(GameSound.CLICK); onDescargar(context) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = ColorClaro.copy(alpha = 0.12f)),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        text = "Descargar",
                        color = ColorClaro,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Text(
                    text = "Descarga las estadísticas de tu aventura en un archivo",
                    color = ColorClaro.copy(alpha = 0.35f),
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        item { Spacer(Modifier.height(32.dp)) }
    }
}


@Composable
private fun TextoVacio(mensaje: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(ColorClaro.copy(alpha = 0.05f), RoundedCornerShape(12.dp))
            .border(1.dp, ColorClaro.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(mensaje, color = ColorClaro.copy(alpha = 0.4f), fontSize = 13.sp, textAlign = TextAlign.Center)
    }
}
