package com.quentin.recipegenerator.auth

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.quentin.recipegenerator.domain.model.User
import com.quentin.recipegenerator.domain.service.AuthenticationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseAuthentication: AuthenticationService {

    private val auth = Firebase.auth
    override suspend fun signIn(
        email: String, password: String
    ): User? = withContext(Dispatchers.IO){
        var user: User? = null
        try {
            auth.signInWithEmailAndPassword(email, password)
                .await().user?.apply {
                    user = User(
                        email = this.email,
                        uid = this.uid
                    )
                }
        }catch (e: Exception){
            Log.w("FirebaseAuth", e)
        }
        return@withContext user
    }

    override suspend fun signOut() = withContext(Dispatchers.IO){
        auth.signOut()
    }
}