package com.example.contacts.network.interceptors

import com.example.contacts.app.Constants
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader(Constants.KEY_API_KEY, Constants.API_KEY)
            .build()
        return chain.proceed(request)
    }

}