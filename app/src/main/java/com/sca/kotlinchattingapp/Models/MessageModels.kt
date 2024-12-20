package com.sca.kotlinchattingapp.Models

import com.google.firebase.Timestamp

data class MessageModels(
    val message: String = "",
    val senderId: String = "",
    val lastMsg:String = "",
    val lastMsgTime: Timestamp= Timestamp.now()
)
