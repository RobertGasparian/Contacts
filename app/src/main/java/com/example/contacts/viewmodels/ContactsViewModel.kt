package com.example.contacts.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.contacts.data.Repository
import com.example.contacts.data.models.Contact
import com.example.contacts.network.Resource

class ContactsViewModel(application: Application) : AndroidViewModel(application) {
    private var contactsLD: LiveData<Resource<List<Contact>>>? = null

    fun getContacts(): LiveData<Resource<List<Contact>>> {
        contactsLD = Repository.getContactList()
        return contactsLD!!
    }
}