package tn.esprit.ecoshope.util

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import tn.esprit.ecoshope.model.*

interface ApiService {

    @GET("/historique/user/{userId}")
    fun getUserHistory(@Path("userId") userId: String): Call<Response<List<UserHistory>>>

    @DELETE("/historique/{historyId}")
    fun deleteFromHistory(@Path("historyId") historyId: String): Call<Void>

    @GET("/historique/product/{productId}")
    fun getProductDetails(@Path("productId") productId: String): Call<Product>

    @POST("/historique/add")
    fun addToHistory(@Body request: AddToHistoryRequest): Call<History>
}
