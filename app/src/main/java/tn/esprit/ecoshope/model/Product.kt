package tn.esprit.ecoshope.model

data class Product(
    val name: String,
    val description: String,
    val image: String,
    val code: String,
    val carbonFootprint: String,
    val waterConsumption: String,
    val recyclability: String,
)