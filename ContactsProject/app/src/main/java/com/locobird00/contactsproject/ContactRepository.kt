package com.locobird00.contactsproject

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class ContactRepository(application: Application) {

    val searchResults = MutableLiveData<List<Contact>>()
    private var contactDao: ContactDao?
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    val allContacts: LiveData<List<Contact>>?

    init {
        val db: ContactRoomDatabase? =
            ContactRoomDatabase.getDatabase(application)
        contactDao = db?.contactDao()
        allContacts = contactDao?.getAllContacts()
    }

    fun insertContact(contact: Contact) {
        coroutineScope.launch(Dispatchers.IO) {
            asyncInsert(contact)
        }
    }

    private suspend fun asyncInsert(contact: Contact) {
        contactDao?.insertContact(contact)
    }

    fun deleteContact(id: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            asyncContact(id)
        }
    }

    private suspend fun asyncContact(id: Int) {
        contactDao?.deleteContact(id)
    }

    fun findContact(name: String) {

        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFind(name).await()
        }
    }

    private suspend fun asyncFind(name: String): Deferred<List<Contact>?> =

        coroutineScope.async(Dispatchers.IO) {
            return@async contactDao?.findName(name)
        }

    fun sortAsc() {

        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncSortAsc().await()
        }
    }

    private suspend fun asyncSortAsc(): Deferred<List<Contact>?> =

        coroutineScope.async(Dispatchers.IO) {
            return@async contactDao?.sortAsc()
        }

    fun sortDesc() {

        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncSortDesc().await()
        }
    }

    private suspend fun asyncSortDesc(): Deferred<List<Contact>?> =

        coroutineScope.async(Dispatchers.IO) {
            return@async contactDao?.sortDesc()
        }

}