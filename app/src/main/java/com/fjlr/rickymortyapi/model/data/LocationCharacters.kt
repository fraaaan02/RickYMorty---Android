package com.fjlr.rickymortyapi.model.data

import com.google.gson.annotations.SerializedName

/**
 * class that collects data from LocationCharacters
 */
data class LocationCharacters(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)