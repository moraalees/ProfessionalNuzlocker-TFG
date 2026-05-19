package com.example.professionalnuzlocker.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.professionalnuzlocker.data.model.Pokemon
import com.example.professionalnuzlocker.data.model.enum_classes.Rutas
import com.example.professionalnuzlocker.ui.theme.ColorClaro
import com.example.professionalnuzlocker.ui.theme.ColorOscuro
import com.example.professionalnuzlocker.ui.theme.ColorRojoVibrante
import com.example.professionalnuzlocker.ui.utils.AudioManager
import com.example.professionalnuzlocker.ui.utils.GameSound

/**
 * Tarjeta de Pokémon para la Pokédex que muestra su sprite y nombre.
 *
 * Al pulsarla abre un diálogo con información completa: ID, tipos (con su color),
 * habilidades, línea evolutiva con métodos y rutas de captura. Las rutas son
 * pulsables para ver un mapa de imagen en un segundo diálogo; si hay más de dos
 * aparece un enlace "+N más..." que abre la lista completa.
 *
 * @param pokemon Pokémon a mostrar.
 * @param pokedex Lista completa de Pokémon para resolver la línea evolutiva.
 * @param audioManager Gestor de audio opcional para reproducir sonido al pulsar.
 */
@Composable
fun PokemonPokedexComponente(
    pokemon: Pokemon,
    pokedex: List<Pokemon>,
    audioManager: AudioManager? = null
) {
    var mostrarDialog by rememberSaveable { mutableStateOf(false) }
    var mostrarDialogRutas by rememberSaveable { mutableStateOf(false) }
    var rutaSeleccionada by rememberSaveable { mutableStateOf<Rutas?>(null) }
    var mostrarDialogRutaImagen by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .height(280.dp)
            .width(200.dp)
            .clickable {
                audioManager?.playSound(GameSound.CLICK)
                mostrarDialog = true
            }
            .padding(12.dp),
        border = BorderStroke(2.dp, ColorRojoVibrante)
    ) {
        Box(
            modifier = Modifier
                .background(ColorOscuro)
                .fillMaxSize()
        ) {
            Text(
                text = "#${pokemon.idPokedex.toString().padStart(3, '0')}",
                color = ColorClaro,
                style = TextStyle(
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp,
                    fontSize = 18.sp,
                    lineHeight = 18.sp
                ),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(10.dp)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                Image(
                    painter = painterResource(id = pokemon.imagen),
                    contentDescription = "Pokémon ${pokemon.nombre}",
                    modifier = Modifier.size(110.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = pokemon.nombre.replaceFirstChar { it.uppercase() },
                    color = ColorClaro,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        letterSpacing = 0.5.sp
                    )
                )
            }
        }
    }

    if (mostrarDialog) {
        Dialog(
            onDismissRequest = { mostrarDialog = false }
        ) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = ColorOscuro,
                border = BorderStroke(2.dp, ColorRojoVibrante),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(520.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "#${pokemon.idPokedex.toString().padStart(3, '0')}",
                        color = ColorClaro,
                        style = TextStyle(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp,
                            fontSize = 20.sp
                        ),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(12.dp)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = pokemon.imagen),
                            contentDescription = pokemon.nombre,
                            modifier = Modifier.size(150.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = pokemon.nombre,
                            color = ColorClaro,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            val tipos = if (pokemon.tipo2 != null)
                                listOf(pokemon.tipo1, pokemon.tipo2)
                            else
                                listOf(pokemon.tipo1)
                            tipos.forEach { tipo ->
                                val textColor =
                                    if (tipo.color.luminance() > 0.6f)
                                        ColorOscuro
                                    else
                                        ColorClaro
                                Box(
                                    modifier = Modifier
                                        .background(tipo.color, RoundedCornerShape(10.dp))
                                        .padding(horizontal = 12.dp, vertical = 5.dp)
                                ) {
                                    Text(
                                        text = tipo.name,
                                        color = textColor,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                pokemon.habilidades.take(2).forEach {
                                    Box(
                                        modifier = Modifier
                                            .background(ColorRojoVibrante.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                                            .padding(horizontal = 10.dp, vertical = 5.dp)
                                    ) {
                                        Text(
                                            text = it,
                                            color = ColorClaro,
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }
                            if (pokemon.habilidades.size >= 3) {
                                Box(
                                    modifier = Modifier
                                        .background(ColorRojoVibrante.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                                        .padding(horizontal = 10.dp, vertical = 5.dp)
                                ) {
                                    Text(
                                        text = pokemon.habilidades[2],
                                        color = ColorClaro,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        val evoluciones = pokemon.lineaEvolutiva
                            .mapNotNull { id -> pokedex.find { it.idPokedex == id } }
                            .sortedBy { it.idPokedex }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            evoluciones.forEachIndexed { index, evo ->
                                Image(
                                    painter = painterResource(id = evo.imagen),
                                    contentDescription = evo.nombre,
                                    modifier = Modifier
                                        .size(60.dp)
                                        .background(ColorRojoVibrante.copy(0.15f), CircleShape)
                                        .padding(6.dp)
                                )
                                if (index < evoluciones.size - 1) {
                                    val metodo = evo.metodoEvolutivo.values.firstOrNull { it != "Etapa final" }
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "→",
                                            color = ColorClaro,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = metodo ?: "",
                                            color = ColorClaro,
                                            fontSize = 10.sp
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Dónde atraparlo:",
                                color = ColorClaro,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            val rutasEnum = pokemon.rutasCaptura.mapNotNull { nombre ->
                                Rutas.entries.find { it.nombreRuta == nombre }
                            }
                            if (rutasEnum.isEmpty()) {
                                Text(
                                    text = "Desconocido",
                                    color = ColorClaro,
                                    fontSize = 13.sp
                                )
                            } else {
                                val maxVisible = 2
                                val visibles = rutasEnum.take(maxVisible)

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        visibles.forEach { ruta ->
                                            Box(
                                                modifier = Modifier
                                                    .background(ColorRojoVibrante.copy(0.2f), RoundedCornerShape(8.dp))
                                                    .clickable {
                                                        audioManager?.playSound(GameSound.CLICK)
                                                        rutaSeleccionada = ruta
                                                        mostrarDialogRutaImagen = true
                                                    }
                                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                                            ) {
                                                Text(
                                                    text = ruta.nombreRuta,
                                                    color = ColorClaro,
                                                    fontSize = 12.sp
                                                )
                                            }
                                        }
                                    }

                                    if (rutasEnum.size > maxVisible) {
                                        Text(
                                            text = "+${rutasEnum.size - maxVisible} más...",
                                            color = ColorRojoVibrante,
                                            fontSize = 12.sp,
                                            modifier = Modifier.clickable {
                                                audioManager?.playSound(GameSound.CLICK)
                                                mostrarDialogRutas = true
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (mostrarDialogRutaImagen && rutaSeleccionada != null) {
        Dialog(
            onDismissRequest = { mostrarDialogRutaImagen = false }
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = ColorOscuro,
                border = BorderStroke(2.dp, ColorRojoVibrante),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = rutaSeleccionada!!.nombreRuta,
                        color = ColorClaro,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Image(
                        painter = painterResource(id = rutaSeleccionada!!.imagen),
                        contentDescription = rutaSeleccionada!!.nombreRuta,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }

    if (mostrarDialogRutas) {
        Dialog(
            onDismissRequest = { mostrarDialogRutas = false }
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = ColorOscuro,
                border = BorderStroke(2.dp, ColorRojoVibrante),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Todas las rutas",
                        color = ColorClaro,
                        fontWeight = FontWeight.Bold
                    )

                    pokemon.rutasCaptura.forEach { nombreRuta ->
                        val ruta = Rutas.entries.find { it.nombreRuta == nombreRuta }
                        Box(
                            modifier = Modifier
                                .background(ColorRojoVibrante.copy(0.2f), RoundedCornerShape(8.dp))
                                .then(
                                    if (ruta != null) Modifier.clickable {
                                        audioManager?.playSound(GameSound.CLICK)
                                        rutaSeleccionada = ruta
                                        mostrarDialogRutaImagen = true
                                    } else Modifier
                                )
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = nombreRuta,
                                color = ColorClaro
                            )
                        }
                    }
                }
            }
        }
    }
}