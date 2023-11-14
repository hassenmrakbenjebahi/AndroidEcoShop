package tn.esprit.ecoshope.util.retrofitUser

data class ApiResponse(
    val status: String,
    val message: String,
    val token: String
)