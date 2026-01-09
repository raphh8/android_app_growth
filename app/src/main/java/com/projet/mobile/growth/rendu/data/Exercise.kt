package com.projet.mobile.growth.rendu.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Exercise(
    @SerialName("name")
    val name: String,
    @SerialName("gifUrl")
    val gif: String,
    @SerialName("repNumber")
    val repNumber: Int? = null,
    @SerialName("setNumber")
    val setNumber: Int? = null,
    @SerialName("weight")
    val weight: Int? = null
)