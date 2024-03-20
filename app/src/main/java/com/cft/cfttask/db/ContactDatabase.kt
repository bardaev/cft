package com.cft.cfttask.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cft.cfttask.api.data.ContactItem

@Database(entities = [ContactItem::class], version = 1)
abstract class ContactDatabase: RoomDatabase() {
    abstract fun contactDao(): ContactDao
}