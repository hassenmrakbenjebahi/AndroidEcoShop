package tn.esprit.ecoshope.util

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import tn.esprit.ecoshope.model.*

interface ApiService {

    @GET("/api/history/user/{userId}")
    fun getUserHistory(@Path("userId") userId: String): Call<Response<List<UserHistory>>>

    @DELETE("/api/history/{historyId}")
    fun deleteFromHistory(@Path("historyId") historyId: String): Call<Void>

    @GET("/api/history/product/{productId}")
    fun getProductDetails(@Path("productId") productId: String): Call<Product>

    @POST("/api/history/add")
    fun addToHistory(@Body request: AddToHistoryRequest): Call<History>
}
