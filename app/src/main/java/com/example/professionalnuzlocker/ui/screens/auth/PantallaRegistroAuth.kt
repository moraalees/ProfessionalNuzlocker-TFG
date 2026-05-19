package com.example.professionalnuzlocker.ui.screens.auth

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.professionalnuzlocker.R
import com.example.professionalnuzlocker.ui.theme.ColorClaro
import com.example.professionalnuzlocker.ui.theme.ColorOscuro
import com.example.professionalnuzlocker.ui.theme.ColorRojo
import com.example.professionalnuzlocker.ui.theme.ColorRojoVibrante

/**
 * Pantalla de creación de cuenta con campos de correo, contraseña y confirmación.
 * Valida formato, longitud mínima y coincidencia de contraseñas antes de llamar a
 * [AuthViewModel.registrar]; en caso de éxito invoca [onRegistroExitoso].
 * [irLogin] navega de vuelta a [PantallaLogin].
 */
@Composable
fun PantallaRegistroAuth(
    onRegistroExitoso: () -> Unit,
    irLogin: () -> Unit
) {
    val viewModel: AuthViewModel = viewModel()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmError by remember { mutableStateOf<String?>(null) }

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = ColorClaro,
        unfocusedTextColor = ColorClaro,
        errorTextColor = ColorClaro,
        focusedBorderColor = ColorRojoVibrante,
        unfocusedBorderColor = ColorClaro.copy(alpha = 0.4f),
        cursorColor = ColorRojoVibrante,
        errorCursorColor = ColorRojoVibrante,
        focusedLabelColor = ColorRojoVibrante,
        unfocusedLabelColor = ColorClaro.copy(alpha = 0.7f)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(ColorOscuro, ColorOscuro, ColorRojo))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )

            Text(
                "Crear cuenta",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 26.sp),
                color = ColorClaro
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it; emailError = null; viewModel.limpiarError() },
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    isError = emailError != null,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors
                )
                if (emailError != null) {
                    Text(
                        text = emailError!!,
                        color = ColorRojoVibrante,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it; passwordError = null; viewModel.limpiarError() },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    isError = passwordError != null,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = null,
                                tint = ColorClaro.copy(alpha = 0.7f)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors
                )
                if (passwordError != null) {
                    Text(
                        text = passwordError!!,
                        color = ColorRojoVibrante,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it; confirmError = null },
                    label = { Text("Confirmar contraseña") },
                    singleLine = true,
                    isError = confirmError != null,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors
                )
                if (confirmError != null) {
                    Text(
                        text = confirmError!!,
                        color = ColorRojoVibrante,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }
            }

            viewModel.error?.let {
                Text(
                    it,
                    color = ColorRojoVibrante,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Button(
                onClick = {
                    emailError = null
                    passwordError = null
                    confirmError = null
                    val emailTrimmed = email.trim()
                    when {
                        emailTrimmed.isBlank() ->
                            emailError = "Introduce tu correo electrónico."
                        !Patterns.EMAIL_ADDRESS.matcher(emailTrimmed).matches() ->
                            emailError = "El correo no tiene un formato válido."
                        password.isBlank() ->
                            passwordError = "Introduce una contraseña."
                        password.length < 8 ->
                            passwordError = "La contraseña debe tener al menos 8 caracteres."
                        confirmPassword.isBlank() ->
                            confirmError = "Confirma tu contraseña."
                        password != confirmPassword ->
                            confirmError = "Las contraseñas no coinciden."
                        else ->
                            viewModel.registrar(emailTrimmed, password, onRegistroExitoso)
                    }
                },
                enabled = !viewModel.cargando,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = ColorRojoVibrante)
            ) {
                if (viewModel.cargando) {
                    CircularProgressIndicator(
                        color = ColorClaro,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Registrarse",
                        color = ColorClaro,
                        fontSize = 16.sp
                    )
                }
            }

            TextButton(
                onClick = irLogin
            ) {
                Text(
                    text = "¿Ya tienes cuenta? Inicia sesión",
                    color = ColorClaro.copy(alpha = 0.8f)
                )
            }
        }
    }
}
