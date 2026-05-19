package com.example.professionalnuzlocker.data.remote

import com.example.professionalnuzlocker.data.model.ChatPregunta
import com.example.professionalnuzlocker.data.model.ChatRespuesta
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

/**
 * Interfaz Retrofit para comunicarse con el backend de la IA del Nuzlocker.
 *
 * El único endpoint es `POST /chat`, que recibe un [ChatPregunta] con el mensaje
 * del jugador y el ID de la partida, y devuelve una [ChatRespuesta].
 * La instancia lista para usar se obtiene desde [ChatApi.service].
 */
interface ChatApiService {
    /** Envía un mensaje al endpoint /chat del backend con el token de autenticación y devuelve la respuesta de la IA. */
    @POST("chat")
    suspend fun enviarMensaje(
        @Header("Authorization") token: String,
        @Body request: ChatPregunta
    ): ChatRespuesta
}

/**
 * Singleton que provee la instancia de [ChatApiService] lista para usar.
 *
 * Configura un [OkHttpClient] con timeouts de 60 segundos para dar margen
 * a las respuestas lentas del modelo de IA.
 */
object ChatApi {
    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()

    val service: ChatApiService = Retrofit.Builder()
        .baseUrl("https://professionalnuzlocker-be.onrender.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ChatApiService::class.java)
}
