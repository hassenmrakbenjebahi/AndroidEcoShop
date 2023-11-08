package tn.esprit.ecoshope.model.user

data class User( val Firstname:String, val Lastname:String, val email:String,val password:String,
                 val confirmPassword:String,
                 val phone:Int,
                 val Image:String )
