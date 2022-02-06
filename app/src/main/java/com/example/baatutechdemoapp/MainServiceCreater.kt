package com.example.baatutechdemoapp

import android.app.Application
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object MainServiceCreater {

    private fun <S> createService(serviceClass: Class<S>, application: Application): S {

        val httpClientBuilder = OkHttpClient.Builder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClientBuilder.addInterceptor(httpLoggingInterceptor).addInterceptor(MainInterceptor())

        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .retryOnConnectionFailure(true)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.API)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(serviceClass)
    }

    fun createService(application: Application): Api {
        return createService<Api>(Api::class.java, application)
    }
}