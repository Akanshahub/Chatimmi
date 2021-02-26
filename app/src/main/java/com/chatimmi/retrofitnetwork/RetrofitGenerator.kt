package com.chatimmi.retrofitnetwork

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class RetrofitGenerator {

    companion object {

         //val API_BASE_URI = "http://70.54.131.186:3000/"
         //val API_BASE_URI = "http://chatimmi.net/"
         val API_BASE_URI = "https://chatimmi.net:80/"
       // http://70.54.131.186:3000/


        lateinit var  retrofit: Retrofit

        private val loggingInterceptor  = HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY

        }

        val httpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(HeaderInterceptor())
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()!!

        fun getRetrofitObject() : Retrofit {
            retrofit = Retrofit.Builder()
                    .baseUrl(API_BASE_URI)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)

                    .build()

            return retrofit
        }
    }
}