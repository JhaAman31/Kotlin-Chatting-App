package com.sca.kotlinchattingapp.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sca.kotlinchattingapp.Models.Repositories.ChatsRepository
import com.sca.kotlinchattingapp.Models.UserModel

class ChatViewModel : ViewModel() {
    private val userRepository = ChatsRepository()

    private val _users = MutableLiveData<List<UserModel>>()
    val users: LiveData<List<UserModel>> = _users

    fun fetchAllUsers() {
        userRepository.fetchAllUsers(
            onSuccess = { users -> _users.value = users },
            onFailure = { _users.value = emptyList() }
        )
    }
}

