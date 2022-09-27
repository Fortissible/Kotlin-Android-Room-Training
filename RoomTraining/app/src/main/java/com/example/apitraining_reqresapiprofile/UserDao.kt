package com.example.apitraining_reqresapiprofile

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user:UserEntity)

    @Update
    suspend fun update(user:UserEntity)

    @Delete
    suspend fun delete(user:UserEntity)

    @Query("SELECT * FROM userentity ORDER BY id ASC")
    fun getAllSavedUser(): LiveData<List<UserEntity>>
}