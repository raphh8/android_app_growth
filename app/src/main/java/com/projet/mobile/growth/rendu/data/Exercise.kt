package com.projet.mobile.growth.rendu.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Exercise(
    @SerialName("exerciseId")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("gifUrl")
    val gif: String,
    @SerialName("targetMuscles")
    val muscles: List<String>? = null,
    @SerialName("bodyParts")
    val bodyParts: List<String>? = null,
    @SerialName("equipments")
    val equipments: List<String>? = null,
    @SerialName("secondaryMuscles")
    val secondaryMuscles: List<String>? = null,
    @SerialName("instructions")
    val instructions: List<String>? = null,
    var repNumber: Int? = null,
    var setNumber: Int? = null,
    var weight: Int? = null
)