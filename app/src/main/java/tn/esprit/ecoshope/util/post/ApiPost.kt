package tn.esprit.ecoshope.util.post

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import tn.esprit.ecoshope.model.Post

interface ApiPost {
    @GET("post")
    fun getPost():Call<Post>
    companion object{
        val baseUrl = "http://192.168.1.147:9090/"
        fun create():ApiPost{
            val retrofit=Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create()).baseUrl(baseUrl).build()
            return retrofit.create(ApiPost::class.java)
        }

    }
}