package com.cft.cfttask.api

import com.cft.cfttask.api.data.RandomUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/api/")
    suspend fun get(@Query("results") results: Int): Response<RandomUserResponse>
}