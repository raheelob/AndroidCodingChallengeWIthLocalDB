package com.example.maydcodingchallenge.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maydcodingchallenge.R
import com.example.maydcodingchallenge.data.api.RemoteData
import com.example.maydcodingchallenge.data.local.ShortLinkEntity
import com.example.maydcodingchallenge.data.repository.ShortLinkRoomRepository
import com.example.maydcodingchallenge.data.response.model.ErrorData
import com.example.maydcodingchallenge.view.usecase.ShortenURLUseCase
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: ShortenURLUseCase,
    private val shortLinkRoomRepository: ShortLinkRoomRepository
) : ViewModel() {

    private val tasksEventChannel = Channel<URLsEvent>()
    val tasksEvent = tasksEventChannel.receiveAsFlow()

    fun submitLinkURL(urlTIL: TextInputEditText) {
        if (urlTIL.text!!.isEmpty()) {
            urlTIL.error = urlTIL.context.getString(R.string.please_add_a_link_here)
            return
        }
        getShortenURL(urlTIL.text!!.trim().toString())
    }

    private fun getShortenURL(longUrl: String) = viewModelScope.launch {
        tasksEventChannel.send(URLsEvent.Loading)
        useCase.execute(ShortenURLUseCase.Params(longUrl)).collect { res ->
            when (res) {
                RemoteData.Loading -> {
                    sendLoadingEvent()
                }

                is RemoteData.Success -> {
                    res.value.let {
                        val entity = ShortLinkEntity(
                            code = it.result?.code.toString(),
                            full_link = it.result?.fullShareLink.toString(),
                            shortLink = it.result?.shortLink.toString(),
                            isCopied = false
                        )
                        insertItemToHistory(entity)
                    }
                }

                is RemoteData.RemoteErrorByNetwork -> {
                    sendRemoteErrorByNetworkEvent()
                }

                is RemoteData.Error -> {
                    res.error?.let { sendErrorEvent(it) }
                }
            }
        }
    }

    internal fun getHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            val history = shortLinkRoomRepository.getAllShortLinks()
            getHistoryEvent(history)
        }
    }

    private fun insertItemToHistory(event: ShortLinkEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            shortLinkRoomRepository.insertShortLink(event)
            getHistory()
        }

    fun deleteItemFromHistory(item: ShortLinkEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            sendLoadingEvent()
            deleteItem(item)
        }
    }

    private suspend fun deleteItem(item: ShortLinkEntity) {
        shortLinkRoomRepository.deleteShortLinkByCode(item.code)
        getHistoryAfterDelete()
    }

    private fun getHistoryAfterDelete() {
        viewModelScope.launch(Dispatchers.IO) {
            val history = shortLinkRoomRepository.getAllShortLinks()
            delay(1000)
            getHistoryAfterDeleteEvent(history)
        }
    }

    private suspend fun getHistoryAfterDeleteEvent(list: List<ShortLinkEntity>) {
        tasksEventChannel.send(URLsEvent.URLDeleted(list))
    }

    suspend fun getHistoryEvent(list: List<ShortLinkEntity>) {
        tasksEventChannel.send(URLsEvent.GetHistory(list))
    }

    private suspend fun sendLoadingEvent() {
        tasksEventChannel.send(URLsEvent.Loading)
    }

    private suspend fun sendRemoteErrorByNetworkEvent() {
        tasksEventChannel.send(URLsEvent.RemoteErrorByNetwork)
    }

    suspend fun sendErrorEvent(errorData: ErrorData) {
        tasksEventChannel.send(URLsEvent.Error(errorData))
    }

    sealed class URLsEvent {
        data class GetHistory(val list: List<ShortLinkEntity>) : URLsEvent()
        data class URLDeleted(val list: List<ShortLinkEntity>) : URLsEvent()
        class Error(val errorData: ErrorData) : URLsEvent()
        object RemoteErrorByNetwork : URLsEvent()
        object Loading : URLsEvent()
    }

}