package com.example.professionalnuzlocker.ui.screens.pokedex

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.professionalnuzlocker.data.repository.Pokedex
import com.example.professionalnuzlocker.ui.components.PokemonPokedexComponente
import com.example.professionalnuzlocker.ui.utils.AudioManager
import com.example.professionalnuzlocker.ui.utils.GameSound
import com.example.professionalnuzlocker.ui.theme.ColorClaro
import com.example.professionalnuzlocker.ui.theme.ColorGrisaceo
import com.example.professionalnuzlocker.ui.theme.ColorOscuro
import com.example.professionalnuzlocker.ui.theme.ColorRojo
import com.example.professionalnuzlocker.ui.theme.ColorRojoVibrante

@Composable
fun PantallaPokedex(audioManager: AudioManager? = null) {
    val fondoDesvanecido = Brush.verticalGradient(
        colors = listOf(ColorOscuro, ColorOscuro, ColorRojo)
    )
    val listaPokemon = Pokedex.getPokemon()
    val lazyGridState = rememberLazyGridState()
    val focusManager = LocalFocusManager.current

    var textoBusqueda by remember { mutableStateOf("") }

    val pokemonFiltrado = remember(textoBusqueda) {
        if (textoBusqueda.isBlank()) listaPokemon
        else listaPokemon.filter { it.nombre.startsWith(textoBusqueda.trim(), ignoreCase = true) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoDesvanecido)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "POKÉDEX",
            style = TextStyle(
                fontWeight = FontWeight.Black,
                letterSpacing = (1.5).sp,
                fontSize = 38.sp,
                lineHeight = 10.sp
            ),
            color = ColorRojo,
            modifier = Modifier.padding(10.dp)
        )

        OutlinedTextField(
            value = textoBusqueda,
            onValueChange = { textoBusqueda = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            placeholder = {
                Text(
                    text = "Buscar Pokémon...",
                    color = ColorClaro.copy(alpha = 0.35f),
                    fontSize = 15.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = ColorClaro.copy(alpha = 0.45f),
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = textoBusqueda.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(onClick = {
                        audioManager?.playSound(GameSound.CLICK)
                        textoBusqueda = ""
                        focusManager.clearFocus()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Borrar búsqueda",
                            tint = ColorClaro.copy(alpha = 0.6f),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = ColorClaro,
                unfocusedTextColor = ColorClaro,
                focusedContainerColor = ColorGrisaceo,
                unfocusedContainerColor = ColorGrisaceo.copy(alpha = 0.7f),
                focusedBorderColor = ColorRojoVibrante.copy(alpha = 0.7f),
                unfocusedBorderColor = ColorClaro.copy(alpha = 0.15f),
                cursorColor = ColorRojoVibrante
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
            textStyle = TextStyle(fontSize = 15.sp)
        )

        if (pokemonFiltrado.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 60.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = "No se encontró ningún\nPokémon con ese nombre.",
                    color = ColorClaro.copy(alpha = 0.4f),
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )
            }
        } else {
            LazyVerticalGrid(
                state = lazyGridState,
                columns = GridCells.Fixed(2)
            ) {
                items(pokemonFiltrado) { pokemon ->
                    PokemonPokedexComponente(pokemon, listaPokemon, audioManager)
                }
            }
        }
    }
}
