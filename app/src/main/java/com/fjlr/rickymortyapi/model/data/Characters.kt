package com.fjlr.rickymortyapi.model.data

import com.google.gson.annotations.SerializedName

/**
 * class that collects data from Characters
 */
data class Characters (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String,
    @SerializedName("type") val type: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("origin") val origin: OriginCharacters,
    @SerializedName("location") val location: LocationCharacters,
    @SerializedName("image") val image: String,
    @SerializedName("created") val created: String
)