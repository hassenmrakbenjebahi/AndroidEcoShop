package tn.esprit.ecoshope.util.retrofitUser


import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query
import tn.esprit.ecoshope.model.user.User

interface Api {


    @FormUrlEncoded
    @POST("login")
    fun userlogin(
        @Field("email") email :String,
        @Field("password") password:String
        ):Call<User>

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