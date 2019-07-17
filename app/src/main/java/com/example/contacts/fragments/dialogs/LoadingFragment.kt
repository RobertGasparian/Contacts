package com.example.contacts.fragments.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.contacts.R
import com.example.contacts.fragments.ContactsFragment

class LoadingFragment : DialogFragment() {
    companion object {
        val TAG = this::class.java.simpleName

        fun newInstance(): LoadingFragment {
            return LoadingFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_fragment_loading, container, false)
    }
}