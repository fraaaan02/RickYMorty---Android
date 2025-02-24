package com.fjlr.rickymortyapi.model.api

import com.fjlr.rickymortyapi.model.data.EpisodeResponse
import retrofit2.http.GET

interface EpisodeApi {
    @GET("episode/?episode=E01")
    suspend fun getTemporadas() : EpisodeResponse
}