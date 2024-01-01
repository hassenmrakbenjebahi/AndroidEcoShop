package tn.esprit.ecoshope.util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClientObject {
    val baseUrl = "http://192.168.1.18:9090/"
    val retrofit= Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create()).baseUrl(baseUrl).build()
    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }

}