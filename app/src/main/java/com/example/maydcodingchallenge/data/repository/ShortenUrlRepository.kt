package com.example.maydcodingchallenge.data.repository

import com.example.example.ShortenUrlResponse
import com.example.maydcodingchallenge.data.api.RemoteData
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface ShortenUrlRepository {
     fun getShortenURL(longURL: String): Flow<RemoteData<ShortenUrlResponse>>
}