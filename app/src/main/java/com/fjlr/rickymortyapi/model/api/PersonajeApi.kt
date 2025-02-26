package com.fjlr.rickymortyapi.model.api

import com.fjlr.rickymortyapi.model.data.Characters
import com.fjlr.rickymortyapi.model.data.Episode
import retrofit2.http.GET
import retrofit2.http.Path

interface PersonajeApi {
    @GET("episode/{id}")
    suspend fun getPersonajeForId(@Path ("id") id: Int) : Episode

    @GET("character/{id}")
    suspend fun getDetailPersonaje(@Path ("id") id: Int) : Characters
}