package com.example.contacts.viewmodels

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.contacts.data.Repository
import com.example.contacts.data.models.Contact
import com.example.contacts.network.Resource
import com.example.contacts.network.bodies.ContactBody
import com.example.contacts.network.responses.ImageUploadResponse

class AddViewModel(application: Application) : AndroidViewModel(application) {

    private var addContactLD: LiveData<Resource<Contact>>? = null
    private var imageUploadLD: LiveData<Resource<ImageUploadResponse>>? = null

    fun createContact(contact: ContactBody) : LiveData<Resource<Contact>> {
        addContactLD = Repository.createContact(contact)
        return addContactLD!!
    }

    fun imageUpload(filePath: Uri) : LiveData<Resource<ImageUploadResponse>> {
        imageUploadLD = Repository.uploadImage(filePath)
        return imageUploadLD!!
    }
}