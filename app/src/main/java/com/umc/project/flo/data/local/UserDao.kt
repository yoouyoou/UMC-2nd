package com.umc.project.flo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.umc.project.flo.data.entities.User

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM UserTable")
    fun getUsers(): List<User>

    @Query("SELECT * FROM UserTable WHERE email = :email AND password = :password")
    fun getUser(email:String, password:String): User?   //정보가 있을수도 없을수도 있으므로 ?

}