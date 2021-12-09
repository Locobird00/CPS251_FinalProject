package com.locobird00.contactsproject.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.locobird00.contactsproject.Contact
import com.locobird00.contactsproject.R

class ContactListAdapter(private val contactCardLayout: Int) :
    RecyclerView.Adapter<ContactListAdapter.ViewHolder>() {

    private var contactList: List<Contact>? = null
    var listener: OnItemClickListener? = null

    fun settingListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onClick(string: String)
    }

    override fun onBindViewHolder(holder: ViewHolder, listPosition: Int) {
        val itemName = holder.itemName
        val itemPhone = holder.itemPhone
        val itemID = holder.itemID
        val itemDel = holder.itemDel
        contactList.let {
            itemName.text = it!![listPosition].contactName
            itemPhone.text = it!![listPosition].contactNum
            itemID.text = it!![listPosition].contactId.toString()
        }

        itemDel.setOnClickListener(View.OnClickListener {
            val id = itemID.text.toString()
            listener?.onClick(id)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            contactCardLayout, parent, false)
        return ViewHolder(view) //, myListener)
    }

    fun setContactList(contacts: List<Contact>) {
        contactList = contacts
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (contactList == null) 0 else contactList!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemName: TextView = itemView.findViewById(R.id.contactFL)
        var itemPhone: TextView = itemView.findViewById(R.id.contactPhoneNum)
        var itemID: TextView = itemView.findViewById(R.id.contactID)
        var itemDel: ImageButton = itemView.findViewById(R.id.deleteKey)
    }
}