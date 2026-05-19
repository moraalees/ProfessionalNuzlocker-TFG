package com.example.professionalnuzlocker.ui.screens.guide

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.professionalnuzlocker.ui.theme.ColorClaro
import com.example.professionalnuzlocker.ui.theme.ColorGrisaceo
import com.example.professionalnuzlocker.ui.theme.ColorOscuro
import com.example.professionalnuzlocker.ui.theme.ColorOshawott
import com.example.professionalnuzlocker.ui.theme.ColorRojo
import com.example.professionalnuzlocker.ui.theme.ColorRojoVibrante
import com.example.professionalnuzlocker.ui.utils.AudioManager
import com.example.professionalnuzlocker.ui.utils.GameSound

/**
 * Pantalla de guía con seis tarjetas expandibles que explican las reglas del Nuzlocke,
 * condiciones de victoria y derrota, seguimiento del progreso y el asistente IA NuzBot.
 * Cada tarjeta presenta la información mediante el componente de cuadro de diálogo de la Prof. Encina.
 */
@Composable
fun PantallaGuia(audioManager: AudioManager? = null) {
    val fondoDesvanecido = Brush.verticalGradient(
        colors = listOf(ColorOscuro, ColorOscuro, ColorRojo)
    )

    var mostrarExplicacion by rememberSaveable { mutableStateOf(false) }
    var mostrarReglas by rememberSaveable { mutableStateOf(false) }
    var mostrarVictoria by rememberSaveable { mutableStateOf(false) }
    var mostrarDerrota by rememberSaveable { mutableStateOf(false) }
    var mostrarProgreso by rememberSaveable { mutableStateOf(false) }
    var mostrarIA by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize().background(fondoDesvanecido),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "GUÍA NUZLOCKE",
                style = TextStyle(
                    fontWeight = FontWeight.Black,
                    letterSpacing = (1.5).sp,
                    fontSize = 38.sp,
                    lineHeight = 10.sp
                ),
                color = ColorOshawott
            )
            Spacer(Modifier.height(19.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Card(
                    modifier = Modifier
                        .height(200.dp)
                        .width(165.dp)
                        .padding(10.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, ColorRojoVibrante)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(ColorGrisaceo)
                            .fillMaxSize()
                            .padding(top = 34.dp)
                    ) {
                        Text(
                            text = "¿QUÉ ES?",
                            color = Color.White,
                            style = TextStyle(
                                fontWeight = FontWeight.Black,
                                letterSpacing = (1.5).sp,
                                fontSize = 20.sp,
                                lineHeight = 10.sp
                            )
                        )
                        Spacer(Modifier.height(40.dp))
                        Button(
                            onClick = { audioManager?.playSound(GameSound.CLICK); mostrarExplicacion = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ColorClaro,
                                contentColor = ColorGrisaceo
                            )
                        ) {
                            Text(text = "+", fontSize = 30.sp)
                        }
                    }
                }
                Spacer(Modifier.width(31.dp))
                Card(
                    modifier = Modifier
                        .height(200.dp)
                        .width(165.dp)
                        .padding(10.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, ColorRojoVibrante)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(ColorClaro)
                            .fillMaxSize()
                            .padding(top = 34.dp)
                    ) {
                        Text(
                            text = "REGLAS",
                            color = Color.Black,
                            style = TextStyle(
                                fontWeight = FontWeight.Black,
                                letterSpacing = (1.5).sp,
                                fontSize = 20.sp,
                                lineHeight = 10.sp
                            )
                        )
                        Spacer(Modifier.height(40.dp))
                        Button(
                            onClick = { audioManager?.playSound(GameSound.CLICK); mostrarReglas = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ColorGrisaceo,
                                contentColor = ColorClaro
                            )
                        ) {
                            Text(text = "+", fontSize = 30.sp)
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Card(
                    modifier = Modifier
                        .height(200.dp)
                        .width(165.dp)
                        .padding(10.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, ColorRojoVibrante)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(ColorClaro)
                            .fillMaxSize()
                            .padding(top = 34.dp)
                    ) {
                        Text(
                            text = "VICTORIA",
                            color = Color.Black,
                            style = TextStyle(
                                fontWeight = FontWeight.Black,
                                letterSpacing = (1.5).sp,
                                fontSize = 20.sp,
                                lineHeight = 10.sp
                            )
                        )
                        Spacer(Modifier.height(40.dp))
                        Button(
                            onClick = { audioManager?.playSound(GameSound.CLICK); mostrarVictoria = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ColorGrisaceo,
                                contentColor = ColorClaro
                            )
                        ) {
                            Text(text = "+", fontSize = 30.sp)
                        }
                    }
                }
                Spacer(Modifier.width(31.dp))
                Card(
                    modifier = Modifier
                        .height(200.dp)
                        .width(165.dp)
                        .padding(10.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, ColorRojoVibrante)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(ColorGrisaceo)
                            .fillMaxSize()
                            .padding(top = 34.dp)
                    ) {
                        Text(
                            text = "DERROTA",
                            color = Color.White,
                            style = TextStyle(
                                fontWeight = FontWeight.Black,
                                letterSpacing = (1.5).sp,
                                fontSize = 20.sp,
                                lineHeight = 10.sp
                            )
                        )
                        Spacer(Modifier.height(40.dp))
                        Button(
                            onClick = { audioManager?.playSound(GameSound.CLICK); mostrarDerrota = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ColorClaro,
                                contentColor = ColorGrisaceo
                            )
                        ) {
                            Text(text = "+", fontSize = 30.sp)
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Card(
                    modifier = Modifier
                        .height(200.dp)
                        .width(165.dp)
                        .padding(10.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, ColorRojoVibrante)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(ColorGrisaceo)
                            .fillMaxSize()
                            .padding(top = 34.dp)
                    ) {
                        Text(
                            text = "PROGRESO",
                            color = Color.White,
                            style = TextStyle(
                                fontWeight = FontWeight.Black,
                                letterSpacing = (1.5).sp,
                                fontSize = 20.sp,
                                lineHeight = 10.sp
                            )
                        )
                        Spacer(Modifier.height(40.dp))
                        Button(
                            onClick = { audioManager?.playSound(GameSound.CLICK); mostrarProgreso = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ColorClaro,
                                contentColor = ColorGrisaceo
                            )
                        ) {
                            Text(text = "+", fontSize = 30.sp)
                        }
                    }
                }
                Spacer(Modifier.width(31.dp))
                Card(
                    modifier = Modifier
                        .height(200.dp)
                        .width(165.dp)
                        .padding(10.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, ColorRojoVibrante)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(ColorClaro)
                            .fillMaxSize()
                            .padding(top = 34.dp)
                    ) {
                        Text(
                            text = "IA",
                            color = Color.Black,
                            style = TextStyle(
                                fontWeight = FontWeight.Black,
                                letterSpacing = (1.5).sp,
                                fontSize = 20.sp,
                                lineHeight = 10.sp
                            )
                        )
                        Spacer(Modifier.height(40.dp))
                        Button(
                            onClick = { audioManager?.playSound(GameSound.CLICK); mostrarIA = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ColorGrisaceo,
                                contentColor = ColorClaro
                            )
                        ) {
                            Text(text = "+", fontSize = 30.sp)
                        }
                    }
                }
            }
        }
    }

    if (mostrarExplicacion) {
        Dialog(
            onDismissRequest = { mostrarExplicacion = false }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(2.dp, ColorRojoVibrante),
                colors = CardDefaults.cardColors(containerColor = ColorGrisaceo)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "¿QUÉ ES?",
                        color = Color.White,
                        style = TextStyle(
                            fontWeight = FontWeight.Black,
                            fontSize = 22.sp,
                            letterSpacing = 2.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .width(60.dp)
                            .background(ColorRojoVibrante)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Un Locke es una forma de jugar a Pokémon con reglas autoimpuestas que aumentan la dificultad. Hay muchísimos tipos de Lockes, pero esta aplicación se basa exclusivamente en los Nuzlockes, cuyas reglas varían y se pueden observar en el recuadro de 'Reglas'.",
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 22.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    Button(
                        onClick = { mostrarExplicacion = false },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ColorClaro,
                            contentColor = ColorGrisaceo
                        )
                    ) {
                        Text(
                            text = "Cerrar",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

    if (mostrarReglas) {
        Dialog(
            onDismissRequest = { mostrarReglas = false }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(2.dp, ColorRojoVibrante),
                colors = CardDefaults.cardColors(containerColor = ColorClaro)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "REGLAS BÁSICAS",
                        color = Color.Black,
                        style = TextStyle(
                            fontWeight = FontWeight.Black,
                            fontSize = 22.sp,
                            letterSpacing = 2.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .width(60.dp)
                            .background(ColorRojoVibrante)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Las reglas más comunes son las siguientes:\n 1. Si un Pokémon se debilita, no podrá ser usado nunca más.\n 2. Solo puedes capturar al primer Pokémon de la zona que te aparezca donde vayas a capturar.\n 3. No puedes comprar pociones en las tiendas.\n 4. No puedes subir a tus Pokémon a un nivel mayor al del siguiente líder de gimnasio.\n 5. Tienes 10 vidas a lo largo de tu aventura, por lo que no debes perder más de 10 Pokémon.\n Cabe recalcar que las normas son autoimpuestas, por lo que cada jugador puede declarar sus propias normas.",
                        color = Color.Black,
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 22.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    Button(
                        onClick = { mostrarReglas = false },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ColorGrisaceo,
                            contentColor = ColorClaro
                        )
                    ) {
                        Text(
                            text = "Cerrar",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

    if (mostrarDerrota) {
        Dialog(
            onDismissRequest = { mostrarDerrota = false }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(2.dp, ColorRojoVibrante),
                colors = CardDefaults.cardColors(containerColor = ColorGrisaceo)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "LA DERROTA",
                        color = Color.White,
                        style = TextStyle(
                            fontWeight = FontWeight.Black,
                            fontSize = 22.sp,
                            letterSpacing = 2.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .width(60.dp)
                            .background(ColorRojoVibrante)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Perderás la partida si bien pierdes todas las vidas que te quedaban o si bien pierdes un combate. Da igual si te sobran vidas, no puedes ser derrotado nunca.",
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 22.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    Button(
                        onClick = { mostrarDerrota = false },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ColorClaro,
                            contentColor = ColorGrisaceo
                        )
                    ) {
                        Text(
                            text = "Cerrar",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

    if (mostrarVictoria) {
        Dialog(
            onDismissRequest = { mostrarVictoria = false }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(2.dp, ColorRojoVibrante),
                colors = CardDefaults.cardColors(containerColor = ColorClaro)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "LA VICTORIA",
                        color = Color.Black,
                        style = TextStyle(
                            fontWeight = FontWeight.Black,
                            fontSize = 22.sp,
                            letterSpacing = 2.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .width(60.dp)
                            .background(ColorRojoVibrante)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Ganarás el desafío si derrotas a la Liga Pokémon y te sobra como mínimo una vida. De nada sirve ganar si pierdes la última vida en el último combate.",
                        color = Color.Black,
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 22.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    Button(
                        onClick = { mostrarVictoria = false },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ColorGrisaceo,
                            contentColor = ColorClaro
                        )
                    ) {
                        Text(
                            text = "Cerrar",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

    if (mostrarProgreso) {
        Dialog(
            onDismissRequest = { mostrarProgreso = false }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(2.dp, ColorRojoVibrante),
                colors = CardDefaults.cardColors(containerColor = ColorGrisaceo)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "PROGRESO",
                        color = Color.White,
                        style = TextStyle(
                            fontWeight = FontWeight.Black,
                            fontSize = 22.sp,
                            letterSpacing = 2.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .width(60.dp)
                            .background(ColorRojoVibrante)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "A lo largo de tu aventura podrás ir anotando los Pokémon que has capturado, los entrenadores que has vencido, los Pokémon que has perdido y los que estás usando en cierto momento de la aventura, las vidas que te quedan, donde atrapar o evolucionar ciertos Pokémon...",
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 22.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    Button(
                        onClick = { mostrarProgreso = false },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ColorClaro,
                            contentColor = ColorGrisaceo
                        )
                    ) {
                        Text(
                            text = "Cerrar",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

    if (mostrarIA) {
        Dialog(
            onDismissRequest = { mostrarIA = false }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(2.dp, ColorRojoVibrante),
                colors = CardDefaults.cardColors(containerColor = ColorClaro)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "GUÍA DE IA",
                        color = Color.Black,
                        style = TextStyle(
                            fontWeight = FontWeight.Black,
                            fontSize = 22.sp,
                            letterSpacing = 2.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .width(60.dp)
                            .background(ColorRojoVibrante)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Esta aplicación cuenta con un chat integrado con IA dispuesta a ayudarte en momentos donde necesites una estrategia o un turno decisivo en un combate. Podrás usarla en cualquier momento, ya que estará actualizada según el estado de tu partida.",
                        color = Color.Black,
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 22.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    Button(
                        onClick = { mostrarIA = false },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ColorGrisaceo,
                            contentColor = ColorClaro
                        )
                    ) {
                        Text(
                            text = "Cerrar",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}