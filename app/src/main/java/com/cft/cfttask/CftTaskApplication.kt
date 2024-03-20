package com.cft.cfttask

import android.app.Application
import com.cft.cfttask.db.ContactRepository

class CftTaskApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        ContactRepository.initialize(this)
    }
}