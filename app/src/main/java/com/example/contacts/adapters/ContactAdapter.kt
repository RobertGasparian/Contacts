package com.example.contacts.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.R
import com.example.contacts.adapters.viewholders.ContactViewHolder
import com.example.contacts.data.models.Contact

class ContactAdapter(var contacts: MutableList<Contact>?): RecyclerView.Adapter<ContactViewHolder>() {
    var itemCliclListener: ContactClickListener? = null

    fun setItemClickListener(listener: ContactClickListener) {
        itemCliclListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contacts?.size ?: 0
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        contacts?.get(position).let {
            it?.let { contact ->
                holder.bind(contact)
                holder.v.setOnClickListener {
                    itemCliclListener?.onItemClick(contact, position)
                }
            }
        }
    }

    fun updateList(contacts: MutableList<Contact>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }
}

interface ContactClickListener {
    fun onItemClick(contact: Contact, position: Int)
}