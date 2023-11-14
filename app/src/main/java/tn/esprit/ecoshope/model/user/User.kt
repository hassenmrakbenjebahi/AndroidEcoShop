package tn.esprit.ecoshope.model.user

data class User( val Username:String, val email:String,
                 val password:String,
                 val confirmPassword:String,
                 val phone:String,
                 val Image:String )
