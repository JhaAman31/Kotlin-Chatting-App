package com.sca.kotlinchattingapp.Models.Repositories

import com.google.firebase.Timestamp
import com.sca.kotlinchattingapp.Utils.FirebaseUtils
import com.sca.kotlinchattingapp.Utils.FirebaseUtils.auth
import com.sca.kotlinchattingapp.UserChats.UserModel

class AuthRepository {

    fun registerUser(
        name: String,
        email: String,
        phone: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.sendEmailVerification()?.addOnCompleteListener { emailTask ->
                        if (emailTask.isSuccessful) {
                            val userData = UserModel(
                                name,
                                user.uid,
                                "",
                                email,
                                password,
                                phone,
                               "",
                                Timestamp.now(),
                                true
                            )
                            saveUserData(userData, onSuccess, onFailure)
                        } else
                            emailTask.exception?.let { onFailure(it) }
                    }
                } else
                    task.exception?.let { onFailure(it) }
            }
    }

    fun loginUser(
        email: String,
        password: String,
        onSuccess: (UserModel) -> Unit,
        onFailure: (Exception) -> Unit
    ) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null && user.isEmailVerified) {
                        getSelfInfo(user.uid, onSuccess, onFailure)
                    } else
                        onFailure(Exception("Verify your email first..."))
                } else
                    task.exception?.let { onFailure(it) }
            }

    }
// For getting current user info
     fun getSelfInfo(
    uid: String,
    onSuccess: (UserModel) -> Unit,
    onFailure: (Exception) -> Unit
    ) {
        FirebaseUtils.usersReference(uid).get().addOnSuccessListener { docs ->
            val userData = docs.toObject(UserModel::class.java)
            if (userData != null)
                onSuccess(userData)
            else
                onFailure(Exception("User not found"))
        }
    }

     fun otherUserInfo(
         uid: String,
         onSuccess: (UserModel) -> Unit,
         onFailure: (Exception) -> Unit
    ) {
        FirebaseUtils.usersReference(uid).get().addOnSuccessListener { docs ->
            val userData = docs.toObject(UserModel::class.java)
            if (userData != null)
                onSuccess(userData)
            else
                onFailure(Exception("User not found"))
        }
    }


    private fun saveUserData(
        userModel: UserModel,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        FirebaseUtils.usersReference(userModel.id).set(userModel).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener {
            onFailure(it)
        }
    }


    fun isLoggedIn(): Boolean = auth.currentUser != null

    fun logout() = auth.signOut()
}