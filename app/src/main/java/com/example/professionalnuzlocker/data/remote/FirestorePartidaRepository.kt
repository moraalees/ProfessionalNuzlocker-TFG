package com.example.professionalnuzlocker.data.remote

import com.example.professionalnuzlocker.data.model.EncuentroRuta
import com.example.professionalnuzlocker.data.model.Partida
import com.example.professionalnuzlocker.data.model.PokemonCapturado
import com.example.professionalnuzlocker.data.model.ResultadoCombate
import com.example.professionalnuzlocker.data.repository.PartidaRepository
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class FirestorePartidaRepository: PartidaRepository {
    private val baseDatos = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private fun userCol() = baseDatos
        .collection("partidas")
        .document(auth.currentUser!!.uid)
        .collection("partidas")

    override suspend fun guardarPartida(partida: Partida): Result<String> {
        return try {
            val partidaAGuardar = partida.copy(createdAt = Timestamp.now())

            suspendCancellableCoroutine { cont ->
                userCol().add(partidaAGuardar)
                    .addOnSuccessListener { docRef ->
                        cont.resume(Result.success(docRef.id))
                    }
                    .addOnFailureListener { ex ->
                        cont.resume(Result.failure(ex))
                    }

                cont.invokeOnCancellation { }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun existePartida(): Result<Boolean> {
        return try {
            suspendCancellableCoroutine { cont ->
                userCol()
                    .limit(1)
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        cont.resume(Result.success(!querySnapshot.isEmpty))
                    }
                    .addOnFailureListener { ex ->
                        cont.resume(Result.failure(ex))
                    }

                cont.invokeOnCancellation { }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun borrarPartidas(): Result<Unit> {
        return try {
            suspendCancellableCoroutine { cont ->
                userCol()
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        val batch = baseDatos.batch()

                        for (doc in querySnapshot.documents) {
                            batch.delete(doc.reference)
                        }

                        batch.commit()
                            .addOnSuccessListener {
                                cont.resume(Result.success(Unit))
                            }
                            .addOnFailureListener { ex ->
                                cont.resume(Result.failure(ex))
                            }
                    }
                    .addOnFailureListener { ex ->
                        cont.resume(Result.failure(ex))
                    }

                cont.invokeOnCancellation { }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun cargarPartida(): Result<Pair<String, Partida>> {
        return try {
            suspendCancellableCoroutine { cont ->
                userCol()
                    .limit(1)
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        val doc = querySnapshot.documents.firstOrNull()
                        if (doc != null) {
                            val partida = doc.toObject(Partida::class.java)
                            if (partida != null) {
                                val partidaMutable = partida.copy(
                                    encuentrosRutas = partida.encuentrosRutas.toMutableList(),
                                    equipo = partida.equipo.toMutableList(),
                                    pc = partida.pc.toMutableList(),
                                    muertos = partida.muertos.toMutableList(),
                                    medallas = partida.medallas.toMutableList(),
                                    resultadosCombates = partida.resultadosCombates.toMutableList()
                                )
                                cont.resume(Result.success(Pair(doc.id, partidaMutable)))
                            } else {
                                cont.resume(Result.failure(Exception("Error al deserializar la partida")))
                            }
                        } else {
                            cont.resume(Result.failure(Exception("No hay partida guardada")))
                        }
                    }
                    .addOnFailureListener { ex ->
                        cont.resume(Result.failure(ex))
                    }

                cont.invokeOnCancellation { }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun actualizarEncuentrosYEquipo(
        docId: String,
        encuentros: List<EncuentroRuta>,
        equipo: List<PokemonCapturado>,
        pc: List<PokemonCapturado>,
        muertos: List<PokemonCapturado>,
        vidas: Int
    ): Result<Unit> {
        return try {
            suspendCancellableCoroutine { cont ->
                userCol().document(docId)
                    .update(
                        mapOf(
                            "encuentrosRutas" to encuentros,
                            "equipo" to equipo,
                            "pc" to pc,
                            "muertos" to muertos,
                            "vidas" to vidas
                        )
                    )
                    .addOnSuccessListener { cont.resume(Result.success(Unit)) }
                    .addOnFailureListener { ex -> cont.resume(Result.failure(ex)) }

                cont.invokeOnCancellation { }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun actualizarResultadosCombates(
        docId: String,
        resultados: List<ResultadoCombate>,
        equipo: List<PokemonCapturado>,
        muertos: List<PokemonCapturado>,
        vidas: Int
    ): Result<Unit> {
        return try {
            suspendCancellableCoroutine { cont ->
                userCol().document(docId)
                    .update(
                        mapOf(
                            "resultadosCombates" to resultados,
                            "equipo" to equipo,
                            "muertos" to muertos,
                            "vidas" to vidas
                        )
                    )
                    .addOnSuccessListener { cont.resume(Result.success(Unit)) }
                    .addOnFailureListener { ex -> cont.resume(Result.failure(ex)) }

                cont.invokeOnCancellation { }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun marcarFinDeLocke(docId: String): Result<Unit> {
        return try {
            suspendCancellableCoroutine { cont ->
                userCol().document(docId)
                    .update("finDeLocke", true)
                    .addOnSuccessListener { cont.resume(Result.success(Unit)) }
                    .addOnFailureListener { ex -> cont.resume(Result.failure(ex)) }
                cont.invokeOnCancellation { }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun incrementarConsultasIA(docId: String): Result<Unit> {
        return try {
            suspendCancellableCoroutine { cont ->
                userCol().document(docId)
                    .update("consultasIA", FieldValue.increment(1))
                    .addOnSuccessListener { cont.resume(Result.success(Unit)) }
                    .addOnFailureListener { ex -> cont.resume(Result.failure(ex)) }
                cont.invokeOnCancellation { }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
