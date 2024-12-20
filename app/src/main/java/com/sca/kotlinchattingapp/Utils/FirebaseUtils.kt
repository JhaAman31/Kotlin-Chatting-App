package com.sca.kotlinchattingapp.Utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseUtils {
    val auth = FirebaseAuth.getInstance()
    val firestore: FirebaseFirestore
        get() = FirebaseFirestore.getInstance()

    fun usersReference(id: String): DocumentReference {
        return firestore.collection("Users").document(id)
    }

    fun userCollection():CollectionReference{
        return firestore.collection("Users")
    }

}