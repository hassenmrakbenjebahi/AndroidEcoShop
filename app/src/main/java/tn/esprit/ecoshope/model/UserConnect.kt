package tn.esprit.ecoshope.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserConnect (
    val _id:String,
    val Username:String,
    val email:String,
    val password:String,
    val confirmPassword:String,
    val phone:String,
    val image:String
):Parcelable