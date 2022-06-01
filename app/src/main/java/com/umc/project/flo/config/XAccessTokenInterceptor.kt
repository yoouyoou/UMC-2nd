package com.umc.project.flo.config

import com.umc.project.flo.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.umc.project.flo.utils.getJwt
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

//api를 요청할 때마다 자동적으로 sharedPreference에 저장되어있는
//jwt를 가져와서 그것을 헤더에 넣어주는 작업
class XAccessTokenInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val jwtToken: String? = getJwt()

        /*
            jwt가 없으면 안넣고, 있다면 X_ACCESS_TOKEN라는 키값으로 레트로핏에 자동적으로 담겨서 api요청
            ->레트로핏에서 어노테이션 헤더를 사용해서 일일이 넣어 줄 필요없고 이 설정만 있으면
              헤더가 있는지 없는지 판단해서 넣어주기 때문에 헤더에 jwt를 넣어주는 작업은 따로 해줄 필요 없음
        */

        jwtToken?.let{
            builder.addHeader(X_ACCESS_TOKEN, jwtToken)
        }

        return chain.proceed(builder.build())
    }

}