package com.cft.cfttask.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.cft.cfttask.api.data.ContactItem

@Dao
interface ContactDao {

    @Query("SELECT * FROM contact")
    suspend fun getAll(): List<ContactItem>

    @Insert
    suspend fun insertAll(contactItems: List<ContactItem>)

    @Query("DELETE FROM contact")
    suspend fun deleteAll()

    @Transaction
    suspend fun deleteInsertAndGet(contactItems: List<ContactItem>): List<ContactItem> {
        deleteAll()
        insertAll(contactItems)
        return getAll()
    }
}