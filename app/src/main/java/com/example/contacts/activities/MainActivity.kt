package com.example.contacts.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.contacts.R
import com.example.contacts.activities.tools.FragmentChangeListener
import com.example.contacts.data.Repository
import com.example.contacts.fragments.BaseFragment
import com.example.contacts.fragments.ContactsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FragmentChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setInitialFragment()
    }

    private fun setInitialFragment() {
        val fragment = ContactsFragment.newInstance()
        fragment.setChangeListener(this@MainActivity)
        supportFragmentManager.beginTransaction()
            .replace(R.id.rootLayout, fragment)
            .commit()
    }

    override fun onChange(fragment: BaseFragment) {
        fragment.setChangeListener(this@MainActivity)
        supportFragmentManager.beginTransaction()
            .replace(R.id.rootLayout, fragment)
            .addToBackStack(null)
            .commit()
    }
}
