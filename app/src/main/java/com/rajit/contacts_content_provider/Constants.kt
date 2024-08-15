package com.rajit.contacts_content_provider

import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

object Constants {

    const val READ_CONTACT_PERMISSION = android.Manifest.permission.READ_CONTACTS
    const val PERMISSION_REQUEST_CODE = 1001

    const val COLUMN_CONTACT_ID = ContactsContract.Profile._ID
    const val COLUMN_CONTACT_LOOKUP_KEY = ContactsContract.Profile.LOOKUP_KEY
    const val COLUMN_CONTACT_NAME = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
    const val COLUMN_CONTACT_NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER
    const val COLUMN_CONTACT_PHOTO_URI = ContactsContract.Contacts.PHOTO_URI

    const val HAS_PHONE_NUMBER_CONDITION = ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER

    val CONTACT_URI: Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI

    fun permissionDeniedToast(context: Context) {
        Toast.makeText(
            context,
            "Permission denied. Cannot access contacts.",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun permissionGrantedToast(context: Context) {
        Toast.makeText(
            context,
            "Permission Granted",
            Toast.LENGTH_SHORT
        ).show()
    }

}