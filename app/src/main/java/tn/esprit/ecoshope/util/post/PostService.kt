package tn.esprit.ecoshope.util.post

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import tn.esprit.ecoshope.model.Post.Comment
import tn.esprit.ecoshope.model.Post.Post
import tn.esprit.ecoshope.model.Post.UserConnect

interface PostService {
    data class newcommentpost(val content:String)
    data class newpost(val content:String)
    @GET("posts")
    fun getPost(): Call<List<Post>>

    @POST("comments/{id}/{idu}")
    fun addComment(@Path("id") id:String, @Path("idu") idu:String, @Body newcomment:newcommentpost): Call<Comment>
    @GET("users/{id}")
    fun detailUser(@Path("id") id:String): Call<UserConnect>
    @GET("comments/{id}")
    fun getAllCommentPost(@Path("id") id: String): Call<List<Comment>>
    @Multipart
    @POST("posts/addpost/{id}")
    fun addpost(@Path("id")id:String, @Part("content") content: RequestBody/*, @Part media: MultipartBody.Part*/): Call<Post>

    @PUT("posts/addlike/{id}/{idu}")
    fun addlike(@Path("id")id: String, @Path("idu")idu: String): Call<Post>
    @PUT("posts/retirelike/{id}/{idu}")
    fun retirelike(@Path("id")id:String, @Path("idu")idu:String): Call<Post>


}