package com.cft.cfttask.api.data

import com.google.gson.annotations.SerializedName

class RandomUserResponse {
    @SerializedName("results") lateinit var contactItems: ArrayList<ContactItem>
    lateinit var info: Info
}