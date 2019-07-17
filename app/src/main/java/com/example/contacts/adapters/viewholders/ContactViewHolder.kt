package com.example.contacts.adapters.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.contacts.R
import com.example.contacts.data.models.Contact
import kotlinx.android.synthetic.main.item_contact.view.*
import org.w3c.dom.Text

class ContactViewHolder(val v: View): RecyclerView.ViewHolder(v) {

    private var nameTV: TextView? = null
    private var phoneTV: TextView? = null
    private var avatarIV: ImageView? = null

    init {
        nameTV = v.findViewById(R.id.nameTV)
        phoneTV = v.findViewById(R.id.phoneTV)
        avatarIV = v.findViewById(R.id.avatarIV)
    }

    fun bind(contact: Contact) {
        nameTV?.text = contact.firstName
        phoneTV?.text = contact.phone
        //TODO: change to our api call
//        Glide.with(v.context)
//            .load(contact.firstImage).into(avatarIV)
    }

}