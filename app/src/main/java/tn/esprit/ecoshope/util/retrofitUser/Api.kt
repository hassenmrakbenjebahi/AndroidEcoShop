package tn.esprit.ecoshope.util.retrofitUser


import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import tn.esprit.ecoshope.model.user.User

interface Api {


    @FormUrlEncoded
    @POST("login")
    fun userlogin(
        @Field("email") email :String,
        @Field("password") password:String
        ):Call<User>


        @Multipart
        @POST("signup")
        fun usersignup(
            @Part("Username") Username: RequestBody,
            @Part("email") email:RequestBody,
            @Part("password") password:RequestBody,
            @Part("confirmPassword") confirmPassword:RequestBody,
            @Part("phone") phone:RequestBody,
            @Part Image: MultipartBody.Part
        ):Call<User>

        @FormUrlEncoded
        @POST("forgetPassword")
        fun forgetpassword(
            @Field("phone") phone: String
        ):Call<ApiResponse>

        @FormUrlEncoded
        @POST("otp")
        fun otp(
            @Header("Authorization") authToken: String,
            @Field("code") code: String
        ):Call<ApiResponse>
        @FormUrlEncoded
        @PATCH("resetPassword")
        fun resetpassword(
            @Header("Authorization") authToken: String,
            @Field("password") password:String,
            @Field("confirmPassword") confirmPassword:String
            ):Call<ApiResponse>


    companion object {

        private var BASE_URL = "http://192.168.0.23:3000/user/"

        fun create() : Api {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(Api::class.java)
        }
    }


}