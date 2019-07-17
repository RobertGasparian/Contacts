package com.example.contacts.network

import com.example.contacts.data.models.Contact
import com.example.contacts.network.bodies.ContactBody
import com.example.contacts.network.responses.ImageUploadResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("rest/contacts")
    fun getContacts() : Call<List<Contact>>

    @GET("rest/contacts/{contactId}")
    fun getContact(@Path ("contactId") contactId: String): Call<Contact>

    @POST("rest/contacts")
    fun createContact(@Body contact: ContactBody) : Call<Contact>

    @GET("media/{id}")
    fun getImage(@Path("id") id: String) : Call<ResponseBody>

    @Multipart
    @POST("media")
    fun uploadImage(@Part("file") file: MultipartBody.Part ) : Call<ImageUploadResponse>
}