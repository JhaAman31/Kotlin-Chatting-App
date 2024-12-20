package com.sca.kotlinchattingapp.Models.Repositories

import com.sca.kotlinchattingapp.Models.MessageModels
import com.sca.kotlinchattingapp.Utils.FirebaseUtils

class MessageRepository {
    private val chatsCollection = FirebaseUtils.firestore.collection("chats")

    fun sendMessage(chatRoomId: String, message: MessageModels) {
        chatsCollection.document(chatRoomId).collection("messages").add(message)
    }

    fun getMessages(chatRoomId: String, onSuccess: (List<MessageModels>) -> Unit) {
        chatsCollection.document(chatRoomId).collection("messages")
            .orderBy("lastMsgTime")
            .addSnapshotListener { snapshot, e ->
                if (e == null && snapshot != null) {
                    val messages = snapshot.toObjects(MessageModels::class.java)
                    onSuccess(messages)
                }
            }
    }
}