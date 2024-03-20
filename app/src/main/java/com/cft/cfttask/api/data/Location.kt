package com.cft.cfttask.api.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    @Embedded var street: Street = Street(),
    @ColumnInfo(name = "city") var city: String = "",
    @ColumnInfo(name = "state") var state: String = "",
    @ColumnInfo(name = "country") var country: String = "",
    @ColumnInfo(name = "postcode") var postcode: String = "",
    @Embedded var coordinates: Coordinates = Coordinates()
): Parcelable {
    private companion object : Parceler<Location> {
        override fun Location.write(parcel: Parcel, flags: Int) {
            parcel.writeParcelable(street, 0)
            parcel.writeString(city)
            parcel.writeString(state)
            parcel.writeString(country)
            parcel.writeString(postcode)
            parcel.writeParcelable(coordinates, 0)
        }

        override fun create(parcel: Parcel): Location {
            return Location(
                parcel.readParcelable(null, Street::class.java) ?: Street()
            )
        }
    }
}

