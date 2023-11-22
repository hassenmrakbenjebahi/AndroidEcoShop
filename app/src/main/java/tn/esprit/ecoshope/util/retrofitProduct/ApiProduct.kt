package tn.esprit.ecoshope.util.retrofitProduct

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET

interface ApiProduct {
    @FormUrlEncoded
    @GET("/product/getone")
    fun getProduct(
        @Field("code")code:String

    ):Call<ApiProductResponse>
}