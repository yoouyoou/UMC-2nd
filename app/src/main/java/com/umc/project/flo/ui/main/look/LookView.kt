package com.umc.project.flo.ui.main.look

import com.umc.project.flo.data.remote.FloChartResult

interface LookView {
    fun onGetSongLoading()
    fun onGetSongSuccess(code:Int, result: FloChartResult)
    fun onGetSongFailure(code:Int, message: String)
}