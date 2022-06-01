package com.umc.project.flo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SongTable")
data class Song(
    val title : String = "",            //제목
    val singer : String = "",           //가수
    var second : Int = 0,               //현재 재생시간
    var playTime : Int = 0,             //재생시간
    var isPlaying : Boolean = false,    //재생되고 있는지
    var music: String = "",
    var coverImg: Int? = null,
    var isLike: Boolean = false
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
