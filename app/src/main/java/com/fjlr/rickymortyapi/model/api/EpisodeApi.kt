package com.fjlr.rickymortyapi.model.api

import com.fjlr.rickymortyapi.model.data.Api
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Class that makes requests to the API (Episode)
 */
interface EpisodeApi {
    @GET("episode/?episode=E01")
    suspend fun getTemporadas() : Api

    @GET("episode")
    suspend fun getEpisodesForSeason(@Query("episode") season: String): Api
}