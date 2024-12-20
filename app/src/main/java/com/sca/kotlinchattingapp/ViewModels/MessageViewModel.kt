package com.sca.kotlinchattingapp.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sca.kotlinchattingapp.Models.MessageModels
import com.sca.kotlinchattingapp.Models.Repositories.MessageRepository

class MessageViewModel : ViewModel() {
    private val chatRepository = MessageRepository()

    private val _messages = MutableLiveData<List<MessageModels>>()
    val messages: LiveData<List<MessageModels>> = _messages

    fun sendMessage(chatRoomId: String, message: MessageModels) {
        chatRepository.sendMessage(chatRoomId, message)
    }

    fun loadMessages(chatRoomId: String) {
        chatRepository.getMessages(chatRoomId, { messages ->
            _messages.value = messages
            })
    }
}
