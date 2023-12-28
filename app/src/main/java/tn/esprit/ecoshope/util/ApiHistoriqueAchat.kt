package tn.esprit.ecoshope.util

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.DELETE
import retrofit2.http.Path
import tn.esprit.ecoshope.model.History
import tn.esprit.ecoshope.model.Product

interface ApiHistoriqueAchat {

    @GET("/historique/getAllHistory/{historyByUserId}")
     fun getAllHistory(@Path("historyByUserId") userId: String): Call<List<History>>

    @GET("/historique/getAllHistory2")
    fun getAllHistory2(): Call<List<History>>

    @DELETE("/historique/deleteHistory/{historyId}")
    suspend fun deleteHistory(@Path("historyId") historyId: String): Response<Unit>

    @GET("product/{id}")
    suspend fun getOnce(@Path("id") productId: String): Response<Product>

    companion object {
       private var BASE_URL = "http://172.20.10.2:9090/"
        fun create() : ApiHistoriqueAchat {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiHistoriqueAchat::class.java)
        }
    }

}