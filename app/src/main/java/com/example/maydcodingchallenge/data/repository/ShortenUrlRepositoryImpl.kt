package com.example.maydcodingchallenge.data.repository

import com.example.example.ShortenUrlResponse
import com.example.maydcodingchallenge.data.api.APIsCollection
import com.example.maydcodingchallenge.data.api.RemoteData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ShortenUrlRepositoryImpl @Inject constructor(
    private val listItemsApi: APIsCollection
) : ShortenUrlRepository {

    override fun getShortenURL(longURL: String): Flow<RemoteData<ShortenUrlResponse>> =
        flow {
            val response = listItemsApi.getShortenURL(url = longURL)
            emit(RemoteData.Success(response))
        }.flowOn(Dispatchers.IO)

}