package tn.esprit.ecoshope.model.Post

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comment (val id:String,
                    val _id:String,
                    val content:String,
                    val date:String,
                    val idpost:String,
                    val iduser:String


): Parcelable