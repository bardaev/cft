package com.cft.cfttask.api.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coordinates(
    @ColumnInfo(name = "latitude") var latitude: String = "",
    @ColumnInfo(name = "longitude") var longitude: String = ""
): Parcelable {
    private companion object : Parceler<Coordinates> {
        override fun Coordinates.write(parcel: Parcel, flags: Int) {
            parcel.writeString(latitude)
            parcel.writeString(longitude)
        }

        override fun create(parcel: Parcel): Coordinates {
            return Coordinates(parcel.readString() ?: "", parcel.readString() ?: "")
        }
    }
}