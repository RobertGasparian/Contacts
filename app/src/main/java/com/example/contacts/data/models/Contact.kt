package com.example.contacts.data.models

import com.google.gson.annotations.SerializedName

class Contact {
    @SerializedName("_id")
    var _id: String = ""
    @SerializedName("firstName")
    var firstName: String = ""
    @SerializedName("lastName")
    var lastName: String = ""
    @SerializedName("phone")
    var phone: String = ""
    @SerializedName("email")
    var email: String = ""
    @SerializedName("notes")
    var notes: String = ""
    @SerializedName("images")
    var images: List<String>? = null

    var firstImage: String? = null
        get() {
            return if (images != null && images!!.isNotEmpty()) {
                images!![0]
            } else {
                null
            }
        }

    var displayName: String = ""
    get() {return "$firstName $lastName"}
}