package tn.esprit.ecoshope.util.post

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import tn.esprit.ecoshope.model.Comment
import tn.esprit.ecoshope.model.Post

interface ApiPost {

    data class newcommentpost(val content:String)

    @GET("posts")
    fun getPost():Call<List<Post>>

    @POST("comments/{id}/{idu}")
    fun addComment(@Path("id") id:String,@Path("idu") idu:String,@Body newcomment:newcommentpost):Call<Comment>
    companion object{
        val baseUrl = "http://192.168.1.147:9090/"
        fun create():ApiPost{
            val retrofit=Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create()).baseUrl(baseUrl).build()
            return retrofit.create(ApiPost::class.java)
        }

    }
}