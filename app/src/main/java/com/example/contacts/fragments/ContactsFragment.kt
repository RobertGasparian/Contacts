package com.example.contacts.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.R
import com.example.contacts.adapters.ContactAdapter
import com.example.contacts.adapters.ContactClickListener
import com.example.contacts.data.models.Contact
import com.example.contacts.network.Resource
import com.example.contacts.viewmodels.ContactsViewModel
import kotlinx.android.synthetic.main.fragment_contacts.*

class ContactsFragment : BaseFragment(), ContactClickListener {

    private var model: ContactsViewModel? = null
    private var adapter: ContactAdapter? = null

    companion object {
        val TAG = this::class.java.simpleName

        fun newInstance(): ContactsFragment {
            return ContactsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contacts, container,false)
    }

    override fun setupViews() {
        Log.i(TAG, "setupViews")
    }

    override fun bindViewModel() {
        Log.i(DetailedFragment.TAG, "bindViewModel")
        model = ViewModelProviders.of(this).get(ContactsViewModel::class.java)
        model?.getContacts()?.observe(this, Observer<Resource<List<Contact>>> {
            when(it) {
                is Resource.Success -> {
                    dismissLoadingDialog()
                    setupRecyclerView(it.data ?: emptyList())
                }
                is Resource.Error -> {
                    dismissLoadingDialog()
                    showErrorDialog(it.message)
                }
                is Resource.Loading -> {
                    showLoadingDialog()
                }
            }
        })
    }

    override fun setupListeners() {
        addFAB.setOnClickListener {
            change?.onChange(AddFragment.newInstance())
            Log.d(TAG, "here")
        }

    }

    private fun setupRecyclerView(contacts: List<Contact>) {
        adapter = ContactAdapter(contacts = contacts.toMutableList())
        adapter!!.setItemClickListener(this)
        contactRV.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        contactRV.adapter = adapter
    }

    override fun onItemClick(contact: Contact, position: Int) {
        change?.onChange(DetailedFragment.newInstance(contactId = contact._id))
    }
}