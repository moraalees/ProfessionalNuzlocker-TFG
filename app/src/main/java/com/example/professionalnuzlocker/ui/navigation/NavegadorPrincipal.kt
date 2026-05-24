package com.example.professionalnuzlocker.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.professionalnuzlocker.data.model.enum_classes.RutasNavegacion
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.professionalnuzlocker.ui.screens.auth.PantallaLogin
import com.example.professionalnuzlocker.ui.screens.auth.PantallaRegistroAuth
import com.example.professionalnuzlocker.ui.screens.chat.PantallaChat
import com.example.professionalnuzlocker.ui.screens.chat.PantallaChatViewModel
import com.example.professionalnuzlocker.ui.screens.form.PantallaFormulario
import com.example.professionalnuzlocker.ui.screens.guide.PantallaGuia
import com.example.professionalnuzlocker.ui.screens.home.PantallaInicio
import com.example.professionalnuzlocker.ui.screens.infoRun.PantallaDatosJuego
import com.example.professionalnuzlocker.ui.screens.pokedex.PantallaPokedex
import com.example.professionalnuzlocker.ui.screens.register.PantallaRegistro
import com.example.professionalnuzlocker.ui.screens.stats.PantallaEstadisticas
import com.example.professionalnuzlocker.ui.utils.AudioManager
import com.google.firebase.auth.FirebaseAuth

/**
 * Composable raíz que configura el grafo de navegación completo de la app.
 *
 * Define 9 destinos con [NavHost]: login y registro de auth, inicio, guía, formulario
 * de nueva partida, registro de juego, estadísticas, Pokédex, datos del juego y chat IA.
 * La pantalla inicial es [RutasNavegacion.HOME] si Firebase tiene sesión activa, o
 * [RutasNavegacion.LOGIN] en caso contrario. La bottom bar solo se muestra en las 4
 * pantallas principales del juego (registro, Pokédex, datos y chat IA).
 *
 * @param audioManager Gestor de audio pasado a cada pantalla que lo necesite.
 */
@Composable
fun NavegadorPrincipal(audioManager: AudioManager) {
    val chatViewModel: PantallaChatViewModel = viewModel()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination?.route

    val startDestination = if (FirebaseAuth.getInstance().currentUser != null) {
        RutasNavegacion.HOME.nombre
    } else {
        RutasNavegacion.LOGIN.nombre
    }

    Scaffold(
        bottomBar = {
            if (
                rutaActual == RutasNavegacion.DATOS_JUEGO.nombre ||
                rutaActual == RutasNavegacion.CHAT_IA.nombre ||
                rutaActual == RutasNavegacion.POKEDEX.nombre ||
                rutaActual == RutasNavegacion.REGISTRO_JUEGO.nombre
            ) {
                MiBottomBar(navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(RutasNavegacion.LOGIN.nombre) {
                PantallaLogin(
                    onLoginExitoso = {
                        navController.navigate(RutasNavegacion.HOME.nombre) {
                            popUpTo(RutasNavegacion.LOGIN.nombre) { inclusive = true }
                        }
                    },
                    irRegistro = {
                        navController.navigate(RutasNavegacion.REGISTRO_AUTH.nombre)
                    }
                )
            }
            composable(RutasNavegacion.REGISTRO_AUTH.nombre) {
                PantallaRegistroAuth(
                    onRegistroExitoso = {
                        navController.navigate(RutasNavegacion.HOME.nombre) {
                            popUpTo(RutasNavegacion.LOGIN.nombre) { inclusive = true }
                        }
                    },
                    irLogin = {
                        navController.popBackStack()
                    }
                )
            }
            composable(RutasNavegacion.HOME.nombre) {
                PantallaInicio(
                    continuarPartida = {
                        navController.navigate(RutasNavegacion.REGISTRO_JUEGO.nombre)
                    },
                    mostrarGuia = {
                        navController.navigate(RutasNavegacion.GUIA.nombre)
                    },
                    irFormulario = {
                        navController.navigate(RutasNavegacion.FORMULARIO.nombre)
                    },
                    cerrarSesion = {
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate(RutasNavegacion.LOGIN.nombre) {
                            popUpTo(RutasNavegacion.HOME.nombre) { inclusive = true }
                        }
                    },
                    audioManager = audioManager
                )
            }
            composable(RutasNavegacion.GUIA.nombre) {
                PantallaGuia(audioManager = audioManager)
            }
            composable(RutasNavegacion.FORMULARIO.nombre) {
                PantallaFormulario(
                    empezarRegistro = {
                        navController.navigate(RutasNavegacion.REGISTRO_JUEGO.nombre)
                    },
                    audioManager = audioManager
                )
            }
            composable(RutasNavegacion.REGISTRO_JUEGO.nombre) {
                PantallaRegistro(
                    onIrEstadisticas = {
                        navController.navigate(RutasNavegacion.ESTADISTICAS.nombre)
                    },
                    audioManager = audioManager
                )
            }
            composable(RutasNavegacion.ESTADISTICAS.nombre) {
                PantallaEstadisticas(audioManager = audioManager)
            }
            composable(RutasNavegacion.POKEDEX.nombre) {
                PantallaPokedex(audioManager = audioManager)
            }
            composable(RutasNavegacion.DATOS_JUEGO.nombre) {
                PantallaDatosJuego(audioManager = audioManager)
            }
            composable(RutasNavegacion.CHAT_IA.nombre) {
                PantallaChat(viewModel = chatViewModel)
            }
        }
    }
}
