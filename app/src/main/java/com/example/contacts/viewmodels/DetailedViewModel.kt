package com.example.contacts.viewmodels

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.contacts.data.Repository
import com.example.contacts.data.models.Contact
import com.example.contacts.network.Resource

class DetailedViewModel(application: Application) : AndroidViewModel(application) {

    private var contactLD: LiveData<Resource<Contact>>? = null
    private var imageIdLD = MutableLiveData<String>()
    private var imageLD = Transformations.switchMap(imageIdLD) { Repository.getImage(it) }

    fun getContact(contactId: String) : LiveData<Resource<Contact>> {
        contactLD = Repository.getContactById(contactId)
        return contactLD!!
    }

    fun setImageID(imageId: String) {
        imageIdLD.value = imageId
    }

    fun getImage() : LiveData<Resource<Bitmap>> {
        return imageLD!!
    }
}