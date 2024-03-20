package com.cft.cfttask.api.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
data class Street(
    @ColumnInfo(name = "number") var number: Int = 0,
    @ColumnInfo(name = "name") var name: String = ""
): Parcelable {
    private companion object : Parceler<Street> {
        override fun Street.write(parcel: Parcel, flags: Int) {
            parcel.writeInt(number)
            parcel.writeString(name)
        }

        override fun create(parcel: Parcel): Street {
            return Street(parcel.readInt() ?: 0, parcel.readString() ?: "")
        }
    }
}
