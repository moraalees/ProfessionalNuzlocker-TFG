package com.example.professionalnuzlocker.data.repository

interface AuthRepository {
    suspend fun login(email: String, contrasena: String): Result<Unit>
    suspend fun registro(email: String, contrasena: String): Result<Unit>
    fun cerrarSesion()
    fun sesionActiva(): Boolean
}
