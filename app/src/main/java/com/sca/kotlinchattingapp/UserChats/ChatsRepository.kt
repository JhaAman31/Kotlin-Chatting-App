package com.sca.kotlinchattingapp.UserChats

import com.sca.kotlinchattingapp.Utils.FirebaseUtils


class ChatsRepository {

    fun saveUser(user: UserModel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
       FirebaseUtils.usersReference(user.id).set(user)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun fetchAllUsers(onSuccess: (List<UserModel>) -> Unit, onFailure: (Exception) -> Unit) {
        FirebaseUtils.userCollection().get()
            .addOnSuccessListener { snapshot ->
                val users = snapshot.toObjects(UserModel::class.java)
                onSuccess(users)
            }
            .addOnFailureListener { onFailure(it) }
    }
}
