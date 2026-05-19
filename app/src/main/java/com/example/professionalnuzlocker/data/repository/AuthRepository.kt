package com.example.professionalnuzlocker.data.repository

/**
 * Contrato de autenticación de usuario.
 *
 * Define las cuatro operaciones básicas de auth: [login], [registro],
 * [cerrarSesion] y [sesionActiva].
 */
interface AuthRepository {
    /** Autentica al usuario con email y contraseña. */
    suspend fun login(email: String, contrasena: String): Result<Unit>
    /** Registra un nuevo usuario con email y contraseña. */
    suspend fun registro(email: String, contrasena: String): Result<Unit>
    /** Cierra la sesión del usuario actual. */
    fun cerrarSesion()
    /** Indica si hay una sesión de usuario activa en este momento. */
    fun sesionActiva(): Boolean
}
