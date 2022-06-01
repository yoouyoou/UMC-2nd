package com.umc.project.flo.ui.signup

//액티비티와 AuthService를 연결시켜주기 위한 인터페이스 <- 액티비티에서 상속받아 사용
interface SignUpView {
    //성공, 실패 사례를 처리하는 함수 작성
    fun onSignUpSuccess()
    fun onSignUpFailure(message: String)
}