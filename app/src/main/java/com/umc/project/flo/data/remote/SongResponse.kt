package com.umc.project.flo.data.remote

data class SongResponse (
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: FloChartResult
)

data class FloChartResult(
    val songs: ArrayList<FloChartSongs>
)

data class FloChartSongs(
    val songIdx: Int,
    val albumIdx: Int,
    val singer: String,
    val title: String,
    val coverImgUrl: String
)