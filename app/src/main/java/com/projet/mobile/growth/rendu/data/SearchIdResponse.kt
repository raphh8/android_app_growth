package com.projet.mobile.growth.rendu.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchIdResponse(
    @SerialName("data")
    val data: Exercise
)