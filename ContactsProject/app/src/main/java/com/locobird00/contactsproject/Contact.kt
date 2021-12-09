package com.locobird00.contactsproject

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
class Contact {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "contactId")
    var contactId: Int = 0

    @ColumnInfo(name = "contactName")
    var contactName: String? = null
    var contactNum: String? = null

    constructor() {}

    constructor(id: Int, contactName: String?, contactNum: String?) {
        this.contactName = contactName
        this.contactNum = contactNum
    }

    constructor(contactName: String?, contactNum: String?) {
        this.contactName = contactName
        this.contactNum = contactNum
    }
}