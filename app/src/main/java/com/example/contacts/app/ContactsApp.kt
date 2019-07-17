package com.example.contacts.app

import android.app.Application
import com.example.contacts.data.Repository
import com.example.contacts.network.RetrofitClient

class ContactsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Repository.init(RetrofitClient.getApiService())
    }
}