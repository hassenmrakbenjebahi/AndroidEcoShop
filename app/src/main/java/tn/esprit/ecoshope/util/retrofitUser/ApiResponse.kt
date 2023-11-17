package tn.esprit.ecoshope.util.retrofitUser

import tn.esprit.ecoshope.model.user.User

data class ApiResponse(
    val status: String,
    val message: String,
    val token: String
)