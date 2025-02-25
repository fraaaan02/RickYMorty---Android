package com.fjlr.rickymortyapi.model.api

import com.fjlr.rickymortyapi.model.data.Characters
import retrofit2.http.GET

interface PersonajeApi {
    @GET("character")
    suspend fun getPersonajes(): Characters
}