package tn.esprit.ecoshope.model

import android.os.Parcel
import android.os.Parcelable

data class History(
    var imageId: Int,
    var nameProduct: String,
    var date: String,
    var isFavorite: Boolean,
    val description: String,
    val carbonFootprint: String,
    val waterConsumption: String,
    val recyclability: String,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(imageId)
        parcel.writeString(nameProduct)
        parcel.writeString(date)
        parcel.writeByte(if (isFavorite) 1 else 0)
        parcel.writeString(description)
        parcel.writeString(carbonFootprint)
        parcel.writeString(waterConsumption)
        parcel.writeString(recyclability)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<History> {
        override fun createFromParcel(parcel: Parcel): History {
            return History(parcel)
        }

        override fun newArray(size: Int): Array<History?> {
            return arrayOfNulls(size)
        }
    }
}