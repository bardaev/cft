package com.cft.cfttask.api.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
data class Id(
    @ColumnInfo(name = "_id") @SerializedName("value") var id: String? = null,
    @ColumnInfo(name = "_name") var name: String? = null
): Parcelable {
    private companion object : Parceler<Id> {
        override fun Id.write(parcel: Parcel, flags: Int) {
            parcel.writeString(id)
            parcel.writeString(name)
        }

        override fun create(parcel: Parcel): Id {
            return Id(
                parcel.readString() ?: "",
                parcel.readString() ?: ""
            )
        }
    }
}
