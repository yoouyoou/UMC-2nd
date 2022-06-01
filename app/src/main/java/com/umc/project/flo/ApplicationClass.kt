package com.umc.project.flo

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.umc.project.flo.config.XAccessTokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass: Application() {
    companion object{
        const val X_ACCESS_TOKEN: String = "X-ACCESS-TOKEN" //JWT Token Key
        const val TAG: String = "TEMPLATE-APP"  //Log, SharedPreference
        const val APP_DATABASE = "$TAG-DB"      //DB명

        const val DEV_URL: String = "https://edu-api-test.softsquared.com"  //테스트 서버 주소
        const val PROD_URL: String = "https://edu-api-test.softsquared.com" //실서버 주소
        const val BASE_URL: String = DEV_URL

        lateinit var mSharedPreferences: SharedPreferences  //전체적으로 쓰일 수 있도록
        lateinit var retrofit: Retrofit
    }

    override fun onCreate() {
        super.onCreate()

        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(30000, TimeUnit.MILLISECONDS)
            .connectTimeout(30000, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(XAccessTokenInterceptor())
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mSharedPreferences = applicationContext.getSharedPreferences(TAG, Context.MODE_PRIVATE)
    }
}