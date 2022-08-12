package com.example.maydcodingchallenge.view.usecase

import com.example.example.ShortenUrlResponse
import com.example.maydcodingchallenge.data.api.UseCaseExecutor
import com.example.maydcodingchallenge.data.api.RemoteData
import com.example.maydcodingchallenge.data.repository.ShortenUrlRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShortenURLUseCase @Inject constructor(private val shortenUrlRepository: ShortenUrlRepository) :
    UseCaseExecutor<ShortenURLUseCase.Params, ShortenUrlResponse>() {

    override fun runUseCase(parameter: Params?): Flow<RemoteData<ShortenUrlResponse>> {
        return shortenUrlRepository.getShortenURL(longURL = parameter?.longUrl ?: "")
    }

    data class Params constructor(
        val longUrl : String

    ) {
        companion object {
            fun create(
                 longUrl : String
            ) = Params(longUrl)
        }
    }
}