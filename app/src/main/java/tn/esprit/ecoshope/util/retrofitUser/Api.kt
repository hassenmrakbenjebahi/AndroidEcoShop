package tn.esprit.ecoshope.util.retrofitUser


import androidx.room.Delete
import androidx.room.Query
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import tn.esprit.ecoshope.model.user.User

interface Api {


    @FormUrlEncoded
    @POST("user/login")
    fun userlogin(
        @Field("email") email :String,
        @Field("password") password:String
        ):Call<ApiResponse>


        @Multipart
        @POST("user/signup")
        fun usersignup(
            @Part("Username") Username: RequestBody,
            @Part("email") email:RequestBody,
            @Part("password") password:RequestBody,
            @Part("confirmPassword") confirmPassword:RequestBody,
            @Part("phone") phone:RequestBody,
            @Part Image: MultipartBody.Part
        ):Call<User>

        @FormUrlEncoded
        @POST("user/forgetPassword")
        fun forgetpassword(
            @Field("phone") phone: String
        ):Call<ApiResponse>

        @FormUrlEncoded
        @POST("user/otp")
        fun otp(
            @Header("Authorization") authToken: String,
            @Field("code") code: String
        ):Call<ApiResponse>
        @FormUrlEncoded
        @PATCH("user/resetPassword")
        fun resetpassword(
            @Header("Authorization") authToken: String,
            @Field("password") password:String,
            @Field("confirmPassword") confirmPassword:String
            ):Call<ApiResponse>

        @GET("user/profile")
        fun getUser(
            @Header("Authorization")token: String
        ):Call<ProfileResponse>

    @FormUrlEncoded
    @PUT("user/updateuser")
    fun updateUser(
        @Header("Authorization") token: String,
        @Field("Username") Username: String,
        @Field("email") email: String,
        @Field("phone") phone: String,

    ): Call<ApiResponse>

    @DELETE("user/deleteUser")
    fun deleteUser(
        @Header("Authorization") token: String,
    ):Call<ProfileResponse>


    @FormUrlEncoded
    @PATCH("user/updatePassword")
    fun updatepassword(
        @Header("Authorization") authToken: String,
        @Field("Oldpassword") Oldpassword:String,
        @Field("password") password:String,
        @Field("confirmPassword") confirmPassword:String
    ):Call<ApiResponse>

    @FormUrlEncoded
    @POST("user/registerUser")
    fun google(
        @Field("token") idToken: String
    ):Call<ApiGoogle>

}