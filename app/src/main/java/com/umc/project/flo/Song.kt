package com.umc.project.flo

//제목, 가수, 재생시간, 현재 재생시간, 재싱
data class Song(
    val title : String = "",            //제목
    val singer : String = "",           //가수
    var second : Int = 0,               //현재 재생시간
    var playTime : Int = 0,             //재생시간
    var isPlaying : Boolean = false,    //재생되고 있는지
    var music: String = ""
)
