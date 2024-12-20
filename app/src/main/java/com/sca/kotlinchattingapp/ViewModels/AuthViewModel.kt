package com.sca.kotlinchattingapp.ViewModels


import androidx.lifecycle.ViewModel
import com.sca.kotlinchattingapp.Models.Repositories.AuthRepository
import com.sca.kotlinchattingapp.Models.UserModel

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    fun register(
        name: String,
        email: String,
        phone: String,
        password: String,
        about: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        repository.registerUser(
            name,
            email,
            phone,
            password,
            onSuccess, onFailure
        )
    }

    fun login(
        email: String,
        password: String,
        onSuccess: (UserModel) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        repository.loginUser(
            email, password, onSuccess, onFailure
        )
    }


}