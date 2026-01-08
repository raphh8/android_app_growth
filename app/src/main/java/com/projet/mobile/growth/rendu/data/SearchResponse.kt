package com.projet.mobile.growth.rendu.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    @SerialName("data")
    val data: List<Exercise>
)
