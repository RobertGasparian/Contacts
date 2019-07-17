package com.example.contacts.network.bodies

class ContactBody(
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val notes: String,
    val images: List<String>? = null) {

}