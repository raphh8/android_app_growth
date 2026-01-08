package com.projet.mobile.growth.rendu.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExerciseWebService {
    @GET("/api/v1/exercises/search")
    suspend fun fetchSearchResults(@Query("q") query: String ): Response<SearchResponse>
}
