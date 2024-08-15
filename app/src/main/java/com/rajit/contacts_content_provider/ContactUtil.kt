package com.rajit.contacts_content_provider

import android.content.Context
import android.provider.ContactsContract

object ContactUtil {

    fun importContact(mContext: Context): ArrayList<Contact> {

        val contactList = arrayListOf<Contact>()

        val contentResolver = mContext.contentResolver

        val projection = arrayOf<String>(
            Constants.COLUMN_CONTACT_ID,
            Constants.COLUMN_CONTACT_LOOKUP_KEY,
            Constants.COLUMN_CONTACT_NAME,
            Constants.COLUMN_CONTACT_NUMBER,
            Constants.COLUMN_CONTACT_PHOTO_URI
        )

        val selection = Constants.HAS_PHONE_NUMBER_CONDITION

        val cursor = contentResolver.query(
            Constants.CONTACT_URI,
            projection,
            selection,
            null,
            null
        )

        cursor?.use {
            val idIndex = it.getColumnIndex(ContactsContract.Contacts._ID)
            val nameIndex = it.getColumnIndex(Constants.COLUMN_CONTACT_NAME)
            val phoneNumberIndex = it.getColumnIndex(Constants.COLUMN_CONTACT_NUMBER)
            val photoIndex = it.getColumnIndexOrThrow(Constants.COLUMN_CONTACT_PHOTO_URI)

            while(it.moveToNext()) {
                val contactID = it.getString(idIndex)
                val contactName = it.getString(nameIndex)
                val contactPhone = it.getString(phoneNumberIndex)
                val contactPhoto = it.getString(photoIndex)

                val contact = Contact(
                    id = contactID,
                    name = contactName,
                    phone = contactPhone,
                    photo = contactPhoto ?: ""
                )

                contactList.add(contact)
            }
        }

        return contactList

    }

}