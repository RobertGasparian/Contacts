package com.example.contacts.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.contacts.activities.tools.FragmentChangeListener
import com.example.contacts.fragments.dialogs.LoadingFragment
import android.R
import androidx.appcompat.app.AlertDialog
import com.example.contacts.app.Utils


abstract class BaseFragment : Fragment() {

    private var loadingFragment: LoadingFragment? = null
    protected var change: FragmentChangeListener? = null
    private var isShownErrorDialog: Boolean = false
    private var numberOfRequestedLoadingDialog: Int = 0

    fun setChangeListener(listener: FragmentChangeListener) {
        change = listener
    }

    private fun setupLoadingDialog() {
        loadingFragment = LoadingFragment.newInstance()
        loadingFragment!!.isCancelable = false
    }

    fun showLoadingDialog() {
        numberOfRequestedLoadingDialog++
        if (numberOfRequestedLoadingDialog == 1) {
            setupLoadingDialog()
            loadingFragment?.show(childFragmentManager, null)
        }
    }

    fun dismissLoadingDialog() {
        if (numberOfRequestedLoadingDialog == 1) {
            loadingFragment?.dismiss()
            numberOfRequestedLoadingDialog--
        } else if (numberOfRequestedLoadingDialog > 1) {
            numberOfRequestedLoadingDialog--
        }
    }

    fun showErrorDialog(errorMessage: String? = null) {
        var message = errorMessage ?: "Unknown error"
        if (Utils.getConnectionType(context!!) == 0) {
            message = getString(com.example.contacts.R.string.no_inet_message)
        }

        if (!isShownErrorDialog) {
            isShownErrorDialog = true
            if (numberOfRequestedLoadingDialog >= 1) {
                loadingFragment?.dismiss()
            }
            AlertDialog.Builder(context!!)
                .setTitle("Error")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                    dialog.dismiss()
                    if (numberOfRequestedLoadingDialog > 0) {
                        loadingFragment?.show(childFragmentManager, null)
                    }
                    isShownErrorDialog = false
                }.create().show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        bindViewModel()
        setupListeners()
    }

    abstract fun setupViews()
    abstract fun bindViewModel()
    abstract fun setupListeners()
}