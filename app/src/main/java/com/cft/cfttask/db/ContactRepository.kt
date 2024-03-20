package com.cft.cfttask.db

import android.content.Context
import androidx.room.Room
import com.cft.cfttask.api.data.ContactItem

private const val DATABASE_NAME = "contact-db"

class ContactRepository private constructor(context: Context) {

    private val database: ContactDatabase = Room.databaseBuilder(
        context.applicationContext,
        ContactDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val contactDao = database.contactDao()

    suspend fun getContacts(): List<ContactItem> = contactDao.getAll()

    suspend fun deleteInsertAndGet(contacts: List<ContactItem>): List<ContactItem> = contactDao.deleteInsertAndGet(contacts)

    companion object {
        private var INSTANCE: ContactRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ContactRepository(context)
            }
        }

        fun get(): ContactRepository {
            return INSTANCE ?: throw IllegalStateException("Contact repository must be init")
        }
    }
}