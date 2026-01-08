package com.projet.mobile.growth.rendu.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.projet.mobile.growth.data.UserWebService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object ExerciseAPI {
    val exerciseWebService : ExerciseWebService by lazy {
        retrofit.create(ExerciseWebService::class.java)
    }
    private val retrofit by lazy {
        val client = OkHttpClient()

        val jsonSerializer = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            encodeDefaults = true
        }

        Retrofit.Builder()
            .baseUrl("https://www.exercisedb.dev")
            .client(client)
            .addConverterFactory(jsonSerializer.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}