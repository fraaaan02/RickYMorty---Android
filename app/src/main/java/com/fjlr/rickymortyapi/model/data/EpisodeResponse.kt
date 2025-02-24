package com.fjlr.rickymortyapi.model.data

import com.google.gson.annotations.SerializedName

data class EpisodeResponse(
    @SerializedName("results") val results: List<Episode>
)