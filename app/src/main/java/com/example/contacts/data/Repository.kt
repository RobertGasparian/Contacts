package com.example.contacts.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.LiveData

import com.example.contacts.data.models.Contact
import com.example.contacts.network.ApiService
import com.example.contacts.network.NetworkBoundResource
import com.example.contacts.network.Resource
import com.example.contacts.network.bodies.ContactBody
import com.example.contacts.network.responses.ImageUploadResponse
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import java.io.File


object Repository {
    var apiService: ApiService? = null

    fun init(apiService: ApiService) {
        this.apiService = apiService
    }

    fun getContactList() : LiveData<Resource<List<Contact>>> {
       return (object : NetworkBoundResource<List<Contact>>() {
           override fun createCall() {
               apiService?.let {
                   it.getContacts().enqueue(object : Callback<List<Contact>>{
                       override fun onResponse(call: Call<List<Contact>>, response: Response<List<Contact>>) {
                            if (response.isSuccessful) {
                                setValue(Resource.Success(data = response.body() ?: emptyList()))
                            } else {
                                setValue(Resource.Error(data = null, message = response.message()))
                            }
                       }

                       override fun onFailure(call: Call<List<Contact>>, t: Throwable) {
                           setValue(Resource.Error(data = null, message = t.message ?: "unknown error"))
                       }
                   })
               }
           }
       }).asLiveData()
    }

    fun getContactById(contactId: String) : LiveData<Resource<Contact>> {
        return (object : NetworkBoundResource<Contact>() {
            override fun createCall() {
                apiService?.let {
                    it.getContact(contactId).enqueue(object : Callback<Contact> {
                        override fun onResponse(call: Call<Contact>, response: Response<Contact>) {
                            if (response.isSuccessful) {
                                setValue(Resource.Success(data = response.body() ?: Contact()))
                            } else {
                                setValue(Resource.Error(data = null, message = response.message()))
                            }
                        }

                        override fun onFailure(call: Call<Contact>, t: Throwable) {
                            setValue(Resource.Error(data = null, message = t.message ?: "unknown error"))
                        }
                    })
                }
            }

        }).asLiveData()
    }

    fun createContact(contact: ContactBody) : LiveData<Resource<Contact>> {
        return (object : NetworkBoundResource<Contact>() {
            override fun createCall() {
                apiService?.let {
                    it.createContact(contact).enqueue(object : Callback<Contact> {
                        override fun onResponse(call: Call<Contact>, response: Response<Contact>) {
                            if (response.isSuccessful) {
                                setValue(Resource.Success(data = response.body() ?: Contact()))
                            } else {
                                setValue(Resource.Error(data = null, message = response.message()))
                            }
                        }

                        override fun onFailure(call: Call<Contact>, t: Throwable) {
                            setValue(Resource.Error(data = null, message = t.message ?: "unknown error"))
                        }
                    })
                }
            }

        }).asLiveData()
    }

    fun uploadImage(filePath: Uri) : LiveData<Resource<ImageUploadResponse>> {
        val file = File(filePath.path)
        val fileBody = RequestBody.create(MediaType.parse("image/*"), file)
        val part = MultipartBody.Part.createFormData("upload", file.name, fileBody)
        return (object : NetworkBoundResource<ImageUploadResponse>() {
            override fun createCall() {
                apiService?.let {
                    it.uploadImage(part).enqueue(object : Callback<ImageUploadResponse> {
                        override fun onResponse(
                            call: Call<ImageUploadResponse>,
                            response: Response<ImageUploadResponse>
                        ) {
                            if (response.isSuccessful) {
                                setValue(Resource.Success(data = response.body() ?: ImageUploadResponse()))
                            } else {
                                setValue(Resource.Error(data = null, message = response.message()))
                            }
                        }

                        override fun onFailure(call: Call<ImageUploadResponse>, t: Throwable) {
                            setValue(Resource.Error(data = null, message = t.message ?: "unknown error"))
                        }
                    })
                }
            }
        }).asLiveData()
    }

    fun getImage(id: String) : LiveData<Resource<Bitmap>> {
        return (object : NetworkBoundResource<Bitmap>() {
            override fun createCall() {
                apiService?.let {
                    it.getImage(id).enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            if (response.isSuccessful) {
                                val inStream = response.body()?.byteStream()
                                val bitmap = BitmapFactory.decodeStream(inStream)
                                setValue(Resource.Success(data = bitmap))
                            } else {
                                setValue(Resource.Error(data = null, message = response.message()))
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            setValue(Resource.Error(data = null, message = t.message ?: "unknown error"))
                        }
                    })
                }
            }
        }).asLiveData()
    }
}