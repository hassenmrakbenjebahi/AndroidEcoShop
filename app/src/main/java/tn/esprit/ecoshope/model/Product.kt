package tn.esprit.ecoshope.model

data class Product(
    val image: Int,
    val name: String,
    val description: String,
    val carbonFootprint: String,
    val waterConsumption: String,
    val recyclability: String,
)