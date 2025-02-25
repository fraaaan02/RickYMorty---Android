package com.fjlr.rickymortyapi.model.data

import com.google.gson.annotations.SerializedName

/**
 * class that collects data from the API structure
 */
data class Api(
    @SerializedName("info") val info: Info,
    @SerializedName("results") val results: List<Episode>)