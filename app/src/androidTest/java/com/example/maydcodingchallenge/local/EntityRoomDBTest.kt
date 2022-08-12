package com.example.maydcodingchallenge.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.maydcodingchallenge.data.local.ShortLinkDao
import com.example.maydcodingchallenge.data.local.ShortLinkDatabase
import com.example.maydcodingchallenge.data.local.ShortLinkEntity
import com.example.maydcodingchallenge.data.repository.ShortLinkRoomRepository
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class EntityRoomDBTest {

    private lateinit var shortLinkRoomRepository: ShortLinkRoomRepository
    private lateinit var shortLinkDao: ShortLinkDao
    private lateinit var db: ShortLinkDatabase
    private lateinit var context: Context

    @Before
    fun createDb() {
        context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, ShortLinkDatabase::class.java).build()
        shortLinkDao = db.ShortLinkDao()
        shortLinkRoomRepository = ShortLinkRoomRepository(shortLinkDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() = runBlocking {
        val event = ShortLinkEntity(
            code = "q8fu3t",
            full_link = "https://shrtco.de/q8fu3t",
            shortLink = "shrtco.de/q8fu3t",
            isCopied = false
        )
        shortLinkRoomRepository.insertShortLink(event)
        val listOfCodes = shortLinkDao.findEntityByCode(event.code)
        assertThat(listOfCodes[0].code, equalTo("q8fu3t"))
    }

    @Test
    @Throws(Exception::class)
    fun deleteAnEntityFromTheList() = runBlocking {
        val event = ShortLinkEntity(
            code = "q8fu3t",
            full_link = "https://shrtco.de/q8fu3t",
            shortLink = "shrtco.de/q8fu3t",
            isCopied = false
        )
        shortLinkRoomRepository.insertShortLink(event)
        shortLinkDao.deleteShortLink(event)
        val listOfCodes = shortLinkDao.findEntityByCode(event.code)
        assertTrue(listOfCodes.isEmpty())
    }

}





