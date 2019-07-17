package com.example.contacts.network.responses

import com.google.gson.annotations.SerializedName


class ImageUploadResponse {
    @SerializedName("msg")
    var message: String = ""
    @SerializedName("uploadid")
    var uploadId: String = ""
    @SerializedName("ids")
    var ids: List<String>? = null
}