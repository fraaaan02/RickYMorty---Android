package com.fjlr.rickymortyapi.model.data

import com.google.gson.annotations.SerializedName

/**
 * class that collects data from OriginCharacters
 */
data class OriginCharacters(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)
