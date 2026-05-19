package com.example.professionalnuzlocker.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.professionalnuzlocker.data.model.enum_classes.RutasNavegacion
import com.example.professionalnuzlocker.ui.theme.ColorClaro
import com.example.professionalnuzlocker.ui.theme.ColorOscuro
import com.example.professionalnuzlocker.ui.theme.ColorRojo

/**
 * Barra de navegación inferior con 4 destinos principales: Registro, Pokédex, Pokémon e IA.
 *
 * Fondo [ColorRojo]; icono seleccionado en [ColorOscuro] e icono normal en [ColorClaro].
 * Cada ítem navega con `launchSingleTop = true` haciendo popUpTo [RutasNavegacion.HOME]
 * para evitar apilar duplicados en el back stack.
 *
 * @param navController Controlador de navegación para cambiar de destino.
 */
@Composable
fun MiBottomBar(navController: NavController) {
    val rutaActual = navController.currentBackStackEntryAsState().value?.destination?.route

    val indice = when (rutaActual){
        RutasNavegacion.REGISTRO_JUEGO.nombre -> 0
        RutasNavegacion.POKEDEX.nombre -> 1
        RutasNavegacion.DATOS_JUEGO.nombre -> 2
        RutasNavegacion.CHAT_IA.nombre -> 3
        else -> 0
    }

    NavigationBar(
        containerColor = ColorRojo
    ) {
        NavigationBarItem(
            selected = indice == 0,
            onClick = {
                navController.navigate(RutasNavegacion.REGISTRO_JUEGO.nombre) {
                    popUpTo(RutasNavegacion.HOME.nombre)
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Casa"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = ColorOscuro,
                unselectedIconColor = ColorClaro,
                selectedTextColor = ColorOscuro,
                unselectedTextColor = ColorClaro
            ),
            label = { Text("Registro") }
        )
        NavigationBarItem(
            selected = indice == 1,
            onClick = {
                navController.navigate(RutasNavegacion.POKEDEX.nombre) {
                    popUpTo(RutasNavegacion.HOME.nombre)
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorito"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = ColorOscuro,
                unselectedIconColor = ColorClaro,
                selectedTextColor = ColorOscuro,
                unselectedTextColor = ColorClaro
            ),
            label = { Text("Pokedex") }
        )
        NavigationBarItem(
            selected = indice == 2,
            onClick = {
                navController.navigate(RutasNavegacion.DATOS_JUEGO.nombre) {
                    popUpTo(RutasNavegacion.HOME.nombre)
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Carro de compras"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = ColorOscuro,
                unselectedIconColor = ColorClaro,
                selectedTextColor = ColorOscuro,
                unselectedTextColor = ColorClaro
            ),
            label = { Text("Pokemon") }
        )
        NavigationBarItem(
            selected = indice == 3,
            onClick = {
                navController.navigate(RutasNavegacion.CHAT_IA.nombre) {
                    popUpTo(RutasNavegacion.HOME.nombre)
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.MailOutline,
                    contentDescription = "Correo"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = ColorOscuro,
                unselectedIconColor = ColorClaro,
                selectedTextColor = ColorOscuro,
                unselectedTextColor = ColorClaro
            ),
            label = { Text("IA") }
        )
    }
}