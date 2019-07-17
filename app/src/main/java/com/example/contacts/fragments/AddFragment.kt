package com.example.contacts.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.solver.widgets.ConstraintAnchor
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.contacts.R
import com.example.contacts.app.Constants
import com.example.contacts.app.Utils
import com.example.contacts.data.models.Contact
import com.example.contacts.network.Resource
import com.example.contacts.network.bodies.ContactBody
import com.example.contacts.network.responses.ImageUploadResponse
import com.example.contacts.viewmodels.AddViewModel
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : BaseFragment() {

    private var model: AddViewModel? = null

    companion object {
        val TAG = this::class.java.simpleName

        fun newInstance(): AddFragment {
            return AddFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun setupViews() {

    }

    override fun bindViewModel() {
        model = ViewModelProviders.of(this).get(AddViewModel::class.java)
    }

    override fun setupListeners() {
        addBtn.setOnClickListener {
            if (fieldsAreValid()) {
                model?.createContact(buildContact())?.observe(this, Observer<Resource<Contact>> {
                    when(it) {
                        is Resource.Success -> {
                            dismissLoadingDialog()
                            activity?.onBackPressed()
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
        }
        galleryBtn.setOnClickListener {
            if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), Constants.GALLERY_REQUEST_CODE)
            } else {
                openGallery()
            }
        }
        cameraBtn.setOnClickListener {
            if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                requestPermissions(arrayOf(Manifest.permission.CAMERA), Constants.CAMERA_REQUEST_CODE)
            } else {
                openCamera()
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, Constants.GALLERY_IMAGE_PICK_CODE)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, Constants.CAMERA_IMAGE_PICK_CODE)
    }

    private fun fieldsAreValid() : Boolean {
        var isValid = true
        if (firstNameET.text.toString().trim() == "") {
            firstNameET.error = "Invalid first name"
            isValid = false
        } else {
            firstNameIL.isErrorEnabled = false
        }
        if (lastNameET.text.toString().trim() == "") {
            lastNameIL.error = "Invalid last name"
            isValid = false
        } else {
            lastNameIL.isErrorEnabled = false
        }
        if (phoneET.text.toString().trim() == "") {
            phoneIL.error = "Invalid phone number"
            isValid = false
        } else {
            phoneIL.isErrorEnabled = false
        }
        if (emailET.text.toString().trim() == "" || !Utils.isEmailValid(emailET.text.toString().trim())) {
            emailIL.error = "Invalid email"
            isValid = false
        } else {
            emailIL.isErrorEnabled = false
        }
        if (notesET.text.toString().trim() == "") {
            notesIL.error = "Invalid notes"
            isValid = false
        } else {
            notesIL.isErrorEnabled = false
        }
        return isValid
    }

    private fun buildContact() : ContactBody {
        return ContactBody(
            firstName = firstNameET.text.toString(),
            lastName = lastNameET.text.toString(),
            phone = phoneET.text.toString(),
            email = emailET.text.toString(),
            notes = notesET.text.toString())
        //TODO: add image functional
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK ) {
            when(requestCode) {
                Constants.GALLERY_IMAGE_PICK_CODE -> {
                    val filePath = data?.data
                    filePath?.let {
                        model?.imageUpload(filePath)?.observe(this, Observer<Resource<ImageUploadResponse>> {
                            when(it) {
                                is Resource.Success -> {
                                    dismissLoadingDialog()
                                    //TODO: save image id
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

                }
                Constants.CAMERA_IMAGE_PICK_CODE -> {
                    Log.d(TAG, "camera success")
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constants.GALLERY_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    openGallery()
                } else {
                    showErrorDialog(getString(R.string.permision_denied_msg))
                }
                return
            }
            Constants.CAMERA_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    openCamera()
                } else {
                    showErrorDialog(getString(R.string.permision_denied_msg))
                }
                return
            }
        }
    }
}