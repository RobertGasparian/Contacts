package com.example.contacts.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.contacts.R
import com.example.contacts.data.models.Contact
import com.example.contacts.network.Resource
import com.example.contacts.viewmodels.DetailedViewModel
import kotlinx.android.synthetic.main.fragment_detailed.*

class DetailedFragment: BaseFragment() {

    private var model: DetailedViewModel? = null

    companion object {
        val TAG = this::class.java.simpleName
        private const val KEY_CONTACT_ID = "contact_id"

        fun newInstance(contactId: String): DetailedFragment {
            val args = Bundle()
            args.putString(KEY_CONTACT_ID, contactId)
            val fragment = DetailedFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detailed, container,false)
    }

    override fun setupViews() {
        Log.i(TAG, "setupViews")
    }

    override fun bindViewModel() {
        model = ViewModelProviders.of(this).get(DetailedViewModel::class.java)
        Log.i(TAG, "bindViewModel")
//        model?.getContact("5c6d0fe8e5fb1b55000039fa")?.observe(this, Observer<Resource<Contact>> {
        model?.getContact(arguments?.getString(KEY_CONTACT_ID) ?: "")?.observe(this, Observer<Resource<Contact>> {
            when(it) {
                is Resource.Success -> {
                    dismissLoadingDialog()
                    model?.setImageID(it.data?.firstImage ?: "")
                    setContactData(it.data ?: Contact())
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
        model?.getImage()?.observe(this, Observer<Resource<Bitmap>> {
            when(it) {
                is Resource.Success -> {
                    Log.d(TAG, "success")
                    avatarIV.setImageBitmap(it.data)
                }
                is Resource.Error -> {
                    Log.d(TAG, "error")
                }
                is Resource.Loading -> {
                    Log.d(TAG, "loading")
                }
            }
        })
    }

    override fun setupListeners() {
        Log.i(TAG, "setupListeners")
    }

    private fun setContactData(contact: Contact) {
        fullNameTV.text = contact.displayName
        phoneTV.text = contact.phone
        emailTV.text = contact.email
        notesTV.text = contact.notes
    }
}