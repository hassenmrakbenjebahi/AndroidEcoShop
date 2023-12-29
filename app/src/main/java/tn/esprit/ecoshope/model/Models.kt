package tn.esprit.ecoshope.model

// History Model
data class HistoryRequest(
    val userId: String,
    val productId: String
)

data class HistoryResponse(
    val message: String,
)

data class HistoryEntry(
    val userId: String,
    val productId: List<Product>,
    val date: String  // ou un autre type pour représenter la date selon vos besoins
)

// Product Model
data class ProductDetails(
    val name: String,
    val description: String,
    val image: Int,
    val code: String,
    val carbonFootPrint: String,
    val waterConsumption: String,
    val recyclability: String
)
