package tn.esprit.ecoshope.util.retrofithistory

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import tn.esprit.ecoshope.model.entites.Product
import tn.esprit.ecoshope.model.history.History

interface ApiHistoriqueAchat {

    @POST("/historique/addToHistory")
    suspend fun addToHistory(@Body history: History): Response<History>

    @GET("/historique/getAllHistory/{id}")
     fun getAllHistory(@Path("id") userId: String): Call<List<History>>

    @DELETE("/historique/deleteHistory/{historyId}")
    suspend fun deleteHistory(@Path("historyId") historyId: String): Response<Unit>

    @GET("product/{id}")
    suspend fun getOnce(@Path("id") productId: String): Response<Product>

}