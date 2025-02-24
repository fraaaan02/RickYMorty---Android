package com.fjlr.rickymortyapi.model.data

import com.google.gson.annotations.SerializedName

data class Episode(
    @SerializedName("name") val name: String,
    @SerializedName("episode") val episode: String,
    @SerializedName("characters") val characters: List<String>
)
