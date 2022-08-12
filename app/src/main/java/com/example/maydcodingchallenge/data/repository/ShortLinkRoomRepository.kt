package com.example.maydcodingchallenge.data.repository

import androidx.lifecycle.LiveData
import com.example.maydcodingchallenge.data.local.ShortLinkDao
import com.example.maydcodingchallenge.data.local.ShortLinkEntity
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class ShortLinkRoomRepository @Inject constructor(private val dao: ShortLinkDao) : ShortenLocalRepository {
    override fun getAllShortLinks(): List<ShortLinkEntity> = dao.getAllShortLinks()
    override suspend fun insertShortLink(shortLinkEntity: ShortLinkEntity) = dao.insertShortLink(shortLinkEntity)
    override suspend fun deleteShortLinkByCode(code: String) = dao.deleteLinkByCode(code)
}