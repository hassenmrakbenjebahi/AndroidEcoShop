package tn.esprit.ecoshope.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class UserHistory(
    @SerializedName("_id") val id: String,
    val userId: String,
    val productId: Product,
    val date: LocalDateTime // Utilisez le format de date approprié
)
