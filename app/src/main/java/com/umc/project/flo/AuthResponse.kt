package com.umc.project.flo

import com.google.gson.annotations.SerializedName

//서버에서 로그인 요청 후 받아올 응답 데이터 클래스
data class AuthResponse(
    @SerializedName(value="isSuccess") val isSuccess:Boolean,
    @SerializedName(value="code") val code: Int,
    @SerializedName(value="message") val message:String,
    @SerializedName(value="result") val result: Result?  //아래 Result객체로 응답값 받아옴
)

data class Result(
    @SerializedName(value="userIdx") var userIdx: Int,
    @SerializedName(value="jwt") var jwt: String
)

/* 유의점
회원가입과 로그인api를 같은 data class로 응답값을 받고 있음
따라서 result에 ?로 널처리를 해주어야 회원가입api를 사용했을 때 알아서 널처리가 되므로
같이 사용할 수 있음
*/