package com.cft.cfttask.api.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
data class Picture(
    @ColumnInfo(name = "large") var large: String = "",
    @ColumnInfo(name = "medium") var medium: String = "",
    @ColumnInfo(name = "thumbnail") var thumbnail: String =""
): Parcelable {
    private companion object : Parceler<Picture> {
        override fun Picture.write(parcel: Parcel, flags: Int) {
            parcel.writeString(large)
            parcel.writeString(medium)
            parcel.writeString(thumbnail)
        }

        override fun create(parcel: Parcel): Picture {
            return Picture(
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: ""
            )
        }
    }
}