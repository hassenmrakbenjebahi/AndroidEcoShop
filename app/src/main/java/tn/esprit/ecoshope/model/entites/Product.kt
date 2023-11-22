package tn.esprit.ecoshope.model.entites

data class Product (
   val  name : String,
    val description : String,
   val image: String,
    val code : String,
    val carbonFootPrint: String,
    val waterConsumption: String,
    val recyclability: String
)