package tn.esprit.ecoshope.util

import retrofit2.Call
import retrofit2.http.*
import tn.esprit.ecoshope.model.*

interface ApiService {
    @POST("/api/history/add")
    fun addToHistory(@Body request: HistoryRequest): Call<HistoryResponse>

    @GET("/api/history/user/{userId}")
    fun getUserHistory(@Path("userId") userId: String): Call<List<HistoryEntry>>

    @DELETE("/api/history/{historyId}")
    fun deleteFromHistory(@Path("historyId") historyId: String): Call<Void>

    @GET("/api/history/product/{productId}")
    fun getProductDetails(@Path("productId") productId: String): Call<ProductDetails>
}
