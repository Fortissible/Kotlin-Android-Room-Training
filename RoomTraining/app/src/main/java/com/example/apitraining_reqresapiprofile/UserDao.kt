package com.example.apitraining_reqresapiprofile

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user:UserEntity)

    @Query("UPDATE userentity SET first_name = :firstName, last_name = :lastName, email = :email WHERE id = :id")
    suspend fun update(firstName:String, lastName:String, email:String, id:Int)

    @Delete
    suspend fun delete(user:UserEntity)

    @Query("SELECT * FROM userentity ORDER BY id ASC")
    fun getAllSavedUser(): LiveData<List<UserEntity>>
}