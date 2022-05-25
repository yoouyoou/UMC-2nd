package com.umc.project.flo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "UserTable")
data class User(
    //서버에 request보낼때 변수명을 서버변수명과 어노테이션으로 일치시켜줌
    @SerializedName(value = "email") var email: String,
    @SerializedName(value = "password") var password: String,
    @SerializedName(value = "name") var name: String
){
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}
