package com.example.apitraining_reqresapiprofile

import android.app.Application
import androidx.lifecycle.LiveData

class UserRepository(application: Application) {
    private val mUserDao : UserDao

    init{
        val db = UserDatabase.getDatabase(application)
        mUserDao = db.userDao()
    }

    fun getAllUserOffline(): LiveData<List<UserEntity>> = mUserDao.getAllSavedUser()

    suspend fun insert(user: UserEntity) = mUserDao.insert(user)

    suspend fun delete(user: UserEntity) = mUserDao.delete(user)

    suspend fun update(user: UserEntity) = mUserDao.update(user)
}