package com.fjlr.rickymortyapi.model.data

import com.google.gson.annotations.SerializedName

/**
 * class that collects data from Episode
 */
data class Episode(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("air_date") val airDate: String,
    @SerializedName("episode") val episode: String,
    @SerializedName("characters") val characters: List<String>,
    @SerializedName("created") val created: String
)
