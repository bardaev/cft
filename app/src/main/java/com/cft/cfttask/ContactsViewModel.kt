package com.cft.cfttask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cft.cfttask.api.RetrofitBuilder
import com.cft.cfttask.api.data.ContactItem
import com.cft.cfttask.db.ContactRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactsViewModel: ViewModel() {

    private val db = ContactRepository.get()
    private val apiService = RetrofitBuilder.apiService
    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Error: ${throwable.localizedMessage}")
    }

    val contacts = MutableLiveData<List<ContactItem>?>()
    val contactsLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun loadDataFromDB() {
        CoroutineScope(Dispatchers.IO).launch {
            val data = db.getContacts()
            withContext(Dispatchers.Main) {
                contacts.value = data
            }
        }
    }

    fun refresh() {
        fetchUsers()
    }
    private fun fetchUsers() {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            try {
                val response = apiService.get(20)
                if (response.isSuccessful) {
                    val result = response.body()?.contactItems?.let { db.deleteInsertAndGet(it) }
                    withContext(Dispatchers.Main) {
                        contacts.value = result
                        contactsLoadError.value = null
                        loading.value = false
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError("Connection error")
                }
            }
        }
        contactsLoadError.value = ""
        loading.value = false
    }

    private fun onError(message: String) {
        contactsLoadError.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}