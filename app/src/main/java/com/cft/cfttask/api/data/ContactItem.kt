package com.cft.cfttask.api.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "contact")
data class ContactItem (
    @PrimaryKey(autoGenerate = true)
    @Expose(serialize = false, deserialize = false)
    var identifier: Int = 0,
    @Embedded
    @SerializedName("id") var id: Id = Id(),
    var gender: String = "",
    @Embedded
    @SerializedName("name") var name: Name = Name(),
    var email: String = "",
    var phone: String = "",
    var cell: String = "",
    @Embedded
    var location: Location = Location(),
    @Embedded
    var picture: Picture = Picture()
): Parcelable {
    val fullName: String
        get() = "${name.firstName} ${name.lastName}"
    val address: String
        get() = "${location.city} ${location.street.name} ${location.street.number}"

    private companion object : Parceler<ContactItem> {
        override fun ContactItem.write(parcel: Parcel, flags: Int) {
            parcel.writeInt(identifier)
            parcel.writeParcelable(id, 0)
            parcel.writeString(gender)
            parcel.writeParcelable(name, 0)
            parcel.writeString(email)
            parcel.writeString(phone)
            parcel.writeString(cell)
            parcel.writeParcelable(location, 0)
            parcel.writeParcelable(picture, 0)
        }

        override fun create(parcel: Parcel): ContactItem {
            return ContactItem(
                parcel.readInt(),
                parcel.readParcelable(null, Id::class.java) ?: Id(),
                parcel.readString() ?: "",
                parcel.readParcelable(null, Name::class.java) ?: Name(),
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readParcelable(null, Location::class.java) ?: Location(),
                parcel.readParcelable(null, Picture::class.java) ?: Picture()
            )
        }
    }
}