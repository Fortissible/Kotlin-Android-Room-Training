package com.example.apitraining_reqresapiprofile

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MainViewModel(application: Application): ViewModel(){
    private val mUserRepository : UserRepository = UserRepository(application)

    suspend fun insert(user: UserEntity) {
        mUserRepository.insert(user)
    }
    suspend fun update(user: UserEntity) {
        mUserRepository.update(user)
    }
    suspend fun delete(user: UserEntity) {
        mUserRepository.delete(user)
    }

    fun getAllUserOffline() : LiveData<List<UserEntity>> = mUserRepository.getAllUserOffline()
}