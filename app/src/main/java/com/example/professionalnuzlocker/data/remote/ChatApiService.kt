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

interface ChatApiService {
    @POST("chat")
    suspend fun enviarMensaje(
        @Header("Authorization") token: String,
        @Body request: ChatPregunta
    ): ChatRespuesta
}

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
