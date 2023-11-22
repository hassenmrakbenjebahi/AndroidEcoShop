package tn.esprit.ecoshope.util.retrofitUser

import tn.esprit.ecoshope.model.user.User

data class ApiGoogle(
    val status: String,
    val message: String,
    val token: String,
    val user: User
)