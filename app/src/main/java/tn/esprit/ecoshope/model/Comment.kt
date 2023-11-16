package tn.esprit.ecoshope.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comment (val id:String,
               val author:String,
               val content:String,
               val date:String,
               val user:List<UserConnect>


    ):Parcelable
