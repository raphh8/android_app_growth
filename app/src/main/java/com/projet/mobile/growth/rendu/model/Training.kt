package com.projet.mobile.growth.rendu.model

import com.projet.mobile.growth.rendu.data.Exercise
import kotlinx.serialization.SerialName
import java.io.Serializable

@kotlinx.serialization.Serializable
data class Training(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    var title: String,
    @SerialName("timeEstimation")
    var timeEstimation: String,
    @SerialName("exercises")
    var exercises: List<Exercise> = emptyList()
) : Serializable