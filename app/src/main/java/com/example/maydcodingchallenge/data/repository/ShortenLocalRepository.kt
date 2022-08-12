package com.example.maydcodingchallenge.data.repository

import com.example.maydcodingchallenge.data.local.ShortLinkEntity

interface ShortenLocalRepository {
    fun getAllShortLinks(): List<ShortLinkEntity>
    suspend fun insertShortLink(shortLinkEntity: ShortLinkEntity) {}
    suspend fun deleteShortLinkByCode(code: String) {}
}