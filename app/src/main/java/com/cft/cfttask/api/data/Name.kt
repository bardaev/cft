package com.cft.cfttask.api.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
data class Name(
    @ColumnInfo(name = "first_name") @SerializedName("first") var firstName: String = "",
    @ColumnInfo(name = "last_name")@SerializedName("last") var lastName: String = "",
): Parcelable {

    private companion object : Parceler<Name> {
        override fun Name.write(parcel: Parcel, flags: Int) {
            parcel.writeString(firstName)
            parcel.writeString(lastName)
        }

        override fun create(parcel: Parcel): Name {
            return Name(parcel.readString() ?: "", parcel.readString() ?: "")
        }
    }
}
