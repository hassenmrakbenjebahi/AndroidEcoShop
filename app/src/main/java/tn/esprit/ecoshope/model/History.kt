package tn.esprit.ecoshope.model
import android.os.Parcel
import android.os.Parcelable

data class History(
    val userId: String,
    val productId: String,
    var imageId: String,
    var nameProduct: String,
    var date: String,
    var isFavorite: Boolean,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(productId)
        parcel.writeString(imageId)
        parcel.writeString(nameProduct)
        parcel.writeString(date)
        parcel.writeByte(if (isFavorite) 1 else 0)
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