package tn.esprit.ecoshope.util.post

import android.net.Uri
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import tn.esprit.ecoshope.model.Comment
import tn.esprit.ecoshope.model.Post
import tn.esprit.ecoshope.model.UserConnect

interface ApiPost {

    data class newcommentpost(val content:String)
    data class newpost(val content:String)
    @GET("posts")
    fun getPost():Call<List<Post>>

    @POST("comments/{id}/{idu}")
    fun addComment(@Path("id") id:String,@Path("idu") idu:String,@Body newcomment:newcommentpost):Call<Comment>
   @GET("users/{id}")
   fun detailUser(@Path("id") id:String):Call<UserConnect>
   @GET("comments/{id}")
   fun getAllCommentPost(@Path("id") id: String):Call<List<Comment>>
   @Multipart
   @POST("posts/{id}")
   fun addpost(@Path("id")id:String,@Part("content") content:RequestBody,@Part media: MultipartBody.Part):Call<Post>
    companion object{
        val baseUrl = "http://192.168.1.147:9090/"
        fun create():ApiPost{
            val retrofit=Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create()).baseUrl(baseUrl).build()
            return retrofit.create(ApiPost::class.java)
        }

    }
}