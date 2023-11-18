package tn.esprit.ecoshope.util

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Query
import tn.esprit.ecoshope.model.History

interface ApiHistoriqueAchat {

    @POST("")
    fun seConnecter(

        @Query("log") login: String,
        @Query("pwd") password: String
    ): Call<History>

    companion object {

        var BASE_URL = "http://192.168.1.22:9090/"

        fun create() : ApiHistoriqueAchat {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiHistoriqueAchat::class.java)
        }
    }




}