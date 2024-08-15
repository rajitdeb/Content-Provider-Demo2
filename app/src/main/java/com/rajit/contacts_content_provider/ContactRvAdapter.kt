package com.rajit.contacts_content_provider

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rajit.contacts_content_provider.databinding.ContactItemviewBinding
import com.rajit.contentprovidersample_musicplayerapp.ContactDiffUtil

class ContactRvAdapter: RecyclerView.Adapter<ContactRvAdapter.ContactRvViewHolder>() {

    private var contactList = mutableListOf<Contact>()

    inner class ContactRvViewHolder(private val customView: ContactItemviewBinding):
        RecyclerView.ViewHolder(customView.root) {

            fun bind(currentContact: Contact) {
                customView.apply {
                    contactNameTv.text = currentContact.name
                    phoneNumberTv.text = currentContact.phone

                    contactPhotoIv.load(currentContact.photo) {
                        placeholder(R.drawable.placeholder)
                        error(R.drawable.placeholder)
                    }
                }
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactRvViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ContactItemviewBinding.inflate(inflater, parent, false)
        return ContactRvViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactRvViewHolder, position: Int) {
        val currentSong = contactList[position]

        Log.i("SongRvAdapter", "onBindViewHolder: $currentSong")

        holder.bind(currentSong)
    }

    fun setList(newSongsList: ArrayList<Contact>) {
        val util = ContactDiffUtil(contactList, newSongsList)
        val diffUtilResult = DiffUtil.calculateDiff(util)
        contactList = newSongsList
        diffUtilResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

}