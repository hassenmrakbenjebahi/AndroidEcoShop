package tn.esprit.ecoshope.util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tn.esprit.ecoshope.util.retrofitUser.Api

object ClientObject {

    private var BASE_URL = "http://192.168.0.23:3000/"



        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

          fun <T>buildService (serviceType: Class<T>):T{
              return retrofit.create(serviceType)
          }


}