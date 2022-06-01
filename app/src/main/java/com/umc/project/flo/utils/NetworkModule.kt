package com.umc.project.flo

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//여러 화면에서 공통적으로 쓰이는 함수이므로 따로 파일 생성

const val BASE_URL = "https://edu-api-test.softsquared.com" //슬래시 주의

fun getRetrofit(): Retrofit {
    val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    return retrofit //생성한 레트로핏 객체 반환
}