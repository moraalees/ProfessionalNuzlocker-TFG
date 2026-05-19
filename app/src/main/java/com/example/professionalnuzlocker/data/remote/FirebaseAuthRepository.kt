package com.example.professionalnuzlocker.data.remote

import com.example.professionalnuzlocker.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Implementación de [AuthRepository] sobre Firebase Authentication.
 *
 * Envuelve las callbacks asíncronas de Firebase en corrutinas con
 * [suspendCancellableCoroutine]. Expone login, registro, cierre de sesión
 * y comprobación de sesión activa.
 */
class FirebaseAuthRepository: AuthRepository {
    private val auth = FirebaseAuth.getInstance()

    /** Inicia sesión con email y contraseña. Devuelve [Result.success] si Firebase lo acepta o [Result.failure] con la excepción en caso contrario. */
    override suspend fun login(email: String, contrasena: String): Result<Unit> {
        return try {
            suspendCancellableCoroutine { cont ->
                auth.signInWithEmailAndPassword(email, contrasena)
                    .addOnSuccessListener { cont.resume(Result.success(Unit)) }
                    .addOnFailureListener { cont.resume(Result.failure(it)) }
                cont.invokeOnCancellation { }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /** Crea una cuenta nueva en Firebase con email y contraseña. Devuelve [Result.success] o [Result.failure] con la excepción de Firebase. */
    override suspend fun registro(email: String, contrasena: String): Result<Unit> {
        return try {
            suspendCancellableCoroutine { cont ->
                auth.createUserWithEmailAndPassword(email, contrasena)
                    .addOnSuccessListener { cont.resume(Result.success(Unit)) }
                    .addOnFailureListener { cont.resume(Result.failure(it)) }
                cont.invokeOnCancellation { }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /** Cierra la sesión del usuario actual en Firebase. */
    override fun cerrarSesion() = auth.signOut()

    /** Devuelve true si hay un usuario autenticado en este momento. */
    override fun sesionActiva(): Boolean = auth.currentUser != null
}
