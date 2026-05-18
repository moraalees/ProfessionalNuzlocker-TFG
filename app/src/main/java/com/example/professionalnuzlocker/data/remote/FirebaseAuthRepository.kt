package com.example.professionalnuzlocker.data.remote

import com.example.professionalnuzlocker.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class FirebaseAuthRepository: AuthRepository {
    private val auth = FirebaseAuth.getInstance()

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

    override fun cerrarSesion() = auth.signOut()

    override fun sesionActiva(): Boolean = auth.currentUser != null
}
