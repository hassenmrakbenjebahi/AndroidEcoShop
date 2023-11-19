package tn.esprit.ecoshope.model

import android.os.Parcel
import android.os.Parcelable

data class ProductDetails(
    val imageId: Int,
    val name: String,
    val description: String = "Description du produit",
    val impact: String = "Impact environnemental"
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(imageId)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(impact)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductDetails> {
        override fun createFromParcel(parcel: Parcel): ProductDetails {
            return ProductDetails(parcel)
        }

        override fun newArray(size: Int): Array<ProductDetails?> {
            return arrayOfNulls(size)
        }
    }

}