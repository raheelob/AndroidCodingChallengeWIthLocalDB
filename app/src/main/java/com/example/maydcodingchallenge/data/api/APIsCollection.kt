package com.example.maydcodingchallenge.data.api

import com.example.example.ShortenUrlResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIsCollection {
    @GET("shorten")
    suspend fun getShortenURL(@Query("url")  url : String
    ): ShortenUrlResponse
}