package com.example.professionalnuzlocker.data.model

/** Petición al chat de IA con el texto del usuario y el ID de la partida activa para contextualizar la respuesta. */
data class ChatPregunta(val message: String, val partida_id: String)
