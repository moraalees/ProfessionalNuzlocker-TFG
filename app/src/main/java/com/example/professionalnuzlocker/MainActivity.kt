package com.example.professionalnuzlocker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.professionalnuzlocker.ui.navigation.NavegadorPrincipal
import com.example.professionalnuzlocker.ui.screens.form.PantallaFormulario
import com.example.professionalnuzlocker.ui.screens.guide.PantallaGuia
import com.example.professionalnuzlocker.ui.screens.home.PantallaInicio
import com.example.professionalnuzlocker.ui.screens.pokedex.PantallaPokedex
import com.example.professionalnuzlocker.ui.theme.ProfessionalNuzlockerTheme
import com.example.professionalnuzlocker.ui.utils.AudioManager

/**
 * Punto de entrada de la aplicación. Inicializa [AudioManager], habilita el modo edge-to-edge
 * y lanza [NavegadorPrincipal] como raíz del árbol de composición.
 *
 * Gestiona el ciclo de vida de la música de fondo: la reanuda en [onResume] y la pausa
 * en [onPause] / [onStop] para que el audio no continúe cuando la app pasa a segundo plano.
 */
class MainActivity : ComponentActivity() {

    private lateinit var audioManager: AudioManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        audioManager = AudioManager(this)

        setContent {
            NavegadorPrincipal(audioManager)
        }
    }

    override fun onResume() {
        super.onResume()
        audioManager.startAmbientMusic()
    }

    override fun onPause() {
        super.onPause()
        audioManager.pauseAmbientMusic()
    }

    override fun onStop() {
        super.onStop()
        audioManager.pauseAmbientMusic()
    }
}