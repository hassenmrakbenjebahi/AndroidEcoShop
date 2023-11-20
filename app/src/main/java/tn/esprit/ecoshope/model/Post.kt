package tn.esprit.ecoshope.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Post(
             val id:String,
             val _id:String,
             val content: String,
             val publicationDate:String,
            val likes:List<String>,
            val iduser:String,
            val media:String
    ):Parcelable



