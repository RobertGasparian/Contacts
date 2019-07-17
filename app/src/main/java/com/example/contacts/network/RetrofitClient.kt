package com.example.contacts.network

import com.example.contacts.app.Constants
import com.example.contacts.network.interceptors.ApiKeyInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    var retrofit: Retrofit? = null
    var client: OkHttpClient? = null
    fun getApiService() : ApiService {
        if (client == null) {
            client = OkHttpClient.Builder()
                .addInterceptor(ApiKeyInterceptor())
                .build()
        }
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .client(client!!)
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!.create(ApiService::class.java)
    }
}