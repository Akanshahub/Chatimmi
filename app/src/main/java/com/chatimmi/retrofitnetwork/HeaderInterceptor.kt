package com.chatimmi.retrofitnetwork

import com.chatimmi.Chatimmi
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.*

class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request().newBuilder()
                .header("Authorization", "Bearer " + Chatimmi.authorization)
                .header("device-id", UUID.randomUUID().toString())
                .header("device-type", "2")
                .header("device-token", "aaaaaaaa")
                .header("timezone", TimeZone.getDefault().id.toString())
                .build()
        return chain.proceed(request)
    }
}
