package com.example.githubapi_search.data.network

import com.example.githubapi_search.util.Const.BASE_URL
import com.example.githubapi_search.util.Const.GITHUB_TOKEN
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitConfig {
    companion object {
        private fun client(): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor {
                    val original = it.request()
                    val requestBuilder = original.newBuilder()
                        .addHeader("Authorization", GITHUB_TOKEN)
                    val request = requestBuilder.build()
                    it.proceed(request)
                }
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build()

        fun create(): ApiService =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
    }
}