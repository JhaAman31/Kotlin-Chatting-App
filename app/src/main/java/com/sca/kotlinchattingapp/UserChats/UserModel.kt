package com.sca.kotlinchattingapp.UserChats

import com.google.firebase.Timestamp

data class UserModel(
    val name: String="",
    val id: String="",
    var profilePic: String="",
    val email: String="",
    val password: String="",
    val phone: String="",
    var about:String="",
    val timestamp: Timestamp= Timestamp.now(),
    val emailVerified: Boolean = false
)
