package tn.esprit.ecoshope.util.retrofitUser

data class ProfileResponse( val _id :String,
                            val name: String,
                           val email: String,
                           var Image: String,
                            val phone : String)