package com.umc.project.flo.ui.login

import com.umc.project.flo.data.remote.Result

interface LoginView {
    fun onLoginSuccess(code: Int, result: Result)
    fun onLoginFailure()
}