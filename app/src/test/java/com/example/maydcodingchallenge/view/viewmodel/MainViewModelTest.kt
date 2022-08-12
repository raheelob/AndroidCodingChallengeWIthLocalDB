package com.example.maydcodingchallenge.view.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.maydcodingchallenge.CoroutineTestRule
import com.example.maydcodingchallenge.data.api.APIsCollection
import com.example.maydcodingchallenge.data.local.ShortLinkDao
import com.example.maydcodingchallenge.data.local.ShortLinkDatabase
import com.example.maydcodingchallenge.data.local.ShortLinkEntity
import com.example.maydcodingchallenge.data.repository.ShortLinkRoomRepository
import com.example.maydcodingchallenge.data.repository.ShortenUrlRepository
import com.example.maydcodingchallenge.data.repository.ShortenUrlRepositoryImpl
import com.example.maydcodingchallenge.data.response.model.ErrorData
import com.example.maydcodingchallenge.view.usecase.ShortenURLUseCase
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class MainViewModelTest : TestCase() {

    private inline fun <reified T> mock(): T = Mockito.mock(T::class.java)
    private val apiService = mock<APIsCollection>()
    private val shortenURLRepository: ShortenUrlRepository = ShortenUrlRepositoryImpl(apiService)
    private val useCase: ShortenURLUseCase = ShortenURLUseCase(shortenURLRepository)
    private lateinit var shortLinkRoomRepository: ShortLinkRoomRepository
    private lateinit var shortLinkDao: ShortLinkDao
    private lateinit var db: ShortLinkDatabase
    private lateinit var mainViewModel: MainViewModel
    private lateinit var context: Context

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Before
    public override fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, ShortLinkDatabase::class.java).build()
        shortLinkDao = db.ShortLinkDao()
        shortLinkRoomRepository = ShortLinkRoomRepository(shortLinkDao)
        mainViewModel = MainViewModel(useCase, shortLinkRoomRepository)
    }

    @Test
    fun testInsertionEntityEvent() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) {
            mainViewModel.tasksEvent.collect {
                when (it) {
                    is MainViewModel.URLsEvent.GetHistory -> {
                        assertTrue(it.list.isNotEmpty())
                    }
                    else ->
                        assertFalse(true)
                }
            }
        }
        mainViewModel.getHistoryEvent(getEntityList())
        collectJob.cancel()
    }

    @Test
    fun testErrorEvent() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) {
            mainViewModel.tasksEvent.collect {
                when (it) {
                    is MainViewModel.URLsEvent.Error -> {
                        it.errorData.error?.let { errorStr -> assertTrue(errorStr.isNotEmpty()) }
                    }
                    else ->
                        assertFalse(true)
                }
            }
        }
        mainViewModel.sendErrorEvent(
            ErrorData(
                ok = true,
                errorCode = 1,
                error = "Enter a valid URL"
            )
        )
        collectJob.cancel()
    }

    private fun getEntityList(): List<ShortLinkEntity> {
        return object : ArrayList<ShortLinkEntity>() {
            init {
                add(
                    ShortLinkEntity(
                        code = "q8fu3t",
                        full_link = "https://shrtco.de/q8fu3t",
                        shortLink = "shrtco.de/q8fu3t",
                        isCopied = false
                    )
                )
                add(
                    ShortLinkEntity(
                        code = "q8fu3t",
                        full_link = "https://shrtco.de/q8fu3t",
                        shortLink = "shrtco.de/q8fu3t",
                        isCopied = false
                    )
                )
            }
        }
    }

    @After
    @Throws(Exception::class)
    fun tearDownClass() {
        Mockito.framework().clearInlineMocks()
    }

    @After
    fun closeDb() {
        db.close()
    }
}