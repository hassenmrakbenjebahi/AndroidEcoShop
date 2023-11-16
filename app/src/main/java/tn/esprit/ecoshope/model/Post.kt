package tn.esprit.ecoshope.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Post(
             val id:String,
             val author: String,
             val content: String,
             val publicationDate:String,
            val comment:List<Comment>,
            val likes:List<String>,
            val user:List<UserConnect>
    ):Parcelable



