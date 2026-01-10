package com.projet.mobile.growth.rendu.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ExerciseWebService {
    @GET("/api/v1/exercises/search")
    suspend fun fetchSearchResults(@Query("q") query: String ): Response<SearchResponse>

    @GET("/api/v1/exercises/{exerciseId}")
    suspend fun fetchExerciseDetails(@Path("exerciseId") exerciseId: String ): Response<SearchIdResponse>
}

