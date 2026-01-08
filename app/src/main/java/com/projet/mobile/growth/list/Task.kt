package com.projet.mobile.growth.list
import kotlinx.serialization.SerialName
import java.io.Serializable

@kotlinx.serialization.Serializable
data class Task(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    var title: String,
    @SerialName("description")
    var description: String? = "You can add your own description!"
) : Serializable
